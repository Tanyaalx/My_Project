package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private Integer idCounter = 1;

    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task addTask(Task newTask) {
        Integer newTaskId = idCounter++;
        newTask.setId(newTaskId);

        Task createdTask = new Task(newTaskId, newTask.getName(), newTask.getDescription(), newTask.getStatus());
        tasks.put(createdTask.getId(), createdTask);

        return newTask;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(Integer taskId) {
        if (tasks.containsKey(taskId))  {
        Task recievedTask = tasks.get(taskId);

            Task taskForHistory = new Task(taskId, recievedTask.getName(), recievedTask.getDescription(), recievedTask.getStatus());
            historyManager.addToHistory(taskForHistory);

        return new Task(taskId, recievedTask.getName(), recievedTask.getDescription(), recievedTask.getStatus());
        }
        return null;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            Integer taskId = updatedTask.getId();
            Task existingTask = tasks.get(taskId);

            existingTask.setName(updatedTask.getName());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            return updatedTask;
        }
        return null;
    }

    @Override
    public Task deleteTaskById(Integer id) {
        Task deleteTask = tasks.get(id);
        tasks.remove(id);

        return deleteTask;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        Integer newEpicId = idCounter++;

        Epic createdEpic = new Epic(newEpicId, newEpic.getName(), newEpic.getDescription(), newEpic.getStatus());
        epics.put(createdEpic.getId(), createdEpic);

        newEpic.setId(newEpicId);
        return newEpic;
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            Epic recievedEpic = epics.get(id);

                Epic epicForHistory = new Epic(id, recievedEpic.getName(), recievedEpic.getDescription(), recievedEpic.getStatus(), recievedEpic.getSubtasksId());
                historyManager.addToHistory(epicForHistory);

            return new Epic(id, recievedEpic.getName(), recievedEpic.getDescription(), recievedEpic.getStatus(), recievedEpic.getSubtasksId());
        }
        return null;
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        Integer epicId = updatedEpic.getId();
        if (epics.containsKey(epicId)) {
            Epic existingEpic = epics.get(epicId);

            existingEpic.setName(updatedEpic.getName());
            existingEpic.setDescription(updatedEpic.getDescription());
        }
    }

    @Override
    public Epic deleteEpicById(Integer id) {
        Epic deleteEpic = epics.get(id);
        if (deleteEpic != null) {
            List<Integer> epicSubTasksId = deleteEpic.getSubtasksId();
            for (Integer subtaskId : epicSubTasksId) {
                subtasks.remove(subtaskId);
            }
        }
        epics.remove(id);
        return deleteEpic;
    }

    @Override
    public Subtask addSubtask(Subtask newSubtask) {
        Integer newSubtaskId = idCounter++;
        Integer epicId = newSubtask.getEpicId();

        Subtask createdSubtask = new Subtask(newSubtaskId, newSubtask.getName(), newSubtask.getDescription(), epicId);
        subtasks.put(newSubtaskId, createdSubtask);
        newSubtask.setId(newSubtaskId);

        Epic existingEpic = epics.get(epicId);
        List<Integer> epicSubtasksId = existingEpic.getSubtasksId();
        epicSubtasksId.add(newSubtaskId);
        createdSubtask.setStatus(Status.NEW);
        updateEpicStatus(epicId);
        return newSubtask;
    }

    @Override
    public List<Subtask> deleteAllSubtasks() {
        List<Subtask> deletedSubtasks = new ArrayList<>(subtasks.values());
        List<Epic> allEpics = new ArrayList<>(epics.values());
        for (Epic epic : allEpics) {
            if (epic != null) {
                List<Integer> subtasksId = epic.getSubtasksId();
                subtasksId.clear();
            }
        }
        subtasks.clear();
        return deletedSubtasks;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        if (subtasks.containsKey(id)) {
            Subtask recievedSubtask = subtasks.get(id);

                Subtask subtaskForHistory = new Subtask(id, recievedSubtask.getName(), recievedSubtask.getDescription(), recievedSubtask.getStatus(), recievedSubtask.getEpicId());
                historyManager.addToHistory(subtaskForHistory);

            return new Subtask(id, recievedSubtask.getName(), recievedSubtask.getDescription(), recievedSubtask.getStatus(), recievedSubtask.getEpicId());
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask updatedSubtask) {
        if (subtasks.containsKey(updatedSubtask.getId())) {
            Integer newSubtaskId = updatedSubtask.getId();
            Subtask existingSubtask = subtasks.get(newSubtaskId);

            existingSubtask.setName(updatedSubtask.getName());
            existingSubtask.setDescription(updatedSubtask.getDescription());
            existingSubtask.setStatus(updatedSubtask.getStatus());

            updateEpicStatus(existingSubtask.getEpicId());

            return updatedSubtask;
        }
        return null;
    }

    @Override
    public Subtask deleteSubtaskById(Integer id) {
        Subtask deleteSubtask = subtasks.get(id);
        Integer epicId = deleteSubtask.getEpicId();
        Epic existingEpic = epics.get(epicId);
        List<Integer> epicSubtasksIds = existingEpic.getSubtasksId();
        epicSubtasksIds.remove(id);
        subtasks.remove(id);
        updateEpicStatus(epicId);
        return deleteSubtask;
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(Integer epicId) {
        List<Subtask> allEpicSubtasks = new ArrayList<>();

        Epic existingEpic = epics.get(epicId);
        if (existingEpic != null) {
            List<Integer> epicSubtasksIds = existingEpic.getSubtasksId();
            for (Integer subtaskId : epicSubtasksIds) {
                Subtask existingSubtask = subtasks.get(subtaskId);
                Subtask userSubtask = new Subtask(subtaskId, existingSubtask.getName(), existingSubtask.getDescription(), existingSubtask.getStatus(), existingSubtask.getEpicId());
                allEpicSubtasks.add(userSubtask);
            }
        }

        return allEpicSubtasks;
    }

    @Override
    public void updateEpicStatus(Integer epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> subtaskIdList = epic.getSubtasksId();
        Status targetStatus = Status.NEW;

        if (!subtaskIdList.isEmpty()) {
            boolean allNew = true;
            boolean allDone = true;

            for (Integer subtaskId : subtaskIdList) {
                Subtask newSubtask = subtasks.get(subtaskId);
                if (newSubtask.getStatus() != Status.NEW) {
                    allNew = false;
                }
                if (newSubtask.getStatus() != Status.DONE) {
                    allDone = false;
                }
            }

            if (!allNew) {
                if (allDone) {
                    targetStatus = Status.DONE;
                } else {
                    targetStatus = Status.IN_PROGRESS;
                }
            }

        }
        epic.setStatus(targetStatus);
    }

    @Override
    public List<Task> getHistory() {
       return new ArrayList<>(historyManager.getHistory());
    }
}
