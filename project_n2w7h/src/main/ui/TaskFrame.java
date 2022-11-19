package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;


public class TaskFrame extends JFrame {
    private TextField nameTf;
    private TextField dateTf;
    private JDialog updateDialog;
    private TaskService itemService = new TaskServiceImpl();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Integer width = 600;
    private Integer height = 400;
    private JTable table;
    private JButton insert;
    private JButton flush;
    private JButton delete;
    private JButton update;
    private JButton whereQuery;
    private JButton pic;
    private String[] tittle = {"name", "date"};
    private List<Task> list;
    private final Color[] frameColors = {null, null, null, null, null, null, null, null, null};
    private final Color[] dialogColors = {null, null, null, null};
    private JPanel southPanel;

    //MODIFIES: this
    //EFFECTS: insert Init
    public void insertInit3(Task item) {
        try {
            itemService.insert(item);
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(TaskFrame.this, "error" + e1.getMessage());
            return;
        }
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public void insertInit2(Task item) {
        try {
            item.setName(nameTf.getText().trim().equals("") ? null : String.valueOf(nameTf.getText()));
            item.setDate(dateTf.getText().trim()
                    .equals("") ? null : new Timestamp(simpleDateFormat.parse(dateTf.getText()).getTime()));
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(TaskFrame.this, "error" + e1.getMessage());
            return;
        }
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public void insertInit5(final Dimension screenSize) {
        if (insertDialog == null) {
            insertInit1(screenSize);
            JButton ok = new JButton("add");
            JButton cancel = insertInit4(ok);
            insertDialog.add(cancel);
            insertDialog.getContentPane().setBackground(dialogColors[0]);
            nameTf.setBackground(dialogColors[1]);
            dateTf.setBackground(dialogColors[1]);
            ok.setBackground(dialogColors[2]);
            cancel.setBackground(dialogColors[3]);
        }
        insertDialog.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public JButton insertInit4(JButton ok) {
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task item = new Task();
                insertInit2(item);

                insertInit3(item);
                insertDialog.setVisible(false);
                flush();
                nameTf.setText("");
                dateTf.setText("");
            }


        });
        insertDialog.add(ok);
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertDialog.setVisible(false);
            }
        });
        return cancel;
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public void insertInit1(final Dimension screenSize) {
        insertDialog = new JDialog();
        insertDialog.setTitle("add");
        insertDialog.setBounds(((int) screenSize.getWidth() - width) / 2,
                ((int) screenSize.getHeight() - height) / 2, width, height);
        insertDialog.setLayout(new GridLayout(3, 2));
        insertDialog.add(new JLabel(tittle[0] + ":", JLabel.CENTER));
        nameTf = new TextField();
        insertDialog.add(nameTf);
        insertDialog.add(new JLabel(tittle[1] + ":", JLabel.CENTER));
        dateTf = new TextField();
        insertDialog.add(dateTf);
    }

    //EFFECTS: delete Init
    public void deleteInit1() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TaskFrame.this, "delete");
            return;
        }
        itemService.deleteById(list.get(row).getName());
        flush();
    }

    //EFFECTS: update Init
    public void updateDialogInit4(final Dimension screenSize) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(TaskFrame.this, "update");
            return;
        }
        if (updateDialog == null) {
            updateDialogInit2(screenSize);
        }
        Task item = list.get(row);
        nameTf.setText(item.getName() == null ? "" : item.getName() + "");
        dateTf.setText(item.getDate() == null ? "" : simpleDateFormat.format(item.getDate()) + "");
        updateDialog.setVisible(true);
    }

    //EFFECTS: query Init
    public void whereQueryInit2() {
        JButton query = new JButton("query");
        query.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task item = new Task();
                try {
                    item.setName(nameTf.getText().trim().equals("") ? null : String.valueOf(nameTf.getText()));
                    item.setDate(dateTf.getText().trim()
                            .equals("") ? null : new Timestamp(simpleDateFormat.parse(dateTf.getText()).getTime()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(TaskFrame.this, "error");
                    return;
                }
                flush(item, choice.getSelectedItem().equals("precise query"));
                whereQueryDialog.setVisible(false);
            }
        });
        whereQueryDialog.add(query);
        whereQueryDialog.getContentPane().setBackground(dialogColors[0]);
        nameTf.setBackground(dialogColors[1]);
        dateTf.setBackground(dialogColors[1]);
        panel.setBackground(dialogColors[2]);
        query.setBackground(dialogColors[3]);
    }

    //EFFECTS: query Init
    public void whereQueryInit1(final Dimension screenSize) {
        whereQueryDialog = new JDialog();
        whereQueryDialog.setTitle("query");
        whereQueryDialog.setBounds(((int) screenSize.getWidth() - width) / 2,
                ((int) screenSize.getHeight() - height) / 2, width, height);
        whereQueryDialog.setLayout(new GridLayout(3, 2));
        whereQueryDialog.add(new JLabel(tittle[0] + ":", JLabel.CENTER));
        nameTf = new TextField();
        whereQueryDialog.add(nameTf);
        whereQueryDialog.add(new JLabel(tittle[1] + ":", JLabel.CENTER));
        dateTf = new TextField();
        whereQueryDialog.add(dateTf);
        panel = new JPanel();
        choice = new Choice();
        choice.add("fuzzy query");
        choice.add("precise query");
        panel.add(choice);
        whereQueryDialog.add(panel);
    }

    private JDialog whereQueryDialog;
    private JPanel panel;
    private Choice choice;
    private JDialog insertDialog;

    public TaskFrame() {
        final Dimension screenSize = init1();
        init3(screenSize);
        init4(screenSize);
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public void init4(final Dimension screenSize) {
        whereQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (whereQueryDialog == null) {
                    whereQueryInit1(screenSize);
                    whereQueryInit2();
                }
                whereQueryDialog.setVisible(true);
            }
        });
        pic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileDialog.setVisible(true);
                if (picDialog == null) {
                    picInit1(screenSize);
                } else {
                    picDialog.setVisible(true);
                    picDialog.repaint();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: insert Init
    public void init3(final Dimension screenSize) {
        init2();
        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertInit5(screenSize);
            }
        });
        flush.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flush();
            }
        });
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteInit1();
            }
        });
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDialogInit4(screenSize);
            }
        });
    }

    private JDialog picDialog;
    FileDialog fileDialog = new FileDialog(TaskFrame.this, "TodoList", FileDialog.LOAD);

    //MODIFIES: this
    //EFFECTS: Dialog Init
    public void picInit1(final Dimension screenSize) {
        picDialog = new JDialog(TaskFrame.this) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
                Toolkit tk = Toolkit.getDefaultToolkit();
                Image img = tk.getImage(file.getAbsolutePath());
                g.drawImage(img, 0, 0, picDialog.getWidth(), picDialog.getHeight(), this);
            }
        };
        picDialog.setTitle("load");
        picDialog.setBounds(((int) screenSize.getWidth() - width) / 2,
                ((int) screenSize.getHeight() - height) / 2, width, height);
        picDialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
        picDialog.setVisible(true);
        picDialog.repaint();
    }

    //EFFECTS: Init
    public void init2() {
        pic = new JButton("load");
        pic.setBackground(frameColors[8]);
        southPanel.add(pic);
        add(southPanel, BorderLayout.SOUTH);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        table.getTableHeader().setBackground(frameColors[0]);
        table.setBackground(frameColors[1]);
        scrollPane.getViewport().setBackground(frameColors[2]);
        add(scrollPane);
        flush();
        setVisible(true);
    }


    //EFFECTS: Init
    public Dimension init1() {
        final Dimension screenSize = init0();
        delete = new JButton("delete");
        delete.setBackground(frameColors[6]);
        southPanel.add(delete);
        update = new JButton("update");
        update.setBackground(frameColors[7]);
        southPanel.add(update);
        whereQuery = new JButton("query");
        whereQuery.setBackground(frameColors[8]);
        southPanel.add(whereQuery);
        return screenSize;
    }

    //EFFECTS: Init
    public Dimension init0() {
        setTitle("TodoList");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) screenSize.getWidth() - width) / 2, ((int) screenSize.getHeight() - height) / 2, width,
                height);
        this.width = this.width / 2;
        this.height = (int) (this.height * 0.8);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        southPanel = new JPanel();
        southPanel.setBackground(frameColors[3]);
        insert = new JButton("save");
        insert.setBackground(frameColors[4]);
        southPanel.add(insert);
        flush = new JButton("flush");
        flush.setBackground(frameColors[5]);
        southPanel.add(flush);
        return screenSize;
    }

    //EFFECTS: flush
    private void flush(Task item, boolean isDistance) {
        list = itemService.findAll();
        Iterator<Task> iterator = list.iterator();
        while (iterator.hasNext()) {
            flushInit1(item, isDistance, iterator);
        }
        showList(list);
    }

    //EFFECTS: flush
    public void flush() {
        list = itemService.findAll();
        showList(list);
    }

    //MODIFIES: this
    //EFFECTS: flush
    public void flushInit1(Task item, boolean isDistance, Iterator<Task> iterator) {
        Task u = (Task) iterator.next();
        flushInit2(item, isDistance, iterator, u);
        if (item.getDate() != null && !(item.getDate() + "").equals("")) {
            if (isDistance) {
                if (!u.getDate().equals(item.getDate())) {
                    iterator.remove();
                    return;
                }
            } else {
                if (!(u.getDate() + "").contains(item.getDate() + "")) {
                    iterator.remove();
                    return;
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: flush Init
    public void flushInit2(Task item, boolean isDistance, Iterator<Task> iterator, Task u) {
        if (item.getName() != null && !(item.getName() + "").equals("")) {
            if (isDistance) {
                if (!u.getName().equals(item.getName())) {
                    iterator.remove();
                    return;
                }
            } else {
                if (!(u.getName() + "").contains(item.getName() + "")) {
                    iterator.remove();
                    return;
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: update Dialog Init
    public void updateDialogInit2(final Dimension screenSize) {
        updateDialogInit(screenSize);
        JButton ok = new JButton("Update");
        updateOkInit(ok);
        updateDialog.add(ok);
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDialog.setVisible(false);
            }
        });
        updateDialog.add(cancel);
        updateDialog.getContentPane().setBackground(dialogColors[0]);
        nameTf.setBackground(dialogColors[1]);
        dateTf.setBackground(dialogColors[1]);
        ok.setBackground(dialogColors[2]);
        cancel.setBackground(dialogColors[3]);
    }

    //MODIFIES: this
    //EFFECTS: update Dialog Init
    public void updateOkInit(JButton ok) {
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task item = new Task();
                try {
                    item.setName(nameTf.getText().trim().equals("") ? null : String.valueOf(nameTf.getText()));
                    item.setDate(dateTf.getText().trim()
                            .equals("") ? null : new Timestamp(simpleDateFormat.parse(dateTf.getText()).getTime()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(TaskFrame.this, "error" + e1.getMessage());
                    return;
                }
                try {
                    itemService.updateById(item);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(TaskFrame.this, "error" + e1.getMessage());
                    return;
                }
                updateDialog.setVisible(false);
                flush();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: update Dialog Init
    public void updateDialogInit(final Dimension screenSize) {
        updateDialog = new JDialog();
        updateDialog.setTitle("Update");
        updateDialog.setBounds(((int) screenSize
                .getWidth() - width) / 2, ((int) screenSize.getHeight() - height) / 2, width, height);
        updateDialog.setLayout(new GridLayout(3, 2));
        updateDialog.add(new JLabel(tittle[0] + ":", JLabel.CENTER));
        nameTf = new TextField();
        updateDialog.add(nameTf);
        updateDialog.add(new JLabel(tittle[1] + ":", JLabel.CENTER));
        dateTf = new TextField();
        updateDialog.add(dateTf);
    }


    //MODIFIES: this
    //EFFECTS: show list
    public void showList(List<Task> list) {
        String[][] data = new String[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            Task item2 = list.get(i);
            data[i][0] = item2.getName() == null ? "" : item2.getName() + "";
            data[i][1] = item2.getDate() == null ? "" : simpleDateFormat.format(item2.getDate()) + "";
        }
        table.setModel(new DefaultTableModel(data, tittle));
    }

    public static void main(String[] args) {
        new TaskFrame();
    }
}
