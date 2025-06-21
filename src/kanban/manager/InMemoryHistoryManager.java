package kanban.manager;

import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> history = new ArrayList<>(10);

    @Override
    public void addToHistory(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.removeFirst();
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.of();
    }
}
