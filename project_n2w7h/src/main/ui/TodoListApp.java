package ui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TodoListApp {
    private static final String JSON_STORE = "data/todoList.json";
    private static final String LIST_NAME = "list1";

    private JsonWriter jsonWriter;
    private TodoList todoList;

    public TodoListApp() {
        this.todoList = new TodoList(LIST_NAME);
        this.jsonWriter = new JsonWriter(JSON_STORE);
    }

    public void command() throws IOException {
        commandList();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.equals("exit")) {
            int num = Integer.parseInt(input);
            if (num == 1) {
                this.addTask();
            } else if (num == 2) {
                this.deleteTask();
            } else if (num == 3) {
                this.listIncompleteTaskWithNUmber();
            } else if (num == 4) {
                this.listCompleteTaskWithNUmber();
            } else if (num == 5) {
                this.setTaskComplete();
            } else if (num == 6) {
                this.loadTodoList();
            } else if (num == 7) {
                this.saveTodoList();
            }
            commandList();
            input = in.nextLine();
        }
    }

    private void commandList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------------ToDoListsTool-----------------------------");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Command List: \n")
                .append(" Option 1: Add a task \n Option 2: Delete a task\n")
                .append(" Option 3: List incomplete tasks \n Option 4: List complete tasks\n")
                .append(" Option 5: Complete a task \n")
                .append(" Option 6: Load Lists \n Option 7: Save Lists")
                .append("\n input 'exit' to quit")
                .append(System.lineSeparator())
                .append("Please enter command option number here(ex. input '1' for add a task):");
        System.out.println(stringBuilder.toString());
    }

    public String scanString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private void addTask() {
        System.out.println("Enter the Task name: ");
        String taskName = scanString();
        System.out.println("Enter the Task date: ");
        String taskDate = scanString();
        Task task = new Task(taskName, taskDate, false);
        this.todoList.addTask(task);
        System.out.println("It's has been added to list.");
    }

    private void deleteTask() {
        System.out.println("Enter the Task name: ");
        String taskName = scanString();
        this.todoList.deleteTask(taskName);
        System.out.println("It's has been deleted from list.");
    }

    private void listIncompleteTaskWithNUmber() {
        System.out.println("Incomplete Tasks List : ");
        List<Task> tasks = this.todoList.listIncompleteTasks();
        for (Task t : tasks) {
            String result = "name : ";
            result += t.getName() + " date : ";
            result += t.getDate() + " complete : ";
            result += t.isComplete();
            System.out.println(result);
        }
        System.out.println("Total number : "
                + this.todoList.inCompleteTasksNumber());
    }

    private void listCompleteTaskWithNUmber() {
        System.out.println("Complete Tasks List : ");
        for (Task t : this.todoList.listCompleteTasks()) {
            String result = "name : ";
            result += t.getName() + " date : ";
            result += t.getDate() + " complete : ";
            result += t.isComplete();
            System.out.println(result);
        }
        System.out.println("Total number : "
                + this.todoList.completeTasksNumber());
    }

    //EFFECTS: saves the todoList to file
    private void saveTodoList() throws FileNotFoundException {
        jsonWriter.open();
        jsonWriter.write(this.todoList);
        jsonWriter.close();
        System.out.println("Saved " + this.todoList.getTitle() + " to " + JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: load todolist from file
    private void loadTodoList() throws IOException {
        todoList = new JsonReader(JSON_STORE, LIST_NAME).read();
        System.out.println("Loaded " + this.todoList.getTitle() + " from " + JSON_STORE);
    }

    private void setTaskComplete() {
        System.out.println("Enter the Task name: ");
        String taskName = scanString();
        for (Task task : this.todoList.getTodoList()) {
            if (task.getName().equals(taskName)) {
                task.setComplete(true);
            }
        }
        System.out.println("It's has been set to completed.");
    }
}
