package org.hampelratte.svdrp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {
    private static final transient Logger LOGGER = LoggerFactory.getLogger(IOUtil.class);

    public static String readFile(String textFile) throws IOException {
        LOGGER.debug("Trying to load file {}", textFile);
        InputStream in = IOUtil.class.getResourceAsStream("/" + textFile);
        Scanner scanner = new Scanner(in, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
            if (scanner.hasNext()) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
