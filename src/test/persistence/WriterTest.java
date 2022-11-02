package persistence;

import exceptions.BelowMinimumException;
import model.Player;
import model.Roster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class WriterTest {
    private static final String TEST_FILE = "./data/testRoster2.txt";
    private Writer testWriter;
    private Roster roster;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        roster = new Roster();
        roster.setName("My Team");
        try {
            roster.addPlayer(new Player("LeBron", 35, 9, 40000000, 3, roster));
        } catch (BelowMinimumException e) {
            fail();
        }
    }

    @Test
    void testWriteSettings() {
        // save roster and player data to file
        roster.setSalaryCap(100000000);

        testWriter.write(roster);
        testWriter.close();

        try {
            roster = Reader.readRoster(new File(TEST_FILE));
            assertEquals(100000000, roster.getSalaryCap());
            assertEquals("My Team", roster.getName());
            assertEquals(132627000, roster.getTaxLine());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteRoster() {

        testWriter.write(roster);
        for (Player p : roster.getPlayerList()) {
            testWriter.write(p);
        }
        testWriter.close();

        try {
            roster = Reader.readRoster(new File(TEST_FILE));
            Player player = roster.getPlayerList().get(0);
            assertEquals(35, player.getAge());
            assertEquals("LeBron", player.getName());
            assertEquals(9, player.getYearsInLeague());
            assertEquals(40000000, roster.getTotalOne());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
