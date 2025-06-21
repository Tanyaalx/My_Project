package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    Task addTask(Task newTask);

    void deleteAllTasks();

    Task getTaskById(Integer taskId);

    Task updateTask(Task updatedTask);

    Task deleteTaskById(Integer id);

    List<Epic> getAllEpics();

    Epic addEpic(Epic newEpic);

    void deleteAllEpics();

    Epic getEpicById(Integer id);

    void updateEpic(Epic updatedEpic);

    Epic deleteEpicById(Integer id);

    Subtask addSubtask(Subtask newSubtask);

    List<Subtask> deleteAllSubtasks();

    Subtask getSubtaskById(Integer id);

    Subtask updateSubtask(Subtask updatedSubtask);

    Subtask deleteSubtaskById(Integer id);

    List<Subtask> getSubtasksByEpicId(Integer epicId);

    void updateEpicStatus(Integer epicId);

    List<Task> getHistory();
}
