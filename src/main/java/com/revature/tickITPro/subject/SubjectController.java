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
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {this.subjectService = subjectService;}

    @GetMapping
    @Secured(isAdmin = true)
    public List<SubjectResponse> findAll(){return subjectService.readAll();}

    @GetMapping("/{id}")
    @Secured(isAdmin = true)
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
    public String update(@RequestBody @Valid EditSubjectRequest editSubjectRequest){
        subjectService.update(editSubjectRequest);
        return "Subject has been updated.";
    }

    // Delete using @PathVariable
    @DeleteMapping("/{id}")
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.OK)
    public String delete(@PathVariable String id){
        subjectService.remove(id);
        return "Subject has been removed.";
    }

    // Delete using @RequestParam
    @DeleteMapping({"/query"})
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.OK)
    public String remove(@RequestParam String id){
        subjectService.remove(id);
        return "Subject has been removed.";
    }
}
