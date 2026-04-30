package com.awrad.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.*;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private List<BookSection> sections;
    private BookItemAdapter adapter;
    private String currentSectionId = "intro";
    private LinearLayout tabsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sections = BookData.getSections();

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookItemAdapter(this::openPdfViewer);
        rv.setAdapter(adapter);

        tabsContainer = findViewById(R.id.tabsContainer);
        buildTabs();

        EditText etSearch = findViewById(R.id.etSearch);
        ImageButton btnClear = findViewById(R.id.btnClear);

        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int st, int before, int count) {
                String q = s.toString().trim();
                adapter.setSearchQuery(q);
                btnClear.setVisibility(q.isEmpty() ? View.GONE : View.VISIBLE);
                if (!q.isEmpty()) filterAll(q);
                else showSection(currentSectionId);
            }
        });
        btnClear.setOnClickListener(v -> etSearch.setText(""));
        showSection(currentSectionId);
    }

    private void buildTabs() {
        tabsContainer.removeAllViews();
        for (BookSection sec : sections) {
            TextView tab = new TextView(this);
            tab.setText(sec.getIcon() + " " + sec.getLabel());
            boolean active = sec.getId().equals(currentSectionId);
            tab.setTextColor(Color.parseColor(active ? "#e8c97a" : "#99ffffff"));
            tab.setBackgroundResource(active ? R.drawable.tab_active_bg : R.drawable.tab_normal_bg);
            tab.setPadding(dp(14), dp(7), dp(14), dp(7));
            tab.setTextSize(13);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMarginEnd(dp(8));
            tab.setLayoutParams(lp);
            tab.setOnClickListener(v -> {
                currentSectionId = sec.getId();
                EditText et = findViewById(R.id.etSearch);
                et.setText("");
                showSection(currentSectionId);
                buildTabs();
            });
            tabsContainer.addView(tab);
        }
    }

    private void showSection(String id) {
        for (BookSection sec : sections) {
            if (sec.getId().equals(id)) {
                adapter.setSearchQuery("");
                adapter.setItems(sec.getItems());
                ((TextView) findViewById(R.id.tvSectionTitle)).setText(sec.getIcon() + "  " + sec.getLabel());
                ((TextView) findViewById(R.id.tvCount)).setText(sec.getItems().size() + " موضوع");
                return;
            }
        }
    }

    private void filterAll(String query) {
        String lq = query.toLowerCase();
        List<BookItem> results = new ArrayList<>();
        for (BookItem item : BookData.getAllItems())
            if (item.getTitle().toLowerCase().contains(lq) || item.getSectionLabel().toLowerCase().contains(lq))
                results.add(item);
        adapter.setSearchQuery(query);
        adapter.setItems(results);
        ((TextView) findViewById(R.id.tvSectionTitle)).setText("🔍  نتائج: \"" + query + "\"");
        ((TextView) findViewById(R.id.tvCount)).setText(results.size() + " نتيجة");
    }

    private void openPdfViewer(BookItem item) {
        Intent intent = new Intent(this, PdfViewerActivity.class);
        intent.putExtra("page", item.getPage());
        intent.putExtra("title", item.getTitle());
        startActivity(intent);
    }

    private int dp(int val) {
        return Math.round(val * getResources().getDisplayMetrics().density);
    }
}
