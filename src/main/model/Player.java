package model;

import exceptions.BelowMinimumException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Represents a basketball player with his name, age, number of years spent in league, salary, length of contract, and
// any exceptions they're signed under

public class Player implements Saveable {

    private SimpleStringProperty name;
    private SimpleIntegerProperty age;
    private SimpleIntegerProperty yearsInLeague;
    private List<SimpleLongProperty> salaryByYear = new ArrayList<>();
    private SimpleIntegerProperty contractYears;
    private SimpleLongProperty yearOne;
    private SimpleLongProperty yearTwo;
    private SimpleLongProperty yearThree;
    private SimpleLongProperty yearFour;
    private SimpleLongProperty yearFive;
    private Roster roster;

    public Player(String name, int age, int yearsInLeague,
                  long salary, int contractYears, Roster roster) throws BelowMinimumException {

        this.roster = roster;
        if (roster.getMinimumSalary().get(contractYears) > salary) {
            throw new BelowMinimumException();
        }

        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.yearsInLeague = new SimpleIntegerProperty(yearsInLeague);
        this.contractYears = new SimpleIntegerProperty(contractYears);

        for (int x = 0; x < contractYears; x++) {
            salaryByYear.add(new SimpleLongProperty(salary));
        }
        for (int x = contractYears; x < 5; x++) {
            salaryByYear.add(new SimpleLongProperty(0));
        }

        yearOne = salaryByYear.get(0);
        yearTwo = salaryByYear.get(1);
        yearThree = salaryByYear.get(2);
        yearFour = salaryByYear.get(3);
        yearFive = salaryByYear.get(4);
        addRoster(roster);
    }

    // MODIFIES: this, roster
    // EFFECTS: sets roster field, and adds player to roster
    public void addRoster(Roster roster) {
        this.roster = roster;
        if (roster.getPlayerList().isEmpty()) {
            roster.addPlayer(this);
        } else {
            if (!roster.getPlayerList().contains(this)) {
                roster.addPlayer(this);
            }
        }
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getName());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getAge());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getYearsInLeague());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(getYearOne());
        printWriter.print(Reader.DELIMITER);
        printWriter.println(getContractYears());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }
    /*
    public void setAge(int age) {
        this.age.set(age);
    }*/

    public int getYearsInLeague() {
        return yearsInLeague.get();
    }
    /*
    public void setYearsInLeague(int yearsInLeague) {
        this.yearsInLeague.set(yearsInLeague);
    }*/

    public List<SimpleLongProperty> getSalaryByYear() {
        return salaryByYear;
    }
    /*
    public void setSalaryByYear(List<SimpleLongProperty> salaryByYear) {
        this.salaryByYear = salaryByYear;
    }*/

    public long getYearOne() {
        return yearOne.get();
    }
    /*
    public void setYearOne(long yearOne) {
        this.yearOne.set(yearOne);
    }*/

    public long getYearTwo() {
        return yearTwo.get();
    }
    /*
    public void setYearTwo(long yearTwo) {
        this.yearTwo.set(yearTwo);
    }*/

    public long getYearThree() {
        return yearThree.get();
    }
    /*
    public void setYearThree(long yearThree) {
        this.yearThree.set(yearThree);
    }*/

    public long getYearFour() {
        return yearFour.get();
    }
    /*
    public void setYearFour(long yearFour) {
        this.yearFour.set(yearFour);
    }*/

    public long getYearFive() {
        return yearFive.get();
    }

    public int getContractYears() {
        return contractYears.get();
    }
    /*
    public void setYearFive(long yearFive) {
        this.yearFive.set(yearFive);
    }*/
}
