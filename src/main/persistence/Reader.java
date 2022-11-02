package persistence;

import exceptions.BelowMinimumException;
import model.Player;
import model.Roster;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read account data from a file
public class Reader {
    public static final String DELIMITER = ",";
    public static Roster roster;

    // EFFECTS: returns a list of accounts parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static Roster readRoster(File file) throws IOException {
        List<String> fileContent = readFile(file);
        roster = readSettings(fileContent.get(0));
        fileContent.remove(0);
        roster.setPlayerList(parseContent(fileContent, roster));
        return roster;
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    private static Roster readSettings(String string) {

        List<String> settings = splitString(string);

        String name = settings.get(0);
        long salaryCap = Long.parseLong(settings.get(1));
        long taxLine = Long.parseLong(settings.get(2));
        long taxApron = Long.parseLong(settings.get(3));
        long salaryFloor = Long.parseLong(settings.get(4));
        int maxSize = Integer.parseInt(settings.get(5));
        roster = new Roster(name, salaryCap, taxLine, taxApron, salaryFloor, maxSize);
        roster.setSize(Integer.parseInt(settings.get(6)));
        roster.setTotalOne(Long.parseLong(settings.get(7)));
        roster.setTotalTwo(Long.parseLong(settings.get(8)));
        roster.setTotalThree(Long.parseLong(settings.get(9)));
        roster.setTotalFour(Long.parseLong(settings.get(10)));
        roster.setTotalFive(Long.parseLong(settings.get(11)));

        return roster;
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static List<Player> parseContent(List<String> fileContent, Roster roster) {
        List<Player> accounts = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            accounts.add(parseAccount(lineComponents, roster));
        }

        return accounts;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static Player parseAccount(List<String> components, Roster roster) {
        String name = components.get(0);
        int age = Integer.parseInt(components.get(1));
        int yearsInLeague = Integer.parseInt(components.get(2));
        long salary = Long.parseLong(components.get(3));
        int contractYears = Integer.parseInt(components.get(4));

        try {
            return new Player(name, age, yearsInLeague, salary, contractYears, roster);
        } catch (BelowMinimumException e) {
            return null;
        }
    }
}









