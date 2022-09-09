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

    @Transactional
    public DepartmentResponse createDepartment(NewDepartmentRequest newDepartmentRequest) throws InvalidUserInputException, ResourcePersistanceException {
        Department newDepartment = new Department(newDepartmentRequest);
        isDepartmentValid(newDepartment);
        isDepartmentAvailable(newDepartment.getDepartmentName());
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
    public List<DepartmentResponse> findAllDepartments() {
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

    @Transactional(readOnly = true)
    public Department getDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(ResourceNotFoundException::new);
        return department;
    }

    @Transactional
    public boolean remove(String departmentId) {
        departmentRepository.deleteById(departmentId);
        return true;
    }

    @Transactional
    public DepartmentResponse update(EditDepartmentRequest editDepartmentRequest) throws RuntimeException {

        Department updateDepartment = departmentRepository.findById(editDepartmentRequest.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editDepartmentRequest.getDepartmentName()) && isDepartmentAvailable(editDepartmentRequest.getDepartmentName())){
            updateDepartment.setDepartmentName(editDepartmentRequest.getDepartmentName());
        }
        return new DepartmentResponse(departmentRepository.save(updateDepartment));
    }

    public boolean isDepartmentValid(Department testDepartment) {
        if (testDepartment == null) throw new InvalidUserInputException("Inputted department was null");
        if (testDepartment.getDepartmentId()== null || testDepartment.getDepartmentId().trim().equals("")) throw new InvalidUserInputException("Department ID was empty or null");
        if (testDepartment.getDepartmentName()== null || testDepartment.getDepartmentName().trim().equals("")) throw new InvalidUserInputException("Department Name was empty or null");
        return true;
    }

}
