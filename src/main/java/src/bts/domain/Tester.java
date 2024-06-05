package src.bts.domain;

public class Tester extends Employee{

    private int numberOfRecordedBugs = 0;
    public Tester(String username, String password) {
        super(username, password);
    }

    public int getNumberOfRecordedBugs() {
        return numberOfRecordedBugs;
    }

    public void setNumberOfRecordedBugs(int numberOfRecordedBugs) {
        this.numberOfRecordedBugs = numberOfRecordedBugs;
    }

    @Override
    public String toString() {
        return "Tester{" +
                "numberOfRecordedBugs=" + numberOfRecordedBugs +
                ", id=" + id +
                '}';
    }
}
