package kanban.tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subTasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }
    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public Status getStatus() {
        return Status.NEW;
    }

    public List<Integer> getSubTasksId() {
        return subTasksId;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
