import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LibraryGenerator {
    private static PrintWriter xmlWriter;

    public static Path generateXml(Library lib, String xmlName) {
        Path path = Paths.get(xmlName);
        try {

            xmlWriter = new PrintWriter(xmlName);

            xmlWriter.println("<?xml version='1.0' encoding='utf-8'?>");
            xmlWriter.print("<library>");

            for (int i = 1; i <= lib.getBookCount(); i++) {
                xmlWriter.printf("<%s\"%d\">", Tags.TAG_BOOK, i);
                handleBook(lib.getBook(i));
                xmlWriter.write("</book>");
            }
            xmlWriter.print("</library>");
            System.out.println("XML created successfully at " + path.toAbsolutePath().normalize());

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        } finally {
            if (xmlWriter != null) xmlWriter.close();
        }
        return path;
    }

    private static void handleTag(String tagName, String tagContent) {
        if (tagName != null && tagContent != null)
            xmlWriter.printf("<%s>%s</%s>", tagName, tagContent, tagName);
    }

    private static void handleBook(Book currBook) {
        handleTag(Tags.TAG_TITLE, currBook.getTitle());
        handleTag(Tags.TAG_AUTHOR, currBook.getAuthor());
        if (currBook.getYear() != null) handleTag(Tags.TAG_YEAR, currBook.getYear().toString());
        handleTag(Tags.TAG_GENRE, currBook.getGenre());
        if ((currBook.getPrice() != null) && currBook.getPriceCurrency() != null)
            xmlWriter.printf("<%s\"%s\">%s</price>", Tags.TAG_PRICE, currBook.getPriceCurrency(), currBook.getPrice());
        handleTag(Tags.TAG_ISBN, currBook.getIsbn());
        handleTag(Tags.TAG_LANG, currBook.getLanguage());
        handleTag(Tags.TAG_FORMAT, currBook.getFormat());
        if (currBook.getAwardsCount() != 0) {
            xmlWriter.printf("<%s>", Tags.TAG_AWARDS);
            for (int j = 0; j < currBook.getAwardsCount(); j++)
                handleTag(Tags.TAG_AWARD, currBook.getAward(j));
            xmlWriter.printf("</%s>", Tags.TAG_AWARDS);
        }
    }
}
