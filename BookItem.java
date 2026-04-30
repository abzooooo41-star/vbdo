package com.awrad.app;
public class BookItem {
    private final String title;
    private final int page;
    private final String sectionLabel;
    public BookItem(String title, int page, String sectionLabel) {
        this.title = title; this.page = page; this.sectionLabel = sectionLabel;
    }
    public String getTitle() { return title; }
    public int getPage() { return page; }
    public String getSectionLabel() { return sectionLabel; }
}
