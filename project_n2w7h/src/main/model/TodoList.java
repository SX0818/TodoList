package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;


public class TodoList implements Writable {
    private String title;
    private List<Task> todoList = new ArrayList<>();


    //EFFECTS: return todolist as List<Task>
    public List<Task> getTodoList() {
        return this.todoList;
    }

    //EFFECTS: initialization a todolist with title as String type
    public TodoList(String title) {
        this.title = title;
    }

    //EFFECTS: return title as String type
    public String getTitle() {
        return title;
    }

    //EFFECTS: return size of the list
    public int getTaskListSize() {
        return this.todoList.size();
    }

    //MODIFIES:this
    //EFFECTS:add a task to the list
    public void addTask(Task task) {
        this.todoList.add(task);
    }

    //REQUIRES:the size of the list is > 0
    //MODIFIES:this
    //EFFECTS:remove a task to the list
    public void deleteTask(String taskName) {
        this.todoList.removeIf(task -> task.getName().equals(taskName));
    }

    //MODIFIES:tempList
    //EFFECTS:list incomplete tasks, return as a list
    public List<Task> listIncompleteTasks() {
        List<Task> tempList = new ArrayList<>();
        for (Task task : this.todoList) {
            if (!task.isComplete()) {
                tempList.add(task);
            }
        }
        return tempList;
    }

    //MODIFIES:tempList
    //EFFECTS:list complete tasks, return as a list
    public List<Task> listCompleteTasks() {
        List<Task> tempList = new ArrayList<>();
        for (Task task : this.todoList) {
            if (task.isComplete()) {
                tempList.add(task);
            }
        }
        return tempList;
    }


    //EFFECTS:return number of complete tasks
    public int completeTasksNumber() {
        int count = 0;
        for (Task t : todoList) {
            if (t.isComplete()) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS:return number of incomplete tasks
    public int inCompleteTasksNumber() {
        int count = 0;
        for (Task t : todoList) {
            if (!t.isComplete()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(this.title, this.todoList);
        return json;
    }
}
