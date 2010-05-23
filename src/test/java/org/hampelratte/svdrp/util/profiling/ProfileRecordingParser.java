package org.hampelratte.svdrp.util.profiling;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Response;
import org.hampelratte.svdrp.commands.LSTR;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.util.RecordingsParser;

public class ProfileRecordingParser {

    public ProfileRecordingParser() {
        Connection conn = null;
        try {
            conn = new Connection("192.168.0.1", 2001, 500, "utf-8");
            Response resp = conn.send(new LSTR());
            for (int i = 0; i < 100; i++) {
                long start = System.currentTimeMillis();
                List<Recording> recordings = RecordingsParser.parse(resp.getMessage());
                long stop = System.currentTimeMillis();
                System.out.println("Parsed " + recordings.size() + " recordings in " + (stop - start) + " ms");
            }
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
        new ProfileRecordingParser();
    }
}
