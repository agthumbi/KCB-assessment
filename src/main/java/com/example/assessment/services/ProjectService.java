package com.example.assessment.services;

import com.example.assessment.dto.BaseResponse;
import com.example.assessment.models.Project;
import com.example.assessment.models.Task;
import com.example.assessment.repos.ProjectRepo;
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
public class ProjectService {

    protected final ProjectRepo projectRepo;
    protected final TaskRepo taskRepo;


    public ResponseEntity<?> getCreateProject(@Valid Project project) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Project retvProject = projectRepo.findByName(project.getName());
            if (retvProject != null) {
                baseResponse.setResponseCode(1);
                baseResponse.setResponseMessage("Project:" + project.getName() + " already exists");
            } else {
                projectRepo.save(project);
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Successfully Created " + project.getName());
            }
        } catch (Exception ex) {
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Failed to Create " + project.getName());

        }

        return ResponseEntity.badRequest().body(baseResponse);

    }

    public ResponseEntity<?> getRetrieveAllProjects() {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Project> projectList = projectRepo.findAll();
            if (projectList.size() > 0) {
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Successfully fetched projects");
                baseResponse.setData(projectList);
                return ResponseEntity.ok().body(baseResponse);
            } else {
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Failed to fetch projects/they dont exists");
            }
        } catch (Exception ex) {
            baseResponse.setResponseCode(0);
            baseResponse.setResponseMessage("Something went wront please try again");
        }
        return ResponseEntity.badRequest().body(baseResponse);
    }

    public ResponseEntity<?> getSpecificProject(Long projectId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Optional<Project> retvProject = projectRepo.findById(projectId);
            if (retvProject.isPresent()) {
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Successfully fetched " + retvProject.get().getName());
                baseResponse.setData(retvProject);
                return ResponseEntity.ok().body(baseResponse);
            } else {
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Project with project id: " + projectId + " or does not exist(s)");

            }

        } catch (Exception ex) {
            baseResponse.setResponseCode(0);
            baseResponse.setResponseMessage("Failed to Fetch project with project id:" + projectId);

        }

        return ResponseEntity.badRequest().body(baseResponse);

    }

    public ResponseEntity<?> getCreateTask(@Valid Task task, Long projectId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Optional<Project> retvProject = projectRepo.findById(projectId);
            if (retvProject.isPresent()) {
                Optional<Task> retvTask = Optional.ofNullable(taskRepo.findByTitle(task.getTitle()));
                if(!retvTask.isPresent()) {
                    task.setProjectId(projectId);
                    taskRepo.save(task);
                    baseResponse.setResponseCode(0);
                    baseResponse.setResponseMessage("Successfully Created " + task.getTitle());
                    return ResponseEntity.ok().body(baseResponse);
                }
                else {
                    baseResponse.setResponseCode(1);
                    baseResponse.setResponseMessage("Task with title:" + task.getTitle()+" already exist(s)");
                }
            } else {
                baseResponse.setResponseCode(1);
                baseResponse.setResponseMessage("Project with project id: " + projectId + " or does not exist(s)");
            }
        } catch (Exception ex) {
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Failed to  Create " + task.getTitle());

        }

        return ResponseEntity.badRequest().body(baseResponse);

    }

    public ResponseEntity<?> getRetrieveAllTasks(Long projectId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            Optional<Project> retvProject = projectRepo.findById(projectId);
            if (retvProject.isPresent()) {
                List<Task> taskList = taskRepo.findAllTasks(projectId);
                if (taskList.size() > 0) {
                    baseResponse.setResponseCode(0);
                    baseResponse.setResponseMessage("Successfully fetched tasks");
                    baseResponse.setData(taskList);
                    return ResponseEntity.ok().body(baseResponse);
                } else {
                    baseResponse.setResponseCode(1);
                    baseResponse.setResponseMessage("Failed to fetch tasks/they dont exists");
                }
            } else {
                baseResponse.setResponseCode(0);
                baseResponse.setResponseMessage("Failed to fetch project/they dont exists");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            baseResponse.setResponseCode(1);
            baseResponse.setResponseMessage("Something went wront please try again");
        }
        return ResponseEntity.badRequest().body(baseResponse);
    }
}
