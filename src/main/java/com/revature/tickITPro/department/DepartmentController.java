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
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){this.departmentService = departmentService;}

    @GetMapping
    @Secured(isAdmin = true)
    public List<DepartmentResponse> findAll(){return departmentService.readAll();}

    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable String id) {return departmentService.findById(id);}

    public DepartmentResponse findByIdQuery(@RequestParam String id) {return departmentService.findById(id);}

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DepartmentResponse register(@RequestBody @Valid NewDepartmentRequest newDepartmentRequest){
        return departmentService.createDepartment(newDepartmentRequest);
    }

    @PutMapping
    public String update(@RequestBody EditDepartmentRequest editDepartmentRequest){
        departmentService.update(editDepartmentRequest);
        return "The update was applied to the department";
    }
}
