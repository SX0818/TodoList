package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TodoListTest {

    TodoList todoList;
    Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("project", "10/14/2020", false);
        todoList = new TodoList("Today");
    }

    @Test
    public void testConstructor() {
        assertEquals("Today", todoList.getTitle());
    }

    @Test
    public void testAddTasks() {
        for (int i = 0; i < 5; i++) {
            todoList.addTask(task);
        }
        assertEquals(5, todoList.getTaskListSize());
    }

    @Test
    public void testDeleteTask() {
        for (int i = 0; i < 1; i++) {
            todoList.addTask(task);
        }
        todoList.deleteTask(task.getName());
        assertEquals(0, todoList.getTaskListSize());
    }

    @Test
    public void testListCompleteTasks() {
        task.setComplete(true);
        for (int i = 0; i < 5; i++) {
            todoList.addTask(task);
        }
        List<Task> lists = todoList.listCompleteTasks();
        assertEquals(5, lists.size());
    }

    @Test
    public void testListIncompleteTasks() {
        task.setComplete(false);
        for (int i = 0; i < 5; i++) {
            todoList.addTask(task);
        }
        List<Task> lists = todoList.listIncompleteTasks();
        assertEquals(5, lists.size());
    }

    @Test
    public void testCompleteTasksNumber() {
        task.setComplete(true);
        for (int i = 0; i < 5; i++) {
            todoList.addTask(task);
        }
        assertEquals(5, todoList.completeTasksNumber());
    }

    @Test
    public void testIncompleteTasksNumber() {
        task.setComplete(false);
        for (int i = 0; i < 5; i++) {
            todoList.addTask(task);
        }
        assertEquals(5, todoList.inCompleteTasksNumber());
    }
}