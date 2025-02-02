

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Task{
    private int id;
    private String taskName;
    private String shortDescription;
    private String category;
    private LocalDate deadline;
    private  SortByDate sortByDate;

    public Task(int id, String taskName, String shortDescription, String category, LocalDate deadline) {
        this.id = id;
        this.taskName = taskName;
        this.shortDescription = shortDescription;
        this.category = category;
        this.deadline = deadline;
        this.sortByDate = sortByDate;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }




}