package zti.projekt;

import org.nlp2rdf.parser.Document;
import org.nlp2rdf.parser.NIFParser;

public class Parser {

    public String getFromString(String text)
    {
        Document document = NIFParser.getDocumentFromNIFString(text);
        return document.getText();
    }

}
