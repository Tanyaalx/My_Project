package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.SubTask;
import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private Integer count = 1;

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task addTask(Task newTask) {
        Integer newTaskId = count++;

        Task createdTask = new Task(newTaskId, newTask.getName(), newTask.getDescription());
        tasks.put(createdTask.getId(), createdTask);

        newTask.setId(newTaskId);
        createdTask.setStatus(Status.NEW);
        return newTask;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(Integer taskId) {
        Task recievedTask = tasks.get(taskId);

        return new Task(taskId, recievedTask.getName(), recievedTask.getDescription());
    }

    public Task updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            Integer newTaskId = updatedTask.getId();
            Task existingTask = tasks.get(newTaskId);

            existingTask.setName(updatedTask.getName());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            return updatedTask;
        }
        return null;
    }

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic addEpic(Epic newEpic) {
        Integer newEpicId = count++;

        Epic createdEpic = new Epic(newEpicId, newEpic.getName(), newEpic.getDescription());
        epics.put(createdEpic.getId(), createdEpic);

        newEpic.setId(newEpicId);
        return newEpic;
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpicById(Integer id) {
        Epic recievedEpic = epics.get(id);

        return new Epic(id, recievedEpic.getName(), recievedEpic.getDescription());
    }

    public void updateEpic(Epic updatedEpic) {
        if (epics.containsKey(updatedEpic.getId())) {
            Integer newEpicId = updatedEpic.getId();
            Epic existingEpic = epics.get(newEpicId);

            existingEpic.setName(updatedEpic.getName());
            existingEpic.setDescription(updatedEpic.getDescription());
        }
    }

    public void deleteEpicById(Integer id) {
        Epic newEpic = epics.get(id);
        if (newEpic != null) {
            List<Integer> subTaskIdForNewEpic = newEpic.getSubTasksId();
            for (int i = 0; i < subTaskIdForNewEpic.size(); i++) {
                subTasks.remove(i);
            }
        }
        epics.remove(id);

    }

    public SubTask addSubTask(SubTask newSubTask) {
        Integer newSubTaskId = count++;

        SubTask createdSubTask = new SubTask(newSubTaskId, newSubTask.getName(), newSubTask.getDescription());
        subTasks.put(createdSubTask.getId(), createdSubTask);
        newSubTask.setId(newSubTaskId);

        Integer epicId = newSubTask.getEpicId();
        Epic epicForNewSubTask = epics.get(epicId);
        List<Integer> subTasksIdForEpic = epicForNewSubTask.getSubTasksId();
        subTasksIdForEpic.add(newSubTaskId);
        createdSubTask.setStatus(Status.NEW);
        updateEpicStatus(epicId);
        return newSubTask;
    }

    public void deleteAllSubTasks() {
        List<Epic> epicsList = new ArrayList<>(epics.values());
        for (Epic epic : epicsList) {
            if (epic != null) {
                List<Integer> subTasksId = epic.getSubTasksId();
                subTasksId.clear();
            }
        }
        subTasks.clear();
    }

    public SubTask getSubTaskById(Integer id) {
        SubTask recievedSubTask = subTasks.get(id);

        return new SubTask(id, recievedSubTask.getName(), recievedSubTask.getDescription());
    }

    public SubTask updateSubTask(SubTask updatedSubTask) {
        if (subTasks.containsKey(updatedSubTask.getId())) {
            Integer newSubTaskId = updatedSubTask.getId();
            SubTask existingSubTask = subTasks.get(newSubTaskId);

            existingSubTask.setName(updatedSubTask.getName());
            existingSubTask.setDescription(updatedSubTask.getDescription());
            existingSubTask.setStatus(updatedSubTask.getStatus());

            updateEpicStatus(existingSubTask.getEpicId());

            return updatedSubTask;
        }
        return null;
    }

    public void deleteSubTaskById(Integer id) {
        SubTask newSubTask = subTasks.get(id);
        Integer epicId = newSubTask.getEpicId();
        Epic newEpic = epics.get(epicId);
        List<Integer> subTasksIdList = newEpic.getSubTasksId();
        subTasksIdList.remove(id);
        subTasks.remove(id);
        updateEpicStatus(epicId);
    }

    public List<SubTask> getSubTasksByEpicId(Integer epicId) {
        List<SubTask> allSubTasksForEpic = new ArrayList<>();

        Epic newEpic = epics.get(epicId);
        if (newEpic != null) {
            List<Integer> subTasksIdForEpic = newEpic.getSubTasksId();
            for (Integer subTaskId : subTasksIdForEpic) {
                SubTask newSubTask = subTasks.get(subTaskId);
                allSubTasksForEpic.add(newSubTask);
            }
        }

        return allSubTasksForEpic;
    }

    public void updateEpicStatus(Integer epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> subTaskIdList = epic.getSubTasksId();
        Status targetStatus = Status.NEW;

        if (!subTaskIdList.isEmpty()) {
            boolean allNew = true;
            boolean allDone = true;

            for (Integer subTaskId : subTaskIdList) {
                SubTask newSubTask = subTasks.get(subTaskId);
                if (newSubTask.getStatus() != Status.NEW) {
                    allNew = false;
                }
                if (newSubTask.getStatus() != Status.DONE) {
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
}
