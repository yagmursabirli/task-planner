

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame implements Observer{
    private JLabel dayLabel, dateLabel, birthdayLabel;
    private JTextArea notificationArea;
    private JTable taskTable;
    private JButton addButton, deleteButton, editButton;

    private MainController controller;


    public MainFrame() {

        this.controller = controller;
        setTitle("Task Planner");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        dayLabel = new JLabel();
        dateLabel = new JLabel();
        birthdayLabel = new JLabel();
        topPanel.add(dayLabel);
        topPanel.add(dateLabel);
        topPanel.add(birthdayLabel);
        add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new BorderLayout());


        JPanel notificationPanel = new JPanel(new BorderLayout());

        notificationPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        notificationPanel.setPreferredSize(new Dimension(400, 100));

        notificationArea = new JTextArea();
        notificationArea.setRows(10);
        notificationArea.setColumns(30);
        notificationArea.setEditable(false);

        notificationPanel.add(new JScrollPane(notificationArea), BorderLayout.CENTER);

        notificationPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        notificationPanel.add(new JScrollPane(notificationArea), BorderLayout.CENTER);


        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(BorderFactory.createTitledBorder("Task List"));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addButton = createButton("/add.png", 32, 32, "Add Task");
        deleteButton = createButton("/delete.png", 32, 32, "Delete Task");
        editButton = createButton("/edit.png", 32, 32, "Edit Task");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);


        taskTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(taskTable);

        taskPanel.add(buttonPanel, BorderLayout.NORTH);
        taskPanel.add(tableScrollPane, BorderLayout.CENTER);


        centerPanel.add(notificationPanel, BorderLayout.NORTH);
        centerPanel.add(taskPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        getAddButton().addActionListener(e -> controller.handleAddTask());
        getEditButton().addActionListener(e -> controller.handleEditTask());
        getDeleteButton().addActionListener(e -> controller.handleDeleteTask());
    }

    private JButton createButton(String imagePath, int width, int height, String tooltip) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(img));
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(width + 10, height + 10));
        return button;
    }

    @Override
    public void update() {
        notificationArea.setText("Notifications Updated");
        taskTable.repaint();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }


    public JTable getTaskTable() {
        return taskTable;
    }

    public JLabel getDayLabel() {
        return dayLabel;
    }

    public JLabel getDateLabel() {
        return dateLabel;
    }

    public JLabel getBirthdayLabel() {
        return birthdayLabel;
    }

    public JTextArea getNotificationArea() {
        return notificationArea;
    }


    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

}