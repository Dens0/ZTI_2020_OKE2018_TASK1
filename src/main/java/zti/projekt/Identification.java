package zti.projekt;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Identification extends Service<String> {

    private final String input;

    public Identification(String textInput) {
        input = textInput;
    }

    @Override
    protected Task<String> createTask() {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                Parser parser = new Parser();
                String rawText = parser.getFromString(input);
                StopWords stopWords = new StopWords();
                String text = null;

                try {
                    text = stopWords.delete(rawText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TextProcessor textProcessor = new TextProcessor();
                HashMap<String, String> entities = null;
                try {
                    entities = textProcessor.processText(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                StringBuilder builder = new StringBuilder();
                builder.append(input + "\r\n");

                rawText = rawText.toLowerCase();

                Pattern pattern = Pattern.compile("<.*[0-9]>");
                Matcher matcher = pattern.matcher(input);
                String taskLink = "";
                if (matcher.find())
                {
                    taskLink = matcher.group();
                }
                String regex ="char=[0-9]*,[0-9]*";
                Pattern charPattern = Pattern.compile(regex);
                Matcher charMatcher = charPattern.matcher(taskLink);
                String charSection = "";
                if (charMatcher.find())
                {
                    charSection = charMatcher.group();
                }
                for(Map.Entry<String, String> pair : entities.entrySet())
                {
                    int start = rawText.indexOf(pair.getKey().toLowerCase().replace("_", " "));
                    int stop = start + pair.getKey().length();
                    builder.append(taskLink.replaceAll(charSection, "char="+ start + "," + stop) + "\r\n");
                    builder.append("        a                     nif:RFC5147String , nif:String , nif:Context ;\r\n");
                    builder.append("        nif:anchorOf           \"" + pair.getKey() + "\"@en ;\r\n") ;
                    builder.append("        nif:beginIndex         \"" + start +"\"^^xsd:nonNegativeInteger ;\r\n");
                    builder.append("        nif:endIndex           \"" + stop + "\"^^xsd:nonNegativeInteger ;\r\n");
                    builder.append("        itsrdf:taIdentRef     dbpedia:"+ pair.getKey() + " .\r\n");
                }

                return builder.toString();

            }

        };
    }

}
