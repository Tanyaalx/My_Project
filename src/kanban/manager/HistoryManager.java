package kanban.manager;

import kanban.tasks.Task;

import java.util.List;

public interface HistoryManager {
    void addToHistory(Task task);

    List<Task> getHistory();
}
