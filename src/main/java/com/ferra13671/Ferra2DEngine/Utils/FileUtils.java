package com.ferra13671.Ferra2DEngine.Utils;

import com.ferra13671.Ferra2DEngine.Logger.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility to simplify work with various files.
 *
 * @author Ferra13671
 */

public class FileUtils {

    private final static Logger logger = new Logger(FileUtils.class);


    /**
     * Creates a file at the specified path.
     *
     * @param path   The path where the file will be created.
     */
    public static void createFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                logger.logError(e.toString());
            }
        } else
            logger.logInfo(path + " file already exists.");
    }


    /**
     * Trying to open the entered link, if it fails, it reports it to the logger.
     *
     * @param uri   Required link.
     */
    public static void openURI(URI uri) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) {
                logger.logError("An error occurred while trying to open the '" + uri.toString() + "' link!");
            }
        }
    }


    /**
     * Reads the file at the entered path and returns all lines as a single String variable.
     *
     * @param path   The path from which the file will be fetched.
     * @return   All text lines in this file.
     */
    public static String fileToString(String path) {
        String out = "";
        try {
            Path path1 = Paths.get(path);
            if (Files.exists(path1)) {
                BufferedReader reader = Files.newBufferedReader(path1, StandardCharsets.UTF_8);
                String line = reader.readLine();
                while (line != null) {
                    out = out + "\n" +line;
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            logger.logError("An error occurred while trying to read a file on a path: " + path);
        }
        return out;
    }

    /**
     * Reads the file at the specified path and returns all lines up to (and including) the specified line as a String variable.
     *
     * @param path   The path from which the file will be fetched.
     * @param endLine   End Line Number. All lines up to and including it will be read.
     * @return   All the lines that have been read.
     */
    public static String fileToString(String path, int endLine) {
        String out = "";
        try {
            Path path1 = Paths.get(path);
            if (Files.exists(path1)) {
                BufferedReader reader = Files.newBufferedReader(path1, StandardCharsets.UTF_8);
                String line = reader.readLine();
                for (int i = 0; i < endLine; i++) {
                    if (line != null) {
                        out = out + "\n" + line;
                        line = reader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            logger.logError("An error occurred while trying to read a file on a path: " + path);
        }
        return out;
    }

    /**
     * Reads the file at the specified path and returns all lines starting with {@code startLine}(inclusive)
     * and ending with {@code endLine}(inclusive) as a String variable.
     *
     * @param path   The path from which the file will be fetched.
     * @param startLine   Initial line number.
     * @param endLine   End Line Number.
     * @return   All the lines that have been read.
     */
    public static String fileToString(String path, int startLine, int endLine) {
        String out = "";
        try {
            Path path1 = Paths.get(path);
            if (Files.exists(path1)) {
                BufferedReader reader = Files.newBufferedReader(path1, StandardCharsets.UTF_8);
                String line = "";
                for (int i = 0; i < startLine; i++) {
                    line = reader.readLine();
                }
                for (int i = 0; i < endLine; i++) {
                    if (line != null) {
                        out = out + "\n" + line;
                        line = reader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            logger.logError("An error occurred while trying to read a file on a path: " + path);
        }
        return out;
    }


    /**
     * Gets the file extension(text after the last comma).
     *
     * @param filePath   The name or path to the file.
     * @return   File Extension.
     */
    public static String getFileExtension(String filePath) {
        int index = filePath.indexOf('.');
        return index == -1? null : filePath.substring(index).replace(".", "");
    }


    /**
     * Checks the file extension. If it is the extension of the picture, it returns true, if not false.
     *
     * @param path   The name or path of the file.
     * @return   Whether the file is a picture or not.
     */
    public static boolean isImage(String path) {
        String extension = getFileExtension(path);
        if (extension != null) {
            switch (extension) {
                case "jpeg":
                case "png":
                case "ico":
                case "gif":
                case "tiff":
                case "tif":
                case "webP":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }


    /**
     * Checks the file extension. If it matches the text file extension, it returns true, if not, it returns false.
     *
     * @param path   The name or path of the file.
     * @return   Whether the file is a text file or not.
     */
    public static boolean isTextFile(String path) {
        String extension = getFileExtension(path);
        if (extension != null) {
            switch (extension) {
                case "txt":
                case "doc":
                case "rtf":
                case "pdf":
                case "log":
                case "tex":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }


    /**
     * Returns the number of times the entered word occurs in the text.
     * <p>
     * <a href="https://ru.stackoverflow.com/questions/608644">Original code</a>
     *
     * @param text   Text that needs to be analyzed.
     * @param word   The word that will be used to analyze.
     * @return How many times the word occurs in the text.
     */
    public static long numberOfWords(String text, String word) {
        String[] words = text.split("\\s+");
        long numbers = 0;
        for (String s : words)
        {
            if (s.equals(word))  numbers++;
        }
        return numbers;
    }
}
