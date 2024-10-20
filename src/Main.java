import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Library myLib = new Library();
        LibraryParser.readFile("random_structure_3.xml", myLib);
        myLib.getAllBookInfo();

        Path newXml = LibraryGenerator.generateXml(myLib, "lib.xml");

        LibraryValidator.validateXml(newXml.toString(), "lib_schema.xsd");
    }
}