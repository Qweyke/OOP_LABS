public class Tags {
    public static final String TAG_BOOK = "<book\\s+id=\"(\\d+)\">";
    public static final String TAG_LIBRARY = "<library>";
    public static final String TAG_TITLE = "<title>";
    public static final String TAG_AUTHOR = "<author>";
    public static final String TAG_YEAR = "<year>";
    public static final String TAG_GENRE = "<genre>";
    public static final String TAG_PRICE = "<price\\s+currency=\"([A-Z]+)\">";
    public static final String TAG_ISBN = "<isbn>";
    public static final String TAG_FORMAT = "<format>";
    public static final String TAG_AWARDS = "<awards>";
    public static final String TAG_AWARD = "<award>";
    public static final String TAG_LANG = "<language>";
    public static final String TAG_CLOSE = "</[a-zA-Z]+>";
    public static final String TAG_HEADER = "<\\?.+\\?>\\s.";
}
