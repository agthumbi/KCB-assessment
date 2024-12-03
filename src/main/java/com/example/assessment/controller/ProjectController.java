package com.example.assessment.controller;


import com.example.assessment.models.Project;
import com.example.assessment.models.Task;
import com.example.assessment.services.ProjectService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        if (!ex.getConstraintViolations().isEmpty()) {
            for (ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
                String fieldName = null;
                for (Path.Node node : constraintViolation.getPropertyPath()) {
                    fieldName = node.getName();
                }
                errors.put(fieldName, constraintViolation.getMessage());
            }
        }

        return errors;
    }

}
