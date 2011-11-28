package org.hampelratte.svdrp.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hampelratte.svdrp.parsers.GroupChannelLineParser;
import org.hampelratte.svdrp.responses.highlevel.Channel;
import org.hampelratte.svdrp.responses.highlevel.ChannelGroup;
import org.junit.Test;

public class GroupChannelLineParserTest {

    @Test
    public void testChannelGroup() {
        String line = "0 :Group";

        Channel cg = new GroupChannelLineParser().parse(line);
        assertTrue(cg instanceof ChannelGroup);
        assertEquals("Group", cg.getName());
        assertEquals(0, cg.getChannelNumber());
    }
}
