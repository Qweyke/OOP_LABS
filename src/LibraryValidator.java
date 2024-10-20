import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class LibraryValidator {
    public static void validateXml(String pathToXml, String pathToXsd) {
        try {
        SchemaFactory libSchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema libSchema = libSchemaFactory.newSchema(new File(pathToXsd));
        Validator libValidator = libSchema.newValidator();
        libValidator.validate(new StreamSource(new File(pathToXml)));
            System.out.println("Your library XML is valid.");
        } catch (SAXException e) {
            System.out.println("Your library XML isn't valid against XSD " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading XML " + e.getMessage());
        }
    }
}
