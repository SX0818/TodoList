package persistence;
import model.TodoList;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json", "list");
        try {
            TodoList todolist = reader.read();
        } catch (IOException e) {
            // pass
            fail("IOException expected");
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodolist.json", "list");
        try {
            TodoList todolist = reader.read();
            assertEquals("list", todolist.getTitle());
            assertEquals(0, todolist.getTaskListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTodoList() {
        JsonReader reader = new JsonReader("data/testReaderGeneralTodolist.json", "list");
        try {
            TodoList todoList = reader.read();
            assertEquals("list", todoList.getTitle());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
