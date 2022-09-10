package com.revature.tickITPro.department;

import com.revature.tickITPro.department.dto.request.EditDepartmentRequest;
import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.department.dto.response.DepartmentResponse;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){this.departmentService = departmentService;}

    @GetMapping
    public List<DepartmentResponse> findAll(){return departmentService.findAllDepartments();}

    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable String id) {return departmentService.findById(id);}

    @GetMapping("/query")
    @Secured
    public DepartmentResponse findByIdQuery(@RequestParam String id) {return departmentService.findById(id);}

    @PostMapping
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.CREATED)
    public DepartmentResponse register(@RequestBody @Valid NewDepartmentRequest newDepartmentRequest){
        return departmentService.createDepartment(newDepartmentRequest);
    }

    @PutMapping
    @Secured(isAdmin = true)
    public DepartmentResponse update(@RequestBody EditDepartmentRequest editDepartmentRequest){
        return departmentService.update(editDepartmentRequest);
    }

    @DeleteMapping("/{id}")
    @Secured(isAdmin = true)
    @ResponseStatus(value = HttpStatus.OK)
    public String delete(@PathVariable String id){
        departmentService.remove(id);
        return "Department with id \'" + id + "\' has been deleted";
    }
}
