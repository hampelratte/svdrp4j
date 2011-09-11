package org.hampelratte.svdrp.util;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class Base64Test {

    @Test
    public void testDecode() throws IOException {
        Assert.assertEquals("Hello World!", new String(Base64.decode("SGVsbG8gV29ybGQh")));
    }
}
