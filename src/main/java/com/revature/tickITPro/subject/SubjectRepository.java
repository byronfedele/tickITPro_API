package com.revature.tickITPro.subject;
import com.revature.tickITPro.subject.DTO.responses.SubjectResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String>{


}
