package edu.wgu.restrel.appointmentsapplication.Utils;

import java.io.File;
import java.io.IOException;

/**
 * This class contains methods that help with reading and writing to text files.
 */
public class FileManager {

    public static void writeToTextFile(String filename, String text) {

        // if no file exists, create it ADD HERE
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error creating file: " + ex.getMessage());
            }
        }

        try {
            java.io.PrintWriter output = new java.io.PrintWriter(new java.io.FileWriter(filename, true));
            output.println(text); // write the text to the file and add a line separator
            output.close();
        } catch (java.io.IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }

    }

    /**
     * This method reads a text file and returns the contents as a string.
     * 
     * @param filename the name of the file to read
     * @return the contents of the file as a string
     */
    public static String readFromFile(String filename) {
        String text = "";
        try {
            java.io.File file = new java.io.File(filename);
            java.util.Scanner input = new java.util.Scanner(file);
            while (input.hasNext()) {
                text += input.nextLine() + "";
            }
        } catch (java.io.IOException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
        }

        return text;

    }

}
