package com.revature.tickITPro.subject;

import com.revature.tickITPro.subject.DTO.requests.EditSubjectRequest;
import com.revature.tickITPro.subject.DTO.requests.NewSubjectRequest;
import com.revature.tickITPro.subject.DTO.responses.SubjectResponse;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subject")
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {this.subjectService = subjectService;}

    @GetMapping
    @Secured
    public List<SubjectResponse> findAll(){return subjectService.readAll();}

    @GetMapping("/{id}")
    @Secured
    public SubjectResponse findById(@PathVariable String id){return subjectService.findById(id);}

    @GetMapping({"/query"})
    @Secured(isAdmin = true)
    public SubjectResponse findByIdQuery(@RequestParam String id){return subjectService.findById(id);}

    @PostMapping
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.CREATED)
    public SubjectResponse create(@RequestBody @Valid NewSubjectRequest newSubjectRequest){
        return subjectService.createSubject(newSubjectRequest);
    }

    @PutMapping
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.OK)
    public SubjectResponse update(@RequestBody @Valid EditSubjectRequest editSubjectRequest){
        return subjectService.update(editSubjectRequest);
    }

    // Delete using @PathVariable
    @DeleteMapping("/{id}")
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.OK)
    public String delete(@PathVariable String id){
        subjectService.remove(id);
        return "Subject with id \'" + id + "\' has been deleted";
    }
}
