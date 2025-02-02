

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper implements Subject{
    private static final String URL = "jdbc:mysql://localhost:3306/taskplanner";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private List<Observer> observers = new ArrayList<>();


    public DatabaseHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addTask(Task task) {

        String sql = "INSERT INTO tasks (task_name, short_description, category, deadline) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskName());
            stmt.setString(2, task.getShortDescription());
            stmt.setString(3, task.getCategory());
            stmt.setDate(4, Date.valueOf(task.getDeadline()));
            stmt.executeUpdate();
            notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET task_name = ?, short_description = ?, category = ?, deadline = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTaskName());
            stmt.setString(2, task.getShortDescription());
            stmt.setString(3, task.getCategory());
            stmt.setDate(4, Date.valueOf(task.getDeadline()));
            stmt.setInt(5, task.getId());
            stmt.executeUpdate();
            notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("task_name"),
                        rs.getString("short_description"),
                        rs.getString("category"),
                        rs.getDate("deadline").toLocalDate()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public JTable addObserver(Observer observer) {
        observers.add(observer);
        return null;
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }


    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0){
            observers.remove(i);
        }
    }


}