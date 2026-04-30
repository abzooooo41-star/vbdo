package com.awrad.app;

import android.graphics.*;
import android.graphics.pdf.PdfRenderer;
import android.os.*;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.util.concurrent.*;

public class PdfViewerActivity extends AppCompatActivity {
    private PdfRenderer pdfRenderer;
    private ParcelFileDescriptor fileDescriptor;
    private int currentPage = 0, totalPages = 0;
    private ImageView ivPage;
    private TextView tvTitle, tvPageNum;
    private ImageButton btnPrev, btnNext, btnBack;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        ivPage = findViewById(R.id.ivPage);
        tvTitle = findViewById(R.id.tvTitle);
        tvPageNum = findViewById(R.id.tvPageNum);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);

        String title = getIntent().getStringExtra("title");
        int targetPage = getIntent().getIntExtra("page", 1);
        tvTitle.setText(title != null ? title : "أوراد الطريقة الإخلاصية العلية");
        btnBack.setOnClickListener(v -> finish());
        btnPrev.setOnClickListener(v -> renderPage(currentPage - 1));
        btnNext.setOnClickListener(v -> renderPage(currentPage + 1));
        loadPdf(targetPage);
    }

    private void loadPdf(int targetPage) {
        progressBar.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            try {
                File f = getPdfFile();
                fileDescriptor = ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_ONLY);
                pdfRenderer = new PdfRenderer(fileDescriptor);
                totalPages = pdfRenderer.getPageCount();
                int idx = Math.max(0, Math.min(targetPage - 1, totalPages - 1));
                runOnUiThread(() -> { progressBar.setVisibility(View.GONE); renderPage(idx); });
            } catch (Exception e) {
                runOnUiThread(() -> { progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "خطأ في تحميل الكتاب", Toast.LENGTH_LONG).show(); });
            }
        });
    }

    private File getPdfFile() throws IOException {
        File f = new File(getCacheDir(), "awrad.pdf");
        if (!f.exists()) {
            try (InputStream in = getAssets().open("awrad.pdf"); FileOutputStream out = new FileOutputStream(f)) {
                byte[] buf = new byte[8192]; int n;
                while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
            }
        }
        return f;
    }

    private void renderPage(int index) {
        if (pdfRenderer == null || index < 0 || index >= totalPages) return;
        currentPage = index;
        btnPrev.setEnabled(index > 0); btnPrev.setAlpha(index > 0 ? 1f : 0.4f);
        btnNext.setEnabled(index < totalPages - 1); btnNext.setAlpha(index < totalPages - 1 ? 1f : 0.4f);
        tvPageNum.setText("صفحة " + (index + 1) + " / " + totalPages);
        progressBar.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            try (PdfRenderer.Page page = pdfRenderer.openPage(index)) {
                DisplayMetrics dm = getResources().getDisplayMetrics();
                int sw = dm.widthPixels - dp(16);
                float scale = (float) sw / page.getWidth();
                Bitmap bm = Bitmap.createBitmap(sw, (int)(page.getHeight() * scale), Bitmap.Config.ARGB_8888);
                new Canvas(bm).drawColor(Color.WHITE);
                page.render(bm, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                runOnUiThread(() -> { progressBar.setVisibility(View.GONE); ivPage.setImageBitmap(bm); });
            } catch (Exception e) { runOnUiThread(() -> progressBar.setVisibility(View.GONE)); }
        });
    }

    private int dp(int val) { return Math.round(val * getResources().getDisplayMetrics().density); }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdfRenderer != null) pdfRenderer.close();
        try { if (fileDescriptor != null) fileDescriptor.close(); } catch (IOException ignored) {}
        executor.shutdown();
    }
}
