package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TaskTest {
    Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("project", "10/14/2020", false);
    }

    @Test
    public void testConstructor() {
        assertEquals("project", task.getName());
        assertEquals("10/14/2020", task.getDate());
    }

    @Test
    public void testSetComplete() {
        task.setComplete(true);
        assertTrue(task.isComplete());
    }
}
