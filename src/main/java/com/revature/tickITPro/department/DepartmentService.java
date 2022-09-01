package com.revature.tickITPro.department;

import com.revature.tickITPro.department.dto.request.EditDepartmentRequest;
import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.department.dto.response.DepartmentResponse;
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
public class DepartmentService {


    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public DepartmentResponse createDepartment(NewDepartmentRequest newDepartmentRequest) throws InvalidUserInputException, ResourcePersistanceException {
        Department newDepartment = new Department(newDepartmentRequest);
        isDepartmentAvailable(newDepartmentRequest.getDepartmentName());
        return new DepartmentResponse(departmentRepository.save(newDepartment));
    }

    @Transactional(readOnly = true)
    public boolean isDepartmentAvailable(String departmentName) {
        if (departmentRepository.findByDepartmentName(departmentName).isPresent()){
            throw new InvalidUserInputException("Department name: " + departmentName + " already in use.");
    }
        return true;
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> readAll() {
        return ((Collection<Department>) departmentRepository.findAll())
                .stream()
                .map(DepartmentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(ResourceNotFoundException::new);
        DepartmentResponse departmentResponse = new DepartmentResponse(department);
        return departmentResponse;
    }

    @Transactional
    public void remove(String departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Transactional
    public void update(EditDepartmentRequest editDepartmentRequest) throws InvalidUserInputException {

        Department updateDepartment = departmentRepository.findById(editDepartmentRequest.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editDepartmentRequest.getDepartmentName()) && isDepartmentAvailable(editDepartmentRequest.getDepartmentName())){
            updateDepartment.setDepartmentName(editDepartmentRequest.getDepartmentName());

        }
    }

}
