package com.revature.tickITPro.subject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String>{

    // takes in a subject name, returns a Subject (with both name and id)
    @Query(value = "FROM Subject where name = :name")
    Optional<Subject> findBySubjectName(String name);


}
