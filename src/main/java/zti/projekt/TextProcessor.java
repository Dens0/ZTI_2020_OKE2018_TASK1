package zti.projekt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class TextProcessor {

    private static final short WINDOW_SIZE = 3;

    public HashMap<String, String> processText(String input) throws FileNotFoundException, IOException {
        StopWords stopWords = new StopWords();
        String deleted = stopWords.delete(input);
        String[] splited = deleted.split(" ");
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = WINDOW_SIZE; i > 0; i--) {
            splited = filterText(splited);
            splited = checkText(splited, i, hashMap);
        }

        return hashMap;
    }



    public String[] checkText(String[] text,int windowSize,HashMap<String,String> entities)
    {
        boolean textEnd = false;
        int windowPosition = 0;
        List<Integer> indexesWhereFoundType = new ArrayList<>();
        while(!textEnd)
        {
            boolean windowEnd = false;
            int currentWordIndex = windowPosition;
            StringBuilder textToCheck = new StringBuilder();
            RDFservice rdf = new RDFservice();
            while(!windowEnd)
            {
                if(currentWordIndex < text.length )
                {
                    if(currentWordIndex - windowPosition < windowSize)
                    {
                        textToCheck.append(text[currentWordIndex] + "_" );
                        currentWordIndex += 1;
                    }
                    else
                    {
                        windowEnd = true;
                        textToCheck = textToCheck.delete(textToCheck.length()-1, textToCheck.length());
                        String phrase = textToCheck.toString();
                        Optional<String> foundType = rdf.check(phrase);
                        if(foundType.isPresent())
                        {
                            entities.put(phrase, foundType.get());
                            for(int i = 1;i<=windowSize;i++)
                            {
                                indexesWhereFoundType.add(currentWordIndex - i);
                            }
                        }
                        windowPosition += 1;
                    }
                } else
                {
                    textEnd = true;
                    windowEnd = true;
                    textToCheck = textToCheck.delete(textToCheck.length()-1, textToCheck.length());
                    String phrase = textToCheck.toString();
                    Optional<String> foundType = rdf.check(phrase);
                    if(foundType.isPresent())
                    {
                        entities.put(phrase, foundType.get());
                        for(int i = 0;i<windowSize;i++)
                        {
                            indexesWhereFoundType.add(windowPosition - i);
                        }
                    }
                }
            }

        }
        String[] nonRecognizedText = text;

        for(Integer index : indexesWhereFoundType)
        {
            nonRecognizedText[index] = "";
        }

        return nonRecognizedText;

    }


    private String[] filterText(String[] splited) {
        return (String[]) Arrays.stream(splited).filter(word -> {
            if (word.length() > 1)
                return true;
            else
                return false;
        }).map(word -> {
            return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }).toArray(String[]::new);
    }
}
