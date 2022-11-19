package model;

public class Task {
    private String name;
    private String date;
    private boolean isComplete;


    //EFFECTS: initialization a task with name, date
    public Task(String name, String date, boolean isComplete) {
        this.name = name;
        this.date = date;
        this.isComplete = isComplete;
    }

    //EFFECTS: return name as String type
    public String getName() {
        return this.name;
    }

    //EFFECTS: return date as String type
    public String getDate() {
        return this.date;
    }

    //MODIFIES: this
    //EFFECTS: set whether true of false for isComplete
    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    //EFFECTS: return isComplete as boolean type
    public boolean isComplete() {
        return this.isComplete;
    }
}

