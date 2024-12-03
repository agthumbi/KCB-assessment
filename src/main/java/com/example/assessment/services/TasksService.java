package com.example.assessment.services;

import com.example.assessment.dto.BaseResponse;
import com.example.assessment.models.Project;
import com.example.assessment.models.Task;
import com.example.assessment.repos.TaskRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class TasksService {
    protected final TaskRepo taskRepo;

    public ResponseEntity<?> getUpdatTaskDetails(@Valid Task task, Long taskId) {
        BaseResponse baseResponse = new BaseResponse();
        if(taskId==0){
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Task Id cannot be blank ");

        }
        try {
            Optional<Task> rtvTask = taskRepo.findById(taskId);
            if (rtvTask.isPresent()) {
                rtvTask.get().setTitle(task.getTitle());
                rtvTask.get().setDescription(task.getDescription());
                rtvTask.get().setStatus(task.getStatus());

                taskRepo.save(rtvTask.get());
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Updated task id:" + taskId);
                return ResponseEntity.ok().body(baseResponse);
            } else {
                baseResponse.setResponseCode(1);
                baseResponse.setResponseMessage("Task id:" + taskId + " does not exist(s)");
            }


        } catch (Exception ex) {
            baseResponse.setResponseCode(0);
            baseResponse.setResponseMessage("Updated failed for task id: " + taskId);

        }

        return ResponseEntity.badRequest().body(baseResponse);

    }

    public ResponseEntity<?> getDeleteTask(Long taskId) {
        BaseResponse baseResponse = new BaseResponse();
        if(taskId==0){
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Task Id cannot be blank ");

        }

        try {
            Optional<Task> rtvTask = taskRepo.findById(taskId);
            if (rtvTask.isPresent()) {
                taskRepo.delete(rtvTask.get());
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Deleted task with task id:" + taskId);
                return ResponseEntity.ok().body(baseResponse);
            }

            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Task with task id: "+taskId+" does not exist(s) ");
        } catch (Exception ex) {
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Failed to delete task with task id: " + taskId);

        }

        return ResponseEntity.badRequest().body(baseResponse);

    }

}
