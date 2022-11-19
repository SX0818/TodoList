package ui;

import java.sql.Timestamp;

public class Task implements Comparable<Task> {
    private String name;
    private Timestamp date;

    public Task() {
    }

    public Task(String name, Timestamp date) {
        this.name = name;
        this.date = date;
    }

    //MODIFIES: this
    //EFFECTS: set name
    public void setName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: get name
    public String getName() {
        return this.name;
    }

    //MODIFIES: this
    //EFFECTS: set date
    public void setDate(Timestamp date) {
        this.date = date;
    }

    //EFFECTS: get date
    public Timestamp getDate() {
        return this.date;
    }

    //EFFECTS: return String
    public String toString() {
        return "Item [name=" + getName() + ", date=" + getDate() + "]";
    }

    //MODIFIES: this
    //EFFECTS: check equal
    public boolean equals(Object obj) {
        if (!(obj instanceof Task)) {
            throw new ClassCastException("ClassCastException");
        }
        Task item = (Task) obj;
        return name.equals(item.name) && date.equals(item.date);
    }

    //EFFECTS: returns a hashCode for the String
    public int hashCode() {
        return name.hashCode() + date.hashCode();
    }

    //MODIFIES: this
    //EFFECTS: compare name and compare date
    public int compareTo(Task item) {
        int compareTo = name.compareTo(item.name);
        if (compareTo == 0) {
            compareTo = date.compareTo(item.date);
        }
        return compareTo;
    }
}