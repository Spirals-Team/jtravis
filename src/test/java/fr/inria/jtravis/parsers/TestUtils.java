package fr.inria.jtravis.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by urli on 22/02/2017.
 */
public class TestUtils {

    public static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        StringBuilder result = new StringBuilder();

        while (reader.ready()) {
            result.append(reader.readLine());
            result.append("\n");
        }
        return result.toString();
    }
}
