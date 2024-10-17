public class Main {
    public static void main(String[] args) {
        Library myLib = new Library();
        LibraryParser.readFile("random_structure_3.xml", myLib);
        myLib.getAllBookInfo();
        LibraryGenerator.generateXml(myLib);
    }
}