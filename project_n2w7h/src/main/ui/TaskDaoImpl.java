package ui;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDaoImpl implements TaskDao {

    private File file = new File("item.txt");
    private ArrayList<Task> list = new ArrayList<>();

    public List<Task> findAll() {
        return (List<Task>) list.clone();
    }

    //EFFECTS: initial microprogram loading
    public TaskDaoImpl() {
        BufferedReader bufferedReader = null;
        try {
            if (!file.exists()) {
                new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("/"))).mkdirs();
                file.createNewFile();
            }
            bufferedReader = new BufferedReader(new FileReader(file));
            while (bufferedReader.ready()) {
                String[] split = bufferedReader.readLine().split("  +");
                list.add(new Task(split[0].equals("null") ? null :
                        (split[0]), split[1].equals("null") ? null : new Timestamp(
                                TaskFrame.simpleDateFormat.parse(split[1]).getTime())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: insert item
    public int insert(Task item) {
        try {
            if (item.getName() == null) {
                item.setName(UUID.randomUUID().toString());
            }
            for (Task obj : list) {
                if (obj.getName().equals(item.getName())) {
                    throw new RuntimeException("RuntimeException");
                }
            }
            list.add(item);
            writeFile();
            return list.size() - 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //MODIFIES: this
    //EFFECTS: insert item
    public int insertSelective(Task item) {
        return insert(item);
    }

    //EFFECTS: write a file
    public void writeFile() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Task item : list) {
                bufferedWriter.write(item.getName()
                        + "  " + TaskFrame.simpleDateFormat.format(item.getDate()) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: update item
    public int updateById(Task item) {
        try {
            int i = 0;
            for (; i < list.size(); i++) {
                if (item.getName().equals(list.get(i).getName())) {
                    list.set(i, item);
                    break;
                }
            }
            writeFile();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //EFFECTS: update item
    public int updateByIdSelective(Task item) {
        return updateById(item);
    }

    //MODIFIES: this
    //EFFECTS: delete item
    public int deleteById(String name) {
        try {
            int i = 0;
            for (; i < list.size(); i++) {
                if (name.equals(list.get(i).getName())) {
                    list.remove(i);
                    break;
                }
            }
            writeFile();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
