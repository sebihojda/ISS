package src.bts.domain;

public class Programmer extends Employee{

    private int numberOfFixedBugs = 0;
    public Programmer(String username, String password) {
        super(username, password);
    }

    public int getNumberOfFixedBugs() {
        return numberOfFixedBugs;
    }

    public void setNumberOfFixedBugs(int numberOfFixedBugs) {
        this.numberOfFixedBugs = numberOfFixedBugs;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "numberOfFixedBugs=" + numberOfFixedBugs +
                ", id=" + id +
                '}';
    }
}
