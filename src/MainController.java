
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainController {
    private DatabaseHelper dbHelper;
    private MainFrame view;
    private List<Task> taskList;
    private JTable taskTable;

    private LocalDate userBirthday;
    private SortByDate sortingStrategy;


    public MainController(DatabaseHelper dbHelper, MainFrame view) {
        dbHelper.addObserver(view);
        this.dbHelper = dbHelper;
        this.view = view;
        this.taskList = new ArrayList<>();
        this.sortingStrategy = new SortByDate();
        this.taskTable = view.getTaskTable();


        loadTasks();

        startTimer();

    }


    public void startTimer() {
        Timer timer = new Timer(1000, e -> {
            LocalDate now = LocalDate.now();
            String day = capitalize(now.getDayOfWeek().toString());
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            Message message = new BasicMessage(day, date);
            message = new BirthdayMessageDecorator(message, userBirthday);


            String notifications = getNotificationMessage();
            message = new NotificationsDecorator(message, notifications);


            String fullMessage = message.getMessage();
            view.getDayLabel().setText(day);
            view.getDateLabel().setText(date);
            view.getBirthdayLabel().setText(fullMessage.contains("Happy Birthday!") ? "Happy Birthday!" : "");
            view.getNotificationArea().setText(notifications);
        });
        timer.start();
    }


    public void loadTasks() {
        taskList = dbHelper.getAllTasks();

        sortingStrategy.sort(taskList);

        String[] columnNames = {"ID", "Task Name", "Description", "Category", "Deadline"};

        Object[][] taskData = new Object[taskList.size()][columnNames.length];
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            taskData[i][0] = task.getId();
            taskData[i][1] = task.getTaskName();
            taskData[i][2] = task.getShortDescription();
            taskData[i][3] = task.getCategory();
            taskData[i][4] = task.getDeadline();
        }

        view.getTaskTable().setModel(new javax.swing.table.DefaultTableModel(taskData, columnNames));
        taskTable.repaint();

    }


    private String getNotificationMessage() {
        taskList = dbHelper.getAllTasks();
        LocalDate now = LocalDate.now();
        StringBuilder notifications = new StringBuilder();

        for (Task task : taskList) {
            if (task.getDeadline().minusDays(1).equals(now)) {
                notifications.append("Task '").append(task.getTaskName())
                        .append("' is due tomorrow!\n");
            }
        }

        return notifications.toString();
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    void handleAddTask() {
        String taskName = getInput("Enter Task Name:", true);
        if (taskName == null) return;

        String shortDescription = getInput("Enter Short Description:", true);
        if (shortDescription == null) return;

        String category = getInput("Enter Category:", true);
        if (category == null) return;

        String deadlineInput = getInput("Enter Deadline (YYYY-MM-DD):", true);
        if (deadlineInput == null) return;

        LocalDate deadline;
        try {
            deadline = LocalDate.parse(deadlineInput);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Task newTask = new Task(0, taskName, shortDescription, category, deadline);

        dbHelper.addTask(newTask);
        loadTasks();
        JOptionPane.showMessageDialog(view, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }



    void handleDeleteTask() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No tasks available to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] taskNames = taskList.stream().map(Task::getTaskName).toArray(String[]::new);
        String selectedTask = (String) JOptionPane.showInputDialog(view, "Select the task to delete:",
                "Delete Task", JOptionPane.QUESTION_MESSAGE, null, taskNames, taskNames[0]);

        if (selectedTask != null) {
            Task taskToDelete = findTaskByName(selectedTask);
            dbHelper.deleteTask(taskToDelete.getId());
            loadTasks();
            JOptionPane.showMessageDialog(view, "Task deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void handleEditTask() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No tasks available to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] taskNames = taskList.stream().map(Task::getTaskName).toArray(String[]::new);
        String selectedTask = (String) JOptionPane.showInputDialog(view, "Select the task to edit:",
                "Edit Task", JOptionPane.QUESTION_MESSAGE, null, taskNames, taskNames[0]);

        if (selectedTask != null) {
            Task taskToEdit = findTaskByName(selectedTask);

            String newTaskName = getInput("Enter the new task name:", false);
            String newShortDescription = getInput("Enter the new description:", false);
            String newCategory = getInput("Enter the new category:", false);
            String deadlineInput = getInput("Enter the new deadline (YYYY-MM-DD):", false);

            if (newTaskName != null) taskToEdit.setTaskName(newTaskName);
            if (newShortDescription != null) taskToEdit.setShortDescription(newShortDescription);
            if (newCategory != null) taskToEdit.setCategory(newCategory);

            if (deadlineInput != null) {
                try {
                    LocalDate newDeadline = LocalDate.parse(deadlineInput);
                    taskToEdit.setDeadline(newDeadline);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Invalid date format. Skipping deadline update.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

            dbHelper.updateTask(taskToEdit);
            loadTasks();
            JOptionPane.showMessageDialog(view, "Task updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Task findTaskByName(String taskName) {
        for (Task task : taskList) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                return task;
            }
        }
        return null;
    }



    private String getInput(String message, boolean required) {
        JTextField textField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        panel.add(textField);

        int option = JOptionPane.showConfirmDialog(view, panel, "Enter Input", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String input = textField.getText();
            if (required && (input == null || input.isBlank())) {
                JOptionPane.showMessageDialog(view, message + " is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return input;
        }
        return null;
    }

}