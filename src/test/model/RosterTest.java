package model;

import exceptions.BelowMinimumException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RosterTest {
// tests for setRoster, getExceptions, save
    Roster roster1;
    Roster roster2;
    Player player1;
    Player player2;

    @BeforeEach
    void runBefore() {
        roster1 = new Roster();
        roster1.setName("Team");
        roster2 = new Roster("My Team", 300, 400, 500, 100, 10);
        try {
            player1 = new Player("Joe", 33, 6, 40000000, 3, roster1);
            player2 = new Player("John", 24, 2, 35000000, 2, roster1);
        } catch (BelowMinimumException e) {
            fail();
        }

    }

    @Test
    void testGettersSetters() {

        roster1.setMaxSize(14);
        assertEquals(14, roster1.getMaxSize());

        roster1.setName("Raptors");
        assertEquals("Raptors", roster1.getName());

        roster1.setSalaryCap(110000000);
        assertEquals(110000000, roster1.getSalaryCap());

        roster1.setTaxLine(135000000);
        assertEquals(135000000, roster1.getTaxLine());

        roster1.setTaxApron(140000000);
        assertEquals(140000000, roster1.getTaxApron());

        roster1.setSalaryFloor(100000000);
        assertEquals(100000000, roster1.getSalaryFloor());
    }

    @Test
    void testGetTotalSalary() {

        roster1.setNewTotalSalary();
        assertEquals(75000000, roster1.getTotalOne());
        assertEquals(75000000, roster1.getTotalTwo());
        assertEquals(40000000, roster1.getTotalThree());
        assertEquals(0, roster1.getTotalFour());
        assertEquals(0, roster1.getTotalFive());
    }

    @Test
    void testAddPlayer() {
        try {
            Player player3 = new Player("James", 29, 6, 5000000, 1, roster1);
        } catch (BelowMinimumException e) {
            fail();
        }
        assertEquals(3, roster1.getSize());
        assertEquals(3, roster1.getPlayerList().size());
        assertEquals(80000000, roster1.getTotalOne());
        assertEquals(75000000, roster1.getTotalTwo());

    }

    @Test
    void testBoundaries() throws BelowMinimumException {
        assertTrue(roster2.isBelowFloor());
        assertFalse(roster2.isAboveApron());
        assertFalse(roster2.isAboveCap());
        assertFalse(roster2.isAboveLine());
        Player player = new Player("Bron", 24, 2, 65000000, 3, roster1);
        assertFalse(roster1.isBelowFloor());
        assertTrue(roster1.isAboveCap());
        assertTrue(roster1.isAboveLine());
        assertTrue(roster1.isAboveApron());
    }
}
