import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryParser {

    private short bookId;
    private String priceCurrency;
    private short bookPrice;

    private static final String TAG_BOOK = "book\\s+id=\"(\\d+)\"";
    private static final String TAG_LIBRARY = "library";
    private static final String TAG_TITLE = "title";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_YEAR = "year";
    private static final String TAG_GENRE = "genre";
    private static final String TAG_PRICE = "price\\s+currency=\"([A-Z]+)\"";
    private static final String TAG_ISBN = "isbn";
    private static final String TAG_AWARDS = "awards";
    private static final String TAG_AWARD = "award";
    private static final String TAG_LANG = "language";


    public void readFile() {

        String fileName = "random_structure_3.xml";

        try {
            BufferedReader libReader = new BufferedReader(new FileReader(fileName));
            int symbol;
            StringBuilder output = new StringBuilder();

            try {


                while ((symbol = libReader.read()) != -1) {

                    output.append(Character.toString(symbol));

                    if (output.toString().equals("<?")) {
                        do symbol = (char) libReader.read(); while (symbol != '\n');
                        output.setLength(0);
                    }

                    if (output.toString().equals("<")) {
                        do {
                            output.append((char) libReader.read());
                        } while (symbol != '>');

                        String tag = output.toString();
                        switch (tag) {

                            case TAG_LIBRARY:
                            case TAG_TITLE:
                            case TAG_AUTHOR:
                            case TAG_YEAR:
                            case TAG_GENRE:
                            case TAG_PRICE:
                            case TAG_ISBN:
                            case TAG_AWARDS:
                            case TAG_AWARD:
                            case TAG_LANG:
                            default:
                                if (Pattern.matches(TAG_BOOK, tag)) {
                                    Matcher matcher = Pattern.compile(TAG_BOOK).matcher(tag);
                                    if (matcher.find()) {
                                        bookId = Short.parseShort(matcher.group(1));
                                    }
                                } else if (Pattern.matches(TAG_PRICE, tag)) {
                                    Matcher matcher = Pattern.compile(TAG_PRICE).matcher(tag);
                                    if (matcher.find()) {
                                        priceCurrency = matcher.group(1);
                                    }
                                } else {

                                }

                        }
                    }

                }
                System.out.println(output);
            } catch (IOException e) {
                System.out.println(e.getMessage() + " line read\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + " file read\n");
        }
    }
}
