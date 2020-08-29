package zti.projekt;

import org.apache.commons.io.IOUtils;
import org.nlp2rdf.parser.Document;
import org.nlp2rdf.parser.NIFParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Parser {

    public String getFromFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Document document = NIFParser.getDocumentFromNIFString(text);
        return document.getText();
    }

    public String getFromString(String text)
    {
        Document document = NIFParser.getDocumentFromNIFString(text);
        return document.getText();
    }

    public String parse(String input) {
        Document document = NIFParser.getDocumentFromNIFString(input);
        return document.getText();
    }

}
