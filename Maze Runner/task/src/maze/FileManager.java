package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private File file;
    private String fileName;
    public FileManager(String fileName) {
        this.fileName = fileName;
        file = new File(fileName);
    }

    public void save(ArrayList<String> lines) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line: lines) {
                printWriter.println(line);
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public ArrayList<String> load() throws FileNotFoundException {
        ArrayList<String> content = new ArrayList<String>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            content.add(line);
        }

        scanner.close();
        return content;
    }
}
