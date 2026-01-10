package Model;

public class Todo {
    private int id;
    private String title;
    private String priority;
    private String deadline;

    public Todo(int id, String title, String priority, String deadline) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public Todo(String title, String priority, String deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public String getDeadline() { return deadline; }
}
