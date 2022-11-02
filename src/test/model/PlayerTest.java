package model;

import exceptions.BelowMinimumException;
import javafx.beans.property.SimpleLongProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    Player player2;
    Roster roster;

    @BeforeEach
    void runBefore() {
        roster = new Roster();
        try {
            player = new Player("LeBron James", 35, 10, 40000000, 3, roster);
            player2 = new Player("Stephen Curry", 31, 8, 30000000, 5, roster);
        } catch (BelowMinimumException e) {
            fail();
        }

    }

    @Test
    void testConstructor() {
        assertEquals("LeBron James", player.getName());
        assertEquals(35, player.getAge());
        assertEquals(10, player.getYearsInLeague());
        assertEquals(40000000, player.getYearOne());
        assertEquals(40000000, player.getYearTwo());
        assertEquals(40000000, player.getYearThree());
        assertEquals(0, player.getYearFour());
        assertEquals(0, player.getYearFive());
        assertEquals(30000000, player2.getYearFour());
        assertEquals(30000000, player2.getYearFive());
    }

    @Test
    void testGettersSetters() {
        List<SimpleLongProperty> salaryByYear = player.getSalaryByYear();
        assertEquals(40000000, salaryByYear.get(0).get());
        assertEquals(40000000, salaryByYear.get(1).get());
        assertEquals(40000000, salaryByYear.get(2).get());
        assertEquals(0, salaryByYear.get(3).get());
        assertEquals(0, salaryByYear.get(4).get());
        player.setName("Stephen Curry");
        assertEquals("Stephen Curry", player.getName());


    }
}
