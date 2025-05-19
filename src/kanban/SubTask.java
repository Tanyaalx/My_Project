package kanban;

import java.util.Objects;

public class SubTask extends Task {

    private Integer epicId;
    public SubTask(String name, String description, Integer epicId, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public SubTask(Integer id, String name, String description) {
        super(id, name, description);
    }

    public Integer getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                "epicId=" + epicId +
                '}';
    }
}
