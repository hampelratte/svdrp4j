package org.hampelratte.svdrp.commands;

import org.junit.Assert;
import org.junit.Test;

public class QUITTest {

    @Test
    public void testCommand() {
        Assert.assertEquals("QUIT", new QUIT().getCommand());
    }

    @Test
    public void testToString() {
        Assert.assertEquals("QUIT", new QUIT().toString());
    }
}
