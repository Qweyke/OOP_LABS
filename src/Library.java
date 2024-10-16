import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<Integer, Book> books;

    public Library() {
        books = new HashMap<>();
    }

    public void addBook(Book book, int bookId) {
        books.put(bookId, book);
    }

    public Book getBook(int bookId) {
        return books.get(bookId);
    }

    public int getBookCount() {
        return books.size();
    }

    public String getBookInfo(int bookId) {
        return books.get(bookId).getStringFullInfo();
    }

    public void getAllBookInfo() {
        if (books.size() > 0) {
            StringBuilder libBuilder = new StringBuilder();
            for (Map.Entry<Integer, Book> entry : books.entrySet()) {
                Integer key = entry.getKey();
                Book book = entry.getValue();

                libBuilder.append("[").append(key).append("] ");
                libBuilder.append(book.getStringFullInfo());

                if (key < books.size()) {
                    libBuilder.append("; ");
                }
            }
            libBuilder.append(".");
            System.out.println(libBuilder);
        } else {
            System.out.println("Library is empty!");
        }
    }
}


