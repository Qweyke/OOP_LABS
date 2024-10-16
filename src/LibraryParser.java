import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

//java sucks :)
public class LibraryParser {
    private static BufferedReader libReader;
    private static int _currentBookId;

    private static Library userLibrary;


    public static void readFile(String fileName, Library library) {

        try {
            libReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        StringBuilder output = new StringBuilder();
        userLibrary = library;

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
                    output.setLength(0);
//                    System.out.println("Processing tag: " + tag);

                    if (tag.startsWith("<book")) {
                        Matcher matcher = Tags.PATTERN_BOOK.matcher(tag);
                        if (matcher.find()) {
                            _currentBookId = Integer.parseInt(matcher.group(1));
                            library.addBook(new Book(), _currentBookId);
                        }
                    } else if (tag.startsWith("</") || tag.startsWith("<?")) {

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