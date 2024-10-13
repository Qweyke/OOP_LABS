import java.util.ArrayList;

public class Book {

    private final int thisId;
    private String title;
    private String author;
    private int year;
    private String genre;
    private String priceCurrency;
    private int price;
    private String isbn;
    private String format;
    private String language;
    private ArrayList<String> awards;


    public Book(int id) {
        thisId = id;
    }

    public void setTitle(String bookTitle) {
        title = bookTitle;
    }

    public void setAuthor(String bookAuthor) {
        author = bookAuthor;
    }

    public void setGenre(String bookGenre) {
        genre = bookGenre;
    }

    public void setIsbn(String bookIsbn) {
        isbn = bookIsbn;
    }

    public void setLanguage(String bookLanguage) {
        language = bookLanguage;
    }

    public void setFormat(String bookFormat) {
        format = bookFormat;
    }

    public void setYear(int bookYear) {
        year = bookYear;
    }

    public void setAwardsArray() {
        awards = new ArrayList<>();
    }

    public void setAward(String bookAward) {
        awards.add(bookAward);
    }

    public void setPriceCurrency(String bookPriceCurrency) {
        priceCurrency = bookPriceCurrency;
    }

    public void setPrice(int bookPrice) {
        price = bookPrice;
    }

    public void getFullInfo() {
        System.out.println("\nBook[" + thisId + "] \"" + title + "\":");
        if (author != null) System.out.println("Author - " + author);
        if (year != 0) System.out.println("Year - " + year);
        if (genre != null) System.out.println("Genre - " + genre);
        if (price != 0 && (priceCurrency != null)) System.out.println("Price - " + price + priceCurrency);
        if (isbn != null) System.out.println("Isbn - " + isbn);
        if (format != null) System.out.println("Format - " + format);

        if (awards != null) {
            System.out.print("Awards - ");
            for (String award : awards) {
                if (awards.size() == 1) {
                    System.out.println(award);
                    break;
                }
                System.out.println(award + ", ");
            }
        }
        if (language != null) System.out.println("Language - " + language);


    }
}
