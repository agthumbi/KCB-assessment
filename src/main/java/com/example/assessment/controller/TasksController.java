package com.example.assessment.controller;

import com.example.assessment.models.Task;
import com.example.assessment.services.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public class TasksController {
    protected final TasksService tasksService;
    @PutMapping("/{taskid}")
    @Operation(summary = "Update a task’s details", description = "Update a task’s details by providing its details.")
    public ResponseEntity<?> getUpdatTaskDetails(@RequestBody Task task,@PathVariable("taskid") Long taskid){
        return tasksService.getUpdatTaskDetails(task,taskid);

    }
    @DeleteMapping("/{taskid}")
    @Operation(summary = "Delete a task", description = "Delete a task by providing its details.")
    public ResponseEntity<?> getDeleteTask(@PathVariable("taskid") Long taskid){
        return tasksService.getDeleteTask(taskid);

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
