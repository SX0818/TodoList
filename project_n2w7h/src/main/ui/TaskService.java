package ui;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    int deleteById(String name);

    int updateById(Task item);

    int insert(Task item);
}
