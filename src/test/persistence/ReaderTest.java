package persistence;

import model.Player;
import model.Roster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    Roster roster;
    Player player;

    @BeforeEach
    void runBefore() throws IOException {
        roster = Reader.readRoster(new File("./data/testRoster.txt"));
    }

    @Test
    void testParseRoster() {
        assertEquals("Team", roster.getName());
        assertEquals(109140000, roster.getSalaryCap());
        assertEquals(132627000, roster.getTaxLine());
        assertEquals(138928000, roster.getTaxApron());
        assertEquals(98226000, roster.getSalaryFloor());
        assertEquals(15, roster.getMaxSize());
        System.out.println(roster.getSize());
        assertEquals(1, roster.getSize());
        assertEquals(1621415, roster.getTotalOne());
        assertEquals(1621415, roster.getTotalTwo());
        assertEquals(0, roster.getTotalThree());
        assertEquals(0, roster.getTotalFour());
        assertEquals(0, roster.getTotalFive());

        player = roster.getPlayerList().get(0);
        assertEquals("LeBron James", player.getName());
        assertEquals(34, player.getAge());
        assertEquals(4, player.getYearsInLeague());
        assertEquals(2, player.getContractYears());
        assertEquals(1621415, player.getSalaryByYear().get(0).get());
        assertEquals(0, player.getSalaryByYear().get(2).get());
    }

    @Test
    void testIOException() {
        try {
            Reader.readRoster(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }

}
