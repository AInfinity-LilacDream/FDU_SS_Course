package data;

import java.io.File;

public class FileProcessor {
    public static Boolean fileExists(String folderPath, String filename) {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return false;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(filename)) {
                    return true;
                }
            }
        }
        return false;
    }
}
