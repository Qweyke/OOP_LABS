import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

//java sucks :)
public class LibraryParser {
    private static BufferedReader libReader;
    private static int _currentBookId;
    private static Library userLibrary;


    public static void readFile(String fileName, Library library) {
        userLibrary = library;
        if ((userLibrary) == null) throw new IllegalArgumentException("Null pointer lib");

        try (BufferedReader libReader = new BufferedReader(new FileReader(fileName))) {
            String libLine;
            while ((libLine = libReader.readLine()) != null) processLibLine(libLine);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        if ((userLibrary = library) == null) System.out.println("Null pointer lib");
    }

    private static void processLibLine(String line) {

        if (line.startsWith("<?") || line.isEmpty()) return;

        StringBuilder tagBuilder = new StringBuilder();
        StringBuilder contentBuilder = new StringBuilder();

        boolean tagOpened = false;
        for (char symbol : line.toCharArray())
            if (symbol == '<') {
                tagOpened = true;
                if (!tagBuilder.toString().isEmpty()) {
                    processTagContent(tagBuilder.toString(), contentBuilder.toString());
                    tagBuilder.setLength(0);
                    contentBuilder.setLength(0);
                }
                tagBuilder.append(symbol);
            } else if (symbol == '>') {
                tagOpened = false;

                tagBuilder.append(symbol);
            } else if (tagOpened) {
                tagBuilder.append(symbol);
            } else contentBuilder.append(symbol);
    }

    private static void processTagContent(String tag, String tagContent) {

        if (tag.startsWith("<book")) {
            Matcher matcher = Tags.PATTERN_BOOK.matcher(tag);
            if (matcher.find()) {
                _currentBookId = Integer.parseInt(matcher.group(1));
                userLibrary.addBook(new Book(), _currentBookId);
            }
        } else if (!tag.startsWith("</")) {
            handleTagContent(tag, tagContent);
        }
    }

    private static void handleTagContent(String tag, String tagContent) {
        switch (tag) {

            case Tags.TAG_TITLE:
                userLibrary.getBook((_currentBookId)).setTitle(tagContent);
                break;
            case Tags.TAG_AUTHOR:
                userLibrary.getBook((_currentBookId)).setAuthor(tagContent);
                break;
            case Tags.TAG_YEAR:
                userLibrary.getBook((_currentBookId)).setYear(Integer.parseInt(tagContent));
                break;
            case Tags.TAG_GENRE:
                userLibrary.getBook((_currentBookId)).setGenre(tagContent);
                break;
            case Tags.TAG_ISBN:
                userLibrary.getBook((_currentBookId)).setIsbn(tagContent);
                break;
            case Tags.TAG_FORMAT:
                userLibrary.getBook((_currentBookId)).setFormat(tagContent);
                break;
            case Tags.TAG_AWARDS:
                userLibrary.getBook((_currentBookId)).setAwardsArray();
                break;
            case Tags.TAG_AWARD:
                userLibrary.getBook((_currentBookId)).setAward(tagContent);
                break;
            case Tags.TAG_LANG:
                userLibrary.getBook((_currentBookId)).setLanguage(tagContent);
                break;
            case Tags.TAG_LIBRARY:
                System.out.println("Library found!");
                break;
            default:

                if (tag.startsWith("<price")) {
                    Matcher matcher = Tags.PATTERN_PRICE.matcher(tag);
                    if (_currentBookId >= 1 && matcher.find()) {
                        userLibrary.getBook((_currentBookId)).setPriceCurrency(matcher.group(1));
                        userLibrary.getBook((_currentBookId)).setPrice(Double.parseDouble(tagContent));
                    } else System.out.println("No books in library!");

                } else
                    System.out.println("Library not found!");
                break;
        }
    }
}