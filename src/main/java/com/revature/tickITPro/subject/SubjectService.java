package com.revature.tickITPro.subject;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.subject.DTO.requests.EditSubjectRequest;
import com.revature.tickITPro.subject.DTO.requests.NewSubjectRequest;
import com.revature.tickITPro.subject.DTO.responses.SubjectResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import com.revature.tickITPro.util.exceptions.ResourcePersistanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public SubjectResponse createSubject(NewSubjectRequest newSubjectRequest) throws InvalidUserInputException, ResourcePersistanceException {
        Subject newSubject = new Subject(newSubjectRequest);
        isSubjectValid(newSubject);
        isSubjectNameAvailable(newSubject.getName());
        return new SubjectResponse(subjectRepository.save(newSubject));
    }

    @Transactional(readOnly = true)
    public boolean isSubjectNameAvailable(String subjectName) {
        if (subjectRepository.findBySubjectName(subjectName).isPresent()){
            throw new InvalidUserInputException("Subject name: " + subjectName + " already in use.");
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List<SubjectResponse> readAll() {
        return ((Collection<Subject>) subjectRepository.findAll())
                .stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectResponse findById(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(ResourceNotFoundException::new);
        SubjectResponse subjectResponse = new SubjectResponse(subject);
        return subjectResponse;
    }

    @Transactional(readOnly = true)
    public Subject getSubject(String subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public boolean remove(String subjectId) {
        subjectRepository.deleteById(subjectId);
        return true;
    }

    @Transactional
    public SubjectResponse update(EditSubjectRequest editSubjectRequest) throws RuntimeException {

        Subject updateSubject = subjectRepository.findById(editSubjectRequest.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editSubjectRequest.getName()) && isSubjectNameAvailable(editSubjectRequest.getName())){
            updateSubject.setName(editSubjectRequest.getName());
        }
        return new SubjectResponse(subjectRepository.save(updateSubject));
    }

    public boolean isSubjectValid(Subject testSubject) {
        if (testSubject == null) throw new InvalidUserInputException("Inputted subject was null");
        if (testSubject.getSubjectId()== null || testSubject.getSubjectId().trim().equals("")) throw new InvalidUserInputException("Subject ID was empty or null");
        if (testSubject.getName() == null || testSubject.getName().trim().equals("")) throw new InvalidUserInputException("Subject Name was empty or null");
        return true;
    }

}
