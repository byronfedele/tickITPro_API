package com.revature.tickITPro.subject;

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

    @Transactional(readOnly = true)
    public SubjectResponse createSubject(NewSubjectRequest newSubjectRequest) throws InvalidUserInputException, ResourcePersistanceException {
        Subject subject = new Subject(newSubjectRequest);
        isSubjectNameAvailable(newSubjectRequest.getName());
        return new SubjectResponse(subjectRepository.save(subject));
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
    public void remove(String subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @Transactional
    public void update(EditSubjectRequest editSubjectRequest) throws InvalidUserInputException {

        Subject updateSubject = subjectRepository.findById(editSubjectRequest.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editSubjectRequest.getName()) && isSubjectNameAvailable(editSubjectRequest.getName())){
            updateSubject.setName(editSubjectRequest.getName());

        }
    }

}
