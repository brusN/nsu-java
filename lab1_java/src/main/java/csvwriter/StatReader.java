package csvwriter;

import java.io.*;
import java.util.*;

public class StatReader {
    private HashMap<String, Integer> words;
    private int wordCount;

    public StatReader() {
        words = new HashMap<>();
        wordCount = 0;
    }

    public HashMap<String, Integer> getMap() {
        return words;
    }

    public int getCount() {
        return wordCount;
    }

    public void reset() {
        words.clear();
        wordCount = 0;
    }

    public void fillMap(FileInputStream file) {
        wordCount = 0;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file))) {
            String fileLine = fileReader.readLine();
            while (fileLine != null) {
                String[] lineWords = fileLine.split("([^A-Za-z0-9]+)");
                for (String lineWord : lineWords) {
                    words.put(lineWord, words.getOrDefault(lineWord, 0) + 1);
                    ++wordCount;
                }
                fileLine = fileReader.readLine();
            }
        } catch (IOException exception) {
            System.err.println("Error while reading file: " + exception.getLocalizedMessage());
        }
    }

    public void sortByValueMap() {
        List<HashMap.Entry<String, Integer>> list = new ArrayList<>(words.entrySet());
        list.sort(HashMap.Entry.comparingByValue(Comparator.reverseOrder()));

        HashMap <String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        words = new LinkedHashMap<>(result);
    }
}
