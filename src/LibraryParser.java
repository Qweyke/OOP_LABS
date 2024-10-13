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
//                    System.out.println(tag);
                    output.setLength(0);


                    if (Pattern.matches(Tags.TAG_BOOK, tag)) {
                        Matcher matcher = Pattern.compile(Tags.TAG_BOOK).matcher(tag);
                        if (matcher.find()) {
                            _currentBookId = Short.parseShort(matcher.group(1));
                            _currentBookPos = _currentBookId - 1;
                            libraryBooks.add(new Book(_currentBookId));
                        }

                    } else if (Pattern.matches(Tags.TAG_CLOSE, tag) || Pattern.matches(Tags.TAG_HEADER, tag)) {
//                        System.out.println("Closing tag found");

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

            case Tags.TAG_TITLE:
                libraryBooks.get((_currentBookPos)).setTitle(tagContent);
                break;
            case Tags.TAG_AUTHOR:
                libraryBooks.get((_currentBookPos)).setAuthor(tagContent);
                break;
            case Tags.TAG_YEAR:
                libraryBooks.get((_currentBookPos)).setYear(Integer.parseInt(tagContent));
                break;
            case Tags.TAG_GENRE:
                libraryBooks.get((_currentBookPos)).setGenre(tagContent);
                break;
            case Tags.TAG_PRICE:
                libraryBooks.get((_currentBookPos)).setPrice(Integer.parseInt(tagContent));
                break;
            case Tags.TAG_ISBN:
                libraryBooks.get((_currentBookPos)).setIsbn(tagContent);
                break;
            case Tags.TAG_FORMAT:
                libraryBooks.get((_currentBookPos)).setFormat(tagContent);
                break;
            case Tags.TAG_AWARDS:
                libraryBooks.get((_currentBookPos)).setAwardsArray();
                break;
            case Tags.TAG_AWARD:
                libraryBooks.get((_currentBookPos)).setAward(tagContent);
                break;
            case Tags.TAG_LANG:
                libraryBooks.get((_currentBookPos)).setLanguage(tagContent);
                break;
            case Tags.TAG_LIBRARY:
                System.out.println("Library found!");
                break;
            default:
                if (Pattern.matches(Tags.TAG_PRICE, tag)) {
                    Matcher matcher = Pattern.compile(Tags.TAG_PRICE).matcher(tag);
                    if (matcher.find()) {
                        if (_currentBookPos >= 0)
                            libraryBooks.get(_currentBookPos).setPriceCurrency(matcher.group(1));
                        else System.out.println("No books in library!");
                    }
                } else
                    System.out.println("Library not found!");
                break;
        }
    }

    public void examineBooks() {
        readFile();
        for (int i = 0; i < _currentBookId; i++) {
            libraryBooks.get(i).getFullInfo();
        }

    }
}