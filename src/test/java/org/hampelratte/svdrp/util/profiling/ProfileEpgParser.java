package org.hampelratte.svdrp.util.profiling;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Response;
import org.hampelratte.svdrp.commands.LSTE;
import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.hampelratte.svdrp.util.EPGParser;

public class ProfileEpgParser {

    public ProfileEpgParser() {
        Connection conn = null;
        try {
            conn = new Connection("192.168.0.1", 2001, 500, "utf-8");
            Response resp = conn.send(new LSTE());
            long start = System.currentTimeMillis();
            List<EPGEntry> entries = EPGParser.parse(resp.getMessage());
            long stop = System.currentTimeMillis();
            System.out.println("Parsed " + entries.size() + " entries in " + (stop - start) + " ms");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ProfileEpgParser();
    }
}
