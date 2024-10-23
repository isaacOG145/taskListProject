package utez.edu._b.taskListProject.control;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utez.edu._b.taskListProject.model.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
public class TaskController {

    private List<Task> taskList = new LinkedList<>();

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("tasks", taskList);
        return "index";
    }

    @GetMapping("/tasks/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "taskForm";
    }


    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute Task task) {
        if (task.getTaskName() == null || task.getTaskDescription() == null) {

            return "redirect:/error";
        }
        taskList.add(task);
        return "redirect:/";
    }

    private Task findTaskByName(String name) {
        return taskList.stream()
                .filter(task -> task.getTaskName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam String taskName) {
        Task existingTask = findTaskByName(taskName);

        if (existingTask != null) {
            existingTask.setTaskStatus(!existingTask.getTaskStatus());

        }

        return "redirect:/";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam String taskName) {
        Task existingTask = findTaskByName(taskName);

        if (existingTask != null) {
            taskList.remove(existingTask);
        }


        return "redirect:/";
    }

    @PostMapping("/searchTask")
    public String searchTask(@RequestParam String taskName, Model model) {
        Task foundTask = findTaskByName(taskName);

        if (foundTask != null) {
            model.addAttribute("tasks", List.of(foundTask));
        } else {
            model.addAttribute("tasks", Collections.emptyList());
            model.addAttribute("errorMessage", "Tarea no encontrada.");
        }

        return "index";
    }

    @PostMapping("/searchPendingTasks")
    public String searchPendingTasks(Model model) {
        List<Task> pendingTasks = taskList.stream()
                .filter(task -> !task.getTaskStatus())
                .toList();

        model.addAttribute("tasks", pendingTasks);
        return "index";
    }

    @PostMapping("/clearList")
    public String clearList(){
        taskList.clear();
        return "redirect:/";
    }

    @GetMapping("/countPendingTasks")
    public String countPendingTasks(Model model) {
        long pendingCount = taskList.stream()
                .filter(task -> !task.getTaskStatus())
                .count();

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("tasks", taskList);
        return "index";
    }

    @GetMapping("/download")
    public void downloadTasks(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=tareas.txt");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            for (Task task : taskList) {
                bw.write(task.toString());
                bw.newLine();
                bw.newLine();
            }
            bw.flush(); 
        } catch (IOException e) {
            System.out.println("Algo malo ocurrió: " + e.getMessage());
        }
    }
}