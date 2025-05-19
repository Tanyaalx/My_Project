package kanban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private Integer count = 1;

    // методы для тасок
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>(tasks.values());
        return allTasks;
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

    public Task getTaskById(Integer id) {
        return tasks.get(id); // создать клон
    }

    public Task updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            Integer newTaskId = updatedTask.getId();
            Task existingTask = tasks.get(newTaskId);
            tasks.put(newTaskId, updatedTask);
//            existingTask.setName(updatedTask.getName());
//            existingTask.setDescription(updatedTask.getDescription());
//            existingTask.setStatus(updatedTask.getStatus());
        }
        return updatedTask;
    }

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    // методы для эпиков
    public List<Epic> getAllEpics() {
        List<Epic> allEpics = new ArrayList<>(epics.values());
        return allEpics;
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
        return epics.get(id);
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
        Epic newEpic = epics.get(id); // получаем ссылку на конкретный эпик
        if (newEpic != null) { // проверяем что в нем есть сабтаски
            List<Integer> subTaskIdForNewEpic = newEpic.getSubTasksId(); // получаем список id сабтасок этого эпика
            for (int i = 0; i < subTaskIdForNewEpic.size(); i++) { // проходимся по нему циклом и по id удаляем из мапы сабтаски
                subTasks.remove(i);
            }
        }
        epics.remove(id); // удаляем эпики из мапы

    }

    // методы для сабтасков
    public SubTask addSubTask(SubTask newSubTask) {
        Integer newSubTaskId = count++;

        SubTask createdSubTask = new SubTask(newSubTaskId, newSubTask.getName(), newSubTask.getDescription());
        subTasks.put(createdSubTask.getId(), createdSubTask);
        newSubTask.setId(newSubTaskId);

        Integer epicId = newSubTask.getEpicId(); // тут надо использовать newSubTask или createdSubTask. есть ли какая-то разница
        Epic epicForNewSubTask = epics.get(epicId); // ссылка на эпик
        List<Integer> subTasksIdForEpic = epicForNewSubTask.getSubTasksId(); // получили список сабтасок конкретного эпика
        subTasksIdForEpic.add(newSubTaskId);
        createdSubTask.setStatus(Status.NEW);
        updateEpicStatus(epicId);
        return newSubTask;
    }

    public void deleteAllSubTasks() {
        // необходимо также очистить список id сабтасок
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
        return subTasks.get(id);
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
        SubTask newSubTask = subTasks.get(id);                  // получаем ссылку на сабтаску
        Integer epicId = newSubTask.getEpicId();               // получаем id эпика этой сабтаски
        Epic newEpic = epics.get(epicId);                      // получаем ссылку на эпик по идентификатору
        List<Integer> subTasksIdList = newEpic.getSubTasksId(); // получаем список id сабтасок этого эпика
        subTasksIdList.remove(id);                              // удаляем из листа id сабтаски
        subTasks.remove(id);                                    // удаляем из мапы
    }

    public List<SubTask> getSubTasksByEpicId(Integer epicId) {
        List<SubTask> allSubTasksForEpic = new ArrayList<>();        // создаем список для ссылок сабтасок этого эпика

        Epic newEpic = epics.get(epicId);                           // получаем ссылку на эпик по идентификатору и проверяем что эпик не пустой
        if (newEpic != null) {
            List<Integer> subTasksIdForEpic = newEpic.getSubTasksId(); // получаем список с id сабтасок для этого эпика
            for (Integer subTaskId : subTasksIdForEpic) {               // проходимся по нему циклом и находим по id ссылку на эту сабтаску в мапе
                SubTask newSubTask = subTasks.get(subTaskId);
                allSubTasksForEpic.add(newSubTask);                    // добавляем ссылку на сабтаску в список
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
