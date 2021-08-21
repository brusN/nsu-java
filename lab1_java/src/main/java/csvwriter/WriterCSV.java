package csvwriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface WriterCSV {
    static void printStats(StatReader statReader, FileWriter outputFile) throws IOException {
        var mapCollection = new LinkedHashMap<>(statReader.getMap());
        int countWords = statReader.getCount();
        int keyValue = 0;
        for (HashMap.Entry<String, Integer> entry : mapCollection.entrySet()) {
            keyValue = entry.getValue();
            outputFile.append(
                    entry.getKey() + ";"
                    + keyValue + ";"
                    + String.format("%.2f", (double)(keyValue * 100) / countWords) + "%" + "\n"
            );
        }
        outputFile.flush();
        outputFile.close();
    }
}
