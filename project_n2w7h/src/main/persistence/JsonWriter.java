package persistence;

import model.TodoList;
import org.json.JSONObject;

import java.io.*;

public class JsonWriter {
    private PrintWriter writer;
    private final String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: open writer; throw FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        File file = new File(destination);
        if (!file.exists()) {
            System.out.println("file does NOT exist");
            return;
        }
        writer = new PrintWriter(file);
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of TodoList to file
    public void write(TodoList todoList) {
        JSONObject json = todoList.toJson();
        this.saveToFile(json.toString());
        this.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}