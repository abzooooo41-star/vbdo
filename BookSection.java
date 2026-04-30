package com.awrad.app;
import java.util.List;
public class BookSection {
    private final String id, label, icon;
    private final List<BookItem> items;
    public BookSection(String id, String label, String icon, List<BookItem> items) {
        this.id = id; this.label = label; this.icon = icon; this.items = items;
    }
    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getIcon() { return icon; }
    public List<BookItem> getItems() { return items; }
}
