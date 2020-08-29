package zti.projekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopWords {

    public String delete(String text) throws FileNotFoundException, IOException {
        List<String> list = read();
        for (String word : list) {
            text = text.replace(word, " ");
        }
        return text;
    }

    private List<String> read() throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\words.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
}