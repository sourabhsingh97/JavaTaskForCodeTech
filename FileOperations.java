import java.io.*;
import java.util.*;

public class FileOperations {

    // Path of the file to be used
    static String filePath = "sample.txt";

    public static void main(String[] args) {
        try {
            // 1. Write content to the file
            writeToFile("This is the original content.\nThis is line 2.\n");

            // 2. Read and display the current file content
            System.out.println("Reading file after writing:");
            readFile();

            // 3. Modify the file content
            modifyFile("original", "modified");

            // 4. Read and display the modified file content
            System.out.println("\nReading file after modification:");
            readFile();
            

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to write to a file
    public static void writeToFile(String content) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
        System.out.println("File written successfully.");
    }

    // Method to read a file
    public static void readFile() throws IOException {
        FileReader reader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        bufferedReader.close();
    }

    // Method to modify text in the file (replaces target word with replacement)
    public static void modifyFile(String target, String replacement) throws IOException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        StringBuilder modifiedContent = new StringBuilder();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            modifiedContent.append(line.replaceAll(target, replacement)).append("\n");
        }

        scanner.close();

        FileWriter writer = new FileWriter(file);
        writer.write(modifiedContent.toString());
        writer.close();

        System.out.println("File modified successfully.");
    }
}
