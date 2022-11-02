package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

// Represents an NBA roster with name of roster, list of players, list of eligible exceptions, the salary cap,
// tax line, tax apron (additional restriction above tax line), salary floor (minimum), and maximum size of roster

public class Roster implements Saveable {

    private String name;
    private List<Player> playerList;
    private long salaryCap;
    private long taxLine;
    private long taxApron;
    private long salaryFloor;
    private int maxSize;
    private long totalOne;
    private long totalTwo;
    private long totalThree;
    private long totalFour;
    private long totalFive;
    private int size;
    public Hashtable<Integer, Long> minimumSalary = new Hashtable<>();

    public Roster() {

        this.maxSize = 15;
        this.playerList = new ArrayList<>(maxSize);
        this.salaryCap = 109140000;
        this.taxLine = 132627000;
        this.taxApron = 138928000;
        this.salaryFloor = 98226000;
        this.totalOne = 0;
        this.totalTwo = 0;
        this.totalThree = 0;
        this.totalFour = 0;
        this.totalFive = 0;

        initializeDictionary();
    }

    public Roster(String name, long salaryCap, long taxLine, long taxApron, long salaryFloor, int maxSize) {

        this.name = name;
        this.playerList = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
        this.salaryCap = salaryCap;
        this.taxLine = taxLine;
        this.taxApron = taxApron;
        this.salaryFloor = salaryFloor;
        this.size = 0;
        this.totalOne = 0;
        this.totalTwo = 0;
        this.totalThree = 0;
        this.totalFour = 0;
        this.totalFive = 0;

        initializeDictionary();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public long getSalaryCap() {
        return salaryCap;
    }

    public void setSalaryCap(long salaryCap) {
        this.salaryCap = salaryCap;
    }

    public long getTaxLine() {
        return taxLine;
    }

    public void setTaxLine(long taxLine) {
        this.taxLine = taxLine;
    }

    public long getTaxApron() {
        return taxApron;
    }

    public void setTaxApron(long taxApron) {
        this.taxApron = taxApron;
    }

    public long getSalaryFloor() {
        return salaryFloor;
    }

    public void setSalaryFloor(long salaryFloor) {
        this.salaryFloor = salaryFloor;
    }

    public void addPlayer(Player player) {
        if (!getPlayerList().contains(player)) {
            size++;
            getPlayerList().add(player);
            setNewTotalSalary();
            player.addRoster(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the new salary totals for each year
    public void setNewTotalSalary() {
        totalOne = getSalary(0);
        totalTwo = getSalary(1);
        totalThree = getSalary(2);
        totalFour = getSalary(3);
        totalFive = getSalary(4);
    }

    public long getSalary(int years) {
        long total = 0;
        for (Player p : playerList) {
            total += p.getSalaryByYear().get(years).get();
        }

        return total;
    }

    public long getTotalOne() {
        return totalOne;
    }

    public long getTotalTwo() {
        return totalTwo;
    }

    public long getTotalThree() {
        return totalThree;
    }

    public long getTotalFour() {
        return totalFour;
    }

    public long getTotalFive() {
        return totalFive;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(salaryCap);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(taxLine);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(taxApron);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(salaryFloor);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(maxSize);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(0);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(totalOne);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(totalTwo);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(totalThree);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(totalFour);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(totalFive);

    }

    public void setTotalOne(long totalOne) {
        this.totalOne = totalOne;
    }

    public void setTotalTwo(long totalTwo) {
        this.totalTwo = totalTwo;
    }

    public void setTotalThree(long totalThree) {
        this.totalThree = totalThree;
    }

    public void setTotalFour(long totalFour) {
        this.totalFour = totalFour;
    }

    public void setTotalFive(long totalFive) {
        this.totalFive = totalFive;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // MODIFIES: this
    // EFFECTS: adds keys and values to minimumSalary dictionary
    public void initializeDictionary() {
        minimumSalary.put(0, (long) 838464);
        minimumSalary.put(1, (long) 1349383);
        minimumSalary.put(2, (long) 1512601);
        minimumSalary.put(3, (long) 1567007);
        minimumSalary.put(4, (long) 1621415);
        minimumSalary.put(5, (long) 1757429);
        minimumSalary.put(6, (long) 1893447);
        minimumSalary.put(7, (long) 2029463);
        minimumSalary.put(8, (long) 2165481);
        minimumSalary.put(9, (long) 2176260);
        minimumSalary.put(10, (long) 2393887);
    }

    public Hashtable<Integer, Long> getMinimumSalary() {
        return minimumSalary;
    }

    // EFFECTS: returns true if the playerList has hit max size, otherwise false
    public boolean isRosterMaxed() {
        return getSize() == getMaxSize();
    }

    // EFFECTS: returns true if player salary is below salary floor, false otherwise
    public boolean isBelowFloor() {
        return getTotalOne() < getSalaryFloor();
    }

    // EFFECTS: returns true if player salary has exceed salary cap, otherwise false
    public boolean isAboveCap() {
        return getTotalOne() > getSalaryCap();
    }

    // EFFECTS: returns true if player salary has exceed tax line, otherwise false
    public boolean isAboveLine() {
        return getTotalOne() > getTaxLine();
    }

    // EFFECTS: returns true if player salary has exceed tax apron, otherwise false=
    public boolean isAboveApron() {
        return getTotalOne() > getTaxApron();
    }
}



