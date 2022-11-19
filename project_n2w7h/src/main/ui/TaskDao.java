package ui;

import java.util.List;

public interface TaskDao {

    List<Task> findAll();

    int insert(Task item);

    int updateById(Task item);

    int deleteById(String name);

    int updateByIdSelective(Task item);

    int insertSelective(Task item);
}
