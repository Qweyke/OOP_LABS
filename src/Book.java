public class Book {

    private short bookId;
    private String title;
    private String author;
    private short year;
    private String genre;
    private String priceCurrency;
    private short price;
    private String isbn;
    private String format;
    private String language;
    private String[] awards;
    private short awardsCount = 0;

    public Book(short id) {
        bookId = id;
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

    public void setYear(short bookYear) {
        year = bookYear;
    }

    public void setAward(String bookAward) {
        awards[awardsCount] = bookAward;
        awardsCount++;
    }

    public void setPriceCurrency(String bookPriceCurrency) {
        priceCurrency = bookPriceCurrency;
    }

    public void setPrice(short bookPrice) {
        price = bookPrice;
    }


//    private void toString {
//
//    }
}
