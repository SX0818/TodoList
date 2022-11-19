package ui;

import java.util.List;

public class TaskServiceImpl implements TaskService {

    private TaskDao itemDao = new TaskDaoImpl();


    //EFFECTS: return a list of task
    public List<Task> findAll() {
        return itemDao.findAll();
    }


    //MODIFIES: this
    //EFFECTS: update Dao
    public int updateById(Task item) {
        return itemDao.updateByIdSelective(item);
    }

    //MODIFIES: this
    //EFFECTS: insert Dao
    public int insert(Task item) {
        return itemDao.insertSelective(item);
    }

    //MODIFIES: this
    //EFFECTS: delete Dao
    public int deleteById(String name) {
        return itemDao.deleteById(name);
    }
}
