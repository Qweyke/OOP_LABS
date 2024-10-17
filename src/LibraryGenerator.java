import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class LibraryGenerator {
    private static int BOOK_TAGS = 11;
    private static Stack<String> tagStack;
    private static PrintWriter xmlWriter;

    public static void generateXml(Library lib) {
        tagStack = new Stack<>();
        tagStack.add("</library>");

        try {
            xmlWriter = new PrintWriter("library.xml");
        } catch (IOException e) {
            System.out.println(e);
        }

        xmlWriter.println("<?xml version='1.0' encoding='utf-8'?>");
        xmlWriter.print("<library>");

        for (int i = 1; i <= lib.getBookCount(); i++) {
            fillStack(lib.getBook(i));
        }
    }

    private static void fillTags(Book currBook) {
        for (int i = 0; i < BOOK_TAGS; i++) {
            if (currBook.getTitle() != null)
                xmlWriter.print(Tags.TAG_TITLE + currBook.getTitle() + tagStack.pop());
            if (currBook.getAuthor() != null)
                xmlWriter.print(Tags.TAG_AUTHOR + currBook.getAuthor() + tagStack.pop());
            if (currBook.getYear() != null)
                xmlWriter.print(Tags.TAG_YEAR + currBook.getYear() + tagStack.pop());
            if (currBook.getGenre() != null)
                xmlWriter.print(Tags.TAG_GENRE + currBook.getGenre() + tagStack.pop());
            if ((currBook.getPrice() != null) && currBook.getPriceCurrency() != null)
                xmlWriter.print(Tags.TAG_PRICE + "\"" + "\"" + currBook.getPriceCurrency() + tagStack.pop());
            if (currBook.getIsbn() != null) xmlWriter.print(Tags.TAG_ISBN + currBook.getIsbn() + tagStack.pop());
            if (currBook.getFormat() != null) xmlWriter.print(Tags.TAG_FORMAT + currBook.getFormat() + tagStack.pop());
            if (currBook.getLanguage() != null)
                xmlWriter.print(Tags.TAG_LIBRARY + currBook.getLanguage() + tagStack.pop());


        }

    }

    private static void fillStack(Book currBook) {
        for (int i = 0; i < BOOK_TAGS; i++) {
            tagStack.add("</book>");
            if (currBook.getAwardsCount() != 0) {
                tagStack.add("</awards>");
                for (int j = 0; j < currBook.getAwardsCount(); j++) {
                    tagStack.add("</award>");
                }
                if (currBook.getLanguage() != null) tagStack.add("</language>");
                if (currBook.getFormat() != null) tagStack.add("</format>");
                if (currBook.getIsbn() != null) tagStack.add("</isbn>");
                if ((currBook.getPrice() != null) && currBook.getPriceCurrency() != null) tagStack.add("</price>");
                if (currBook.getGenre() != null) tagStack.add("</genre>");
                if (currBook.getYear() != null) tagStack.add("</year>");
                if (currBook.getAuthor() != null) tagStack.add("</author>");
                if (currBook.getTitle() != null) tagStack.add("</title>");
            }


        }
    }
}
