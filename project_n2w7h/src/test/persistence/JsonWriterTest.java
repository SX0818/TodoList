package persistence;

import model.Task;
import model.TodoList;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            TodoList todoList = new TodoList("list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
        } catch (FileNotFoundException e) {
            // expected
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriteEmptyTodoList() {
        try {
            TodoList todoList = new TodoList("list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(todoList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json", "list");
            todoList = reader.read();
            assertEquals("list", todoList.getTitle());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriteGeneralTodoList() {
        try {
            TodoList todoList = new TodoList("list");
            todoList.addTask(new Task("quiz","10/30/2020", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(todoList);
            writer.close();


            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json", "list");
            todoList = reader.read();
            assertEquals("list", todoList.getTitle());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

