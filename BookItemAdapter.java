package com.awrad.app;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {
    public interface OnItemClickListener { void onItemClick(BookItem item); }

    private List<BookItem> items = new ArrayList<>();
    private String searchQuery = "";
    private int selectedPosition = -1;
    private final OnItemClickListener listener;

    public BookItemAdapter(OnItemClickListener listener) { this.listener = listener; }

    public void setItems(List<BookItem> items) {
        this.items = items; this.selectedPosition = -1; notifyDataSetChanged();
    }

    public void setSearchQuery(String q) { this.searchQuery = q; }

    public void setSelectedPosition(int pos) {
        int old = selectedPosition; selectedPosition = pos;
        if (old >= 0) notifyItemChanged(old);
        if (pos >= 0) notifyItemChanged(pos);
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        BookItem item = items.get(position);
        boolean selected = position == selectedPosition;
        h.tvTitle.setText(!searchQuery.isEmpty() ? highlight(item.getTitle(), searchQuery) : new SpannableString(item.getTitle()));
        h.tvPage.setText("ص " + item.getPage());
        if (!searchQuery.isEmpty() && item.getSectionLabel() != null) {
            h.tvSection.setVisibility(View.VISIBLE);
            h.tvSection.setText(item.getSectionLabel());
        } else { h.tvSection.setVisibility(View.GONE); }
        h.itemView.setBackgroundResource(selected ? R.drawable.item_selected_bg : R.drawable.item_normal_bg);
        h.tvTitle.setTextColor(Color.parseColor(selected ? "#e8c97a" : "#faf6ee"));
        h.tvPage.setTextColor(Color.parseColor(selected ? "#e8c97a" : "#c9a84c"));
        h.itemView.setOnClickListener(v -> { setSelectedPosition(h.getAdapterPosition()); listener.onItemClick(item); });
    }

    private SpannableString highlight(String text, String query) {
        SpannableString s = new SpannableString(text);
        String lt = text.toLowerCase(), lq = query.toLowerCase();
        int start = 0;
        while (true) {
            int idx = lt.indexOf(lq, start);
            if (idx == -1) break;
            s.setSpan(new BackgroundColorSpan(Color.parseColor("#4dc9a84c")), idx, idx + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = idx + 1;
        }
        return s;
    }

    @Override public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPage, tvSection;
        ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvPage = v.findViewById(R.id.tvPage);
            tvSection = v.findViewById(R.id.tvSection);
        }
    }
}
