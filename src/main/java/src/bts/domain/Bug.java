package src.bts.domain;

public class Bug extends Entity<Long>{

    private String name;
    private String description;
    private Integer severity;
    private String condition;

    public Bug(String name, String description, Integer severity, String condition) {
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", severity=" + severity +
                ", condition='" + condition + '\'' +
                ", id=" + id +
                '}';
    }
}
