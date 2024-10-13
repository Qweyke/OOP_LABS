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

    private int _currentBookId;
    private int _currentBookPos;
    ArrayList<Book> libraryBooks;
    private static final String TAG_BOOK = "<book\\s+id=\"(\\d+)\">";
    private static final String TAG_LIBRARY = "<library>";
    private static final String TAG_TITLE = "<title>";
    private static final String TAG_AUTHOR = "<author>";
    private static final String TAG_YEAR = "<year>";
    private static final String TAG_GENRE = "<genre>";
    private static final String TAG_PRICE = "<price\\s+currency=\"([A-Z]+)\">";
    private static final String TAG_ISBN = "<isbn>";
    private static final String TAG_FORMAT = "<format>";
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


                    if (Pattern.matches(TAG_BOOK, tag)) {
                        Matcher matcher = Pattern.compile(TAG_BOOK).matcher(tag);
                        if (matcher.find()) {
                            _currentBookId = Short.parseShort(matcher.group(1));
                            _currentBookPos = _currentBookId - 1;
                            libraryBooks.add(new Book(_currentBookId));
                        }

                    } else if (Pattern.matches(TAG_CLOSE, tag) || Pattern.matches(TAG_HEADER, tag)) {
                        System.out.println("Closing tag found");

                    } else {

                        libReader.mark(1);
                        while ((symbol = libReader.read()) != '<') {

                            libReader.mark(1);
                            output.append((char) symbol);
                        }
                        libReader.reset();
                        String tagContent = output.toString();
                        output.setLength(0);

                        handleTagContent(tag, tagContent);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleTagContent(String tag, String tagContent) {
        switch (tag) {

            case TAG_TITLE:
                libraryBooks.get((_currentBookPos)).setTitle(tagContent);
                break;
            case TAG_AUTHOR:
                libraryBooks.get((_currentBookPos)).setTitle(tagContent);
            case TAG_YEAR:
            case TAG_GENRE:
            case TAG_PRICE:
            case TAG_ISBN:
            case TAG_FORMAT:
            case TAG_LIBRARY:
            case TAG_AWARDS:
            case TAG_AWARD:
            case TAG_LANG:
                break;
            default:
                if (Pattern.matches(TAG_PRICE, tag)) {
                    Matcher matcher = Pattern.compile(TAG_PRICE).matcher(tag);
                    if (matcher.find()) {
                        if (_currentBookPos >= 0)
                            libraryBooks.get(_currentBookPos).setPriceCurrency(matcher.group(1));
                        else System.out.println("No books in library!");
                    }
                } else
                    System.out.println("Error in tag handle!");
                break;
        }
    }

    public void getBookPos() {
        readFile();
        System.out.println("Current book id in parser is " + libraryBooks.get(_currentBookPos).getId());
        System.out.println("Title is " + (libraryBooks.get(_currentBookPos).getTitle()));

    }
}
