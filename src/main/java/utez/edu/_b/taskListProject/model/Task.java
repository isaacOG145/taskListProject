package utez.edu._b.taskListProject.model;

import java.sql.Date;

public class Task {

    private Long id;
    private String taskName;
    private String taskDescription;
    private Date taskStartDate;
    private boolean taskStatus;

    public Task(){}

    public Task(Long id,String taskName, String taskDescription, Date taskStartDate, boolean taskStatus) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskStatus = taskStatus;
    }

    public Long getId() {return id;}

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public boolean getTaskStatus() {
        return taskStatus;
    }

    public void setId(Long id) {this.id = id;}

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString(){

        String p = this.taskStatus ? "Hecho":"Pendiente";


        return "nombre: " + this.taskName + "\nDescripcion: " + this.taskDescription +
                "\nFecha: " + this.taskStartDate + "\nEstado: " + p + "\n";
    }
}
