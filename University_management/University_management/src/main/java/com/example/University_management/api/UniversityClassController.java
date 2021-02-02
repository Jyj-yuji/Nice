package com.example.University_management.api;


import com.example.University_management.common.exceptions.InvalidUniversityClassException;
import com.example.University_management.model.UniversityClass;
import com.example.University_management.service.UniversityClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {

    private UniversityClassService universityClassService;

    @Autowired
    public UniversityClassController(UniversityClassService universityClassService) {
        this.universityClassService = universityClassService;
    }

    @GetMapping
    List<UniversityClass> GetAllUniversityClasses(){
        return universityClassService.GetAllUniversityClasses();
    }

    @PostMapping@RequestMapping("/add")
    public ResponseEntity<String> AddClass(@RequestBody UniversityClass universityClass){
        try{
            UniversityClass addedUniversityClass = universityClassService.AddUniversityClass(universityClass);
            return ResponseEntity.ok("Class is added.");
        }catch(InvalidUniversityClassException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }
}
