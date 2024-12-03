package com.example.assessment.controller;

import com.example.assessment.models.Task;
import com.example.assessment.services.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TasksController {
    protected final TasksService tasksService;
    @PutMapping("/{taskid}")
    public ResponseEntity<?> getUpdatTaskDetails(@RequestBody Task task,@PathVariable("taskid") Long taskid){
        return tasksService.getUpdatTaskDetails(task,taskid);

    }
    @DeleteMapping("/{taskid}")
    public ResponseEntity<?> getDeleteTask(@PathVariable("taskid") Long taskid){
        return tasksService.getDeleteTask(taskid);

    }
}
