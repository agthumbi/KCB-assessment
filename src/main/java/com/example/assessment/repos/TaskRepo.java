package com.example.assessment.repos;

import com.example.assessment.models.Project;
import com.example.assessment.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Long> {
    @Query(value = "SELECT * FROM TASK  WHERE TITLE =:title", nativeQuery = true)
        //  @Cacheable(value = GET_FUNC_ALL_DETAILS_CACHE)
    Task findByTitle(@Param("title") String title);

    @Query(value = "SELECT t.* FROM TASK t JOIN PROJECT p on p.id=t.project_Id WHERE p.id=:projectId", nativeQuery = true)
        //  @Cacheable(value = GET_FUNC_ALL_DETAILS_CACHE)
    List<Task> findAllTasks(@Param("projectId") Long projectId);
}
