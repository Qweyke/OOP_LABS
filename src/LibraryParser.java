import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryParser {
    BufferedReader libReader;
    StringBuilder output;

    private short currentBookId;
    ArrayList<Book> libraryBooks;
    private static final String TAG_BOOK = "<book\\s+id=\"(\\d+)\">";
    private static final String TAG_LIBRARY = "library";
    private static final String TAG_TITLE = "<title>";
    private static final String TAG_AUTHOR = "<author>";
    private static final String TAG_YEAR = "<year>";
    private static final String TAG_GENRE = "<genre>";
    private static final String TAG_PRICE = "<price\\s+currency=\"([A-Z]+)\">";
    private static final String TAG_ISBN = "<isbn>";
    private static final String TAG_AWARDS = "<awards>";
    private static final String TAG_AWARD = "<award>";
    private static final String TAG_LANG = "<language>";
    private static final String TAG_CLOSE = "</[a-zA-Z]+>";
    private static final String TAG_HEADER = "<\\?.+\\?>";

    public LibraryParser(String fileName) {
        try {
            libReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " file read\n");
        }

        output = new StringBuilder();
        libraryBooks = new ArrayList<>();
    }

    public void readFile() {

        int symbol;

        try {
            while ((symbol = libReader.read()) != -1) {

                if (symbol == '<') {

                    while (symbol != '>') {
                        output.append((char) symbol);
                        symbol = libReader.read();
                    }
                    output.append((char) symbol);

                    String tag = output.toString();
                    System.out.println(tag);
                    output.setLength(0);
                    switch (tag) {
                        case TAG_LIBRARY:
                            System.out.println("Library exists");
                        case TAG_TITLE:
                        case TAG_AUTHOR:
                        case TAG_YEAR:
                        case TAG_GENRE:
                        case TAG_PRICE:
                        case TAG_ISBN:
                        case TAG_AWARDS:
                        case TAG_AWARD:
                        case TAG_LANG:
                            
                            break;

                        default:
                            if (Pattern.matches(TAG_BOOK, tag)) {
                                Matcher matcher = Pattern.compile(TAG_BOOK).matcher(tag);
                                if (matcher.find()) {
                                    currentBookId = Short.parseShort(matcher.group(1));
                                    libraryBooks.add(new Book(currentBookId));
                                }
                            } else if (Pattern.matches(TAG_PRICE, tag)) {
                                Matcher matcher = Pattern.compile(TAG_PRICE).matcher(tag);
                                if (matcher.find()) {
                                    short bookPos = (short) (currentBookId - 1);
                                    if (bookPos >= 0)
                                        libraryBooks.get(bookPos).setPriceCurrency(matcher.group(1));
                                    else System.out.println("No books in library");
                                }
                            } else if (Pattern.matches(TAG_CLOSE, tag) || Pattern.matches(TAG_HEADER, tag)) {
                                System.out.println("Closing tag found");
                            } else {
                                System.out.println("Err in switch");
                            }
                            break;
                    }
                }
            }
            System.out.println(output);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " line read\n");
        }
    }

    public void getBookPos() {
        readFile();
        System.out.println("Current book id in parser is " + currentBookId);
        libraryBooks.get(currentBookId - 1).getBookId();
    }

//    public void getString() {
//        if (bookId != 0)
//            System.out.println(bookId);
//        System.out.println(priceCurrency);
//    }
}
