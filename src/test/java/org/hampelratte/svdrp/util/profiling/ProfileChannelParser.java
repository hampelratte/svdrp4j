package org.hampelratte.svdrp.util.profiling;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import org.hampelratte.svdrp.Connection;
import org.hampelratte.svdrp.Response;
import org.hampelratte.svdrp.commands.LSTC;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.util.ChannelParser;

public class ProfileChannelParser {
    
    public ProfileChannelParser() {
        Connection conn = null;
        try {
            conn = new Connection("192.168.0.1", 2001, 500, "utf-8");
            for (int i = 0; i < 10; i++) {
                Response resp = conn.send(new LSTC());
                long start = System.currentTimeMillis();
                List<Channel> chans = ChannelParser.parse(resp.getMessage(), false);
                long stop = System.currentTimeMillis();
                System.out.println("Parsed " + chans.size() + " channels in " + (stop - start) + " ms");

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        new ProfileChannelParser();
    }
}
