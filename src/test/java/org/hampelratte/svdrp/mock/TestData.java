package org.hampelratte.svdrp.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestData {
    private static final transient Logger logger = LoggerFactory.getLogger(TestData.class);

    public static String readFile(String textFile) throws IOException {
        logger.debug("Trying to load file {}", textFile);
        InputStream in = TestData.class.getResourceAsStream("/" + textFile);
        Scanner scanner = new Scanner(in, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            if (scanner.hasNext()) {
                sb.append('\n');
            }
        }
        scanner.close();
        return sb.toString();
    }
}