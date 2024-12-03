package com.example.assessment.controller;


import com.example.assessment.models.Project;
import com.example.assessment.models.Task;
import com.example.assessment.services.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@AllArgsConstructor
public class ProjectController {
    protected final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> getCreateProject(@RequestBody Project project) {


        return projectService.getCreateProject(project);

    }

    @GetMapping
    public ResponseEntity<?> getRetrieveAllProject() {
        return projectService.getRetrieveAllProjects();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getSpecificProject(@PathVariable ("projectId") Long projectId) {

        return projectService.getSpecificProject(projectId);

    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<?> getCreateTask(@RequestBody Task task,@PathVariable("projectId") Long projectId) {
        return projectService.getCreateTask(task,projectId);

    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<?> getRetrieveAllTasks(@PathVariable("projectId") Long projectId) {
        return projectService.getRetrieveAllTasks(projectId);

    }


}
