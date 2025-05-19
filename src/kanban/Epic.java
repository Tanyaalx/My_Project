package kanban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private List<Integer> subTasksId = new ArrayList<>();

    public Epic() {
        super("epic", "description", Status.NEW);
    }
    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }

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
