package org.hampelratte.svdrp.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.EPGEntry;
import org.junit.Test;

public class EpgParserTest {

    private static final String epgData = 
        "C S19.2E-133-5-1793 Channel Name\n" + 
        "E 12667 1274605200 7200 50 FF\n" + 
        "T Program Title\n" + 
        "D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.|Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..\n" +
        "S Lorem ipsum dolor sit amet...\n" +
        "V 1274605200\n" +
        "e\nc";
    
    @Test
    public void testChannelParsing() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);
        
        assertEquals("S19.2E-133-5-1793", first.getChannelID());
        assertEquals("Channel Name", first.getChannelName());
        
        String epgDataWithoutChannelName = 
            "C S19.2E-133-5-1793\n" + 
            "E 12667 1274605200 7200 50 FF\n" + 
            "T Program Title\n" + 
            "D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..\n" + 
            "e\nc";
    
        entries = EPGParser.parse(epgDataWithoutChannelName);
        first = entries.get(0);
        
        assertEquals("S19.2E-133-5-1793", first.getChannelID());
        assertNull(first.getChannelName());
    }
    
    @Test
    public void testEntryParsing() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);

        assertEquals(12667, first.getEventID());                    // event id
        Calendar calStartTime = GregorianCalendar.getInstance();    // start time
        calStartTime.setTimeInMillis(1274605200 * 1000L);
        assertEquals(calStartTime, first.getStartTime());
        Calendar calEndTime = (Calendar) calStartTime.clone();      // end time
        calEndTime.add(Calendar.SECOND, 7200);
        assertEquals(calEndTime, first.getEndTime());
        assertEquals("50", first.getTableID());                     // table id
        assertEquals("FF", first.getVersion());                     // version
        
        String epgDataWithoutVersion = 
            "C S19.2E-133-5-1793\n" + 
            "E 12667 1274605200 7200 50\n" + 
            "T Program Title\n" + 
            "D Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..\n" + 
            "e\nc";
        
        entries = EPGParser.parse(epgDataWithoutVersion);
        first = entries.get(0);
        assertEquals("", first.getVersion());
    }
    
    @Test
    public void testTitleParsing() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);
        assertEquals("Program Title", first.getTitle());
    }
    
    @Test
    public void testVpsTime() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);
        Calendar vpsTime = GregorianCalendar.getInstance();
        vpsTime.setTimeInMillis(1274605200 * 1000L);
        assertEquals(vpsTime, first.getVpsTime());
    }
    
    @Test
    public void testDescription() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);
        String expected = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..";        
        assertEquals(expected, first.getDescription());
        
    }
    
    @Test
    public void testShortDescription() {
        List<EPGEntry> entries = EPGParser.parse(epgData);
        EPGEntry first = entries.get(0);
        String expected = "Lorem ipsum dolor sit amet...";
        assertEquals(expected, first.getShortText());
    }
}
