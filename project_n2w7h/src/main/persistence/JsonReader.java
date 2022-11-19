package persistence;

import model.Task;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source = " ";
    private String title = " ";
    private static final String DEFAULT_NAME = "list";

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source, String title) {
        this.source = source;
        this.title = title;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException {
        File file = new File(this.source);
        if (!file.exists()) {
            System.out.println("Json file not exist!");
            return new TodoList(DEFAULT_NAME);
        }
        String jsonData = readFile(this.source);
        if (jsonData.isEmpty()) {
            System.out.println("Json file is empty!");
            return new TodoList(DEFAULT_NAME);
        }
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        TodoList todolist = new TodoList(this.title);
        this.addTask(todolist, jsonObject);
        return todolist;
    }

    // MODIFIES: todolist
    // EFFECTS: parses task from JSON object and adds them to todolist
    private void addTask(TodoList todolist, JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(this.title);
            for (Object json : jsonArray) {
                JSONObject nextThingy = (JSONObject) json;
                addTasks(todolist, nextThingy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: todolist
    // EFFECTS: parses task from JSON object and adds it to todolist
    private static void addTasks(TodoList todolist, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String date = jsonObject.getString("date");
        boolean isComplete = jsonObject.getBoolean("complete");
        Task task = new Task(name, date, isComplete);
        todolist.addTask(task);
    }
}
