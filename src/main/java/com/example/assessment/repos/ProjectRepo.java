package com.example.assessment.repos;

import com.example.assessment.models.Project;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepo extends JpaRepository<Project,Long > {

    @Query(value = "SELECT * FROM PROJECT  WHERE NAME =:name", nativeQuery = true)
  //  @Cacheable(value = GET_FUNC_ALL_DETAILS_CACHE)
    Project findByName(@Param("name") String name);


}
