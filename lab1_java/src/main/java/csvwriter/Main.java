package csvwriter;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Getting read file path
        String filePath = args[0];
        FileInputStream inputFile = new FileInputStream(filePath);

        // Reading and counting words from read file
        StatReader inputFileStatReader = new StatReader();
        inputFileStatReader.fillMap(inputFile);
        inputFileStatReader.sortByValueMap();

        // Writing stats in CSV file, args[1] is output file path
        FileWriter outputFile = new FileWriter(new File(args[1]));
        WriterCSV.printStats(inputFileStatReader, outputFile);

        inputFile.close();
        outputFile.close();
    }
}

