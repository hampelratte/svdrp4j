package org.hampelratte.svdrp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.junit.Ignore;
import org.junit.Test;

public class ConnectionTimeoutTest {
    
    @Ignore("Connection timeout test disabled for now.")
    @Test(expected = ConnectException.class)
    public void testConnectionTimeout() throws UnknownHostException, IOException {
        new Connection("localhost", 2001, 100);
    }
}
