package com.awrad.app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookData {
    public static List<BookSection> getSections() {
        List<BookSection> s = new ArrayList<>();
        s.add(new BookSection("intro", "المقدمة والرسائل", "📖", Arrays.asList(
            new BookItem("مبدؤنا الإخلاص", 2, "المقدمة والرسائل"),
            new BookItem("يا من أتى في حيّنا", 3, "المقدمة والرسائل"),
            new BookItem("من رسائل شيخنا", 4, "المقدمة والرسائل"),
            new BookItem("الوظيفة الإخلاصية", 7, "المقدمة والرسائل")
        )));
        s.add(new BookSection("awrad", "الأوراد اليومية", "⭐", Arrays.asList(
            new BookItem("منظومة أسماء الله الحسنى", 22, "الأوراد اليومية"),
            new BookItem("الورد الإخلاصي", 25, "الأوراد اليومية"),
            new BookItem("دعوة الإسم المفرد", 25, "الأوراد اليومية"),
            new BookItem("قصيدة الأحدية", 26, "الأوراد اليومية"),
            new BookItem("من أوراده", 28, "الأوراد اليومية"),
            new BookItem("ورد المضطر", 29, "الأوراد اليومية"),
            new BookItem("ورد السحر", 42, "الأوراد اليومية")
        )));
        s.add(new BookSection("salawat", "الصلوات والأذكار", "🌙", Arrays.asList(
            new BookItem("الصلوات الأبجدية", 61, "الصلوات والأذكار"),
            new BookItem("علاج الأنفس السبعة", 80, "الصلوات والأذكار"),
            new BookItem("الخوة الطاهرة في الدائرة الفاخرة", 95, "الصلوات والأذكار"),
            new BookItem("الاستغاثة المرجوة الإجابة", 99, "الصلوات والأذكار"),
            new BookItem("الجوهرة النورانية", 105, "الصلوات والأذكار"),
            new BookItem("الصلوات الخاتمة", 254, "الصلوات والأذكار")
        )));
        s.add(new BookSection("hizb", "الأحزاب", "☪", Arrays.asList(
            new BookItem("حزب البرهانين", 109, "الأحزاب"),
            new BookItem("حزب النصر", 112, "الأحزاب"),
            new BookItem("حزب النصر لسيدي أبو الحسن", 115, "الأحزاب"),
            new BookItem("حزب الفتح", 118, "الأحزاب"),
            new BookItem("الذخيرة المعدة لكل ضائقة وشدة", 120, "الأحزاب"),
            new BookItem("حزب العناية لمن أراد الكفاية", 123, "الأحزاب"),
            new BookItem("الحزب السيفي", 138, "الأحزاب"),
            new BookItem("الحزب المغني", 155, "الأحزاب"),
            new BookItem("الكفكفية الإخلاصية", 160, "الأحزاب")
        )));
        s.add(new BookSection("anasheed", "الأناشيد والقصائد", "🎵", Arrays.asList(
            new BookItem("أناشيد أهل الفكر المستظرفة", 161, "الأناشيد والقصائد"),
            new BookItem("أنا الدوار في كل البروج", 260, "الأناشيد والقصائد")
        )));
        s.add(new BookSection("weekly", "الأوراد الأسبوعية", "📅", Arrays.asList(
            new BookItem("الأوراد السبعة لأيام الجمعة", 179, "الأوراد الأسبوعية"),
            new BookItem("الأحد", 179, "الأوراد الأسبوعية"),
            new BookItem("الاثنين", 188, "الأوراد الأسبوعية"),
            new BookItem("الثلاثاء", 198, "الأوراد الأسبوعية"),
            new BookItem("الأربعاء", 209, "الأوراد الأسبوعية"),
            new BookItem("الخميس", 219, "الأوراد الأسبوعية"),
            new BookItem("الجمعة", 230, "الأوراد الأسبوعية"),
            new BookItem("السبت", 243, "الأوراد الأسبوعية")
        )));
        return s;
    }
    public static List<BookItem> getAllItems() {
        List<BookItem> all = new ArrayList<>();
        for (BookSection sec : getSections()) all.addAll(sec.getItems());
        return all;
    }
}
