import java.util.regex.Pattern;

public class Tags {

    public static final String TAG_LIBRARY = "library";
    public static final String TAG_TITLE = "title";
    public static final String TAG_AUTHOR = "author";
    public static final String TAG_YEAR = "year";
    public static final String TAG_GENRE = "genre";
    public static final String TAG_ISBN = "isbn";
    public static final String TAG_FORMAT = "format";
    public static final String TAG_AWARDS = "awards";
    public static final String TAG_AWARD = "award";
    public static final String TAG_LANG = "language";
    public static final String TAG_PRICE = "price currency=";
    public static final String TAG_BOOK = "book id=";
    //    public static final Pattern PATTERN_CLOSE = Pattern.compile("/[a-zA-Z]+");
//    public static final Pattern PATTERN_HEADER = Pattern.compile("\\?.+\\?\\s*");
    public static final Pattern PATTERN_PRICE = Pattern.compile("price\\s+currency=\"([A-Z]+)\"");
    public static final Pattern PATTERN_BOOK = Pattern.compile("book\\s+id=\"(\\d+)\"");
}
