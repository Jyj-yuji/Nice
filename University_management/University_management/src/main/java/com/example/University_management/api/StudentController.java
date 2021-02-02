package com.example.University_management.api;

import com.example.University_management.common.exceptions.InvalidUniversityClassException;
import com.example.University_management.common.exceptions.StudentEmptyNameException;
import com.example.University_management.common.exceptions.StudentNonExistException;
import com.example.University_management.model.Student;
import com.example.University_management.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> GetAllStudents(){
        return studentService.GetAllStudents();
    }

    @GetMapping
    @RequestMapping("/similar_name")
    public ResponseEntity<List<Student>> GetStudentsBySimilarName(@RequestParam String name){
        if(name.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        return ResponseEntity.ok(studentService.GetStudentsWithSimilarName(name));
    }

    @GetMapping
    @RequestMapping()
    public List<Student> GetStudentsBySimilarName(@RequestParam int year, @RequestParam int number){
        return studentService.GetStudentsInClass(year, number);
    }


    @GetMapping
    @RequestMapping("/name")
    public ResponseEntity<List<Student>> GetStudents(@RequestParam String name){
        if(name.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        return ResponseEntity.ok(studentService.GetStudentsByName(name));
    }

    @PostMapping
    @RequestMapping("/register")
    @RequiresPermissions("student:write")
    public ResponseEntity<String> Register(@RequestBody Student student){
        if(student == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student cannot be null!");
        }
        try {
            Student saved_student = studentService.AddStudent(student);
            return ResponseEntity.ok("Registered student: " + saved_student.toString());
        } catch (StudentEmptyNameException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @PostMapping
    @RequestMapping("/update")
    public ResponseEntity<String> Update(@RequestBody Student studentToUpdate){
        if(studentToUpdate.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student cannot be null!");
        }
        try {
            Student updated_student = studentService.UpdateStudent(studentToUpdate);
            return ResponseEntity.ok("Updated student: " + updated_student.toString());
        } catch (StudentNonExistException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (StudentEmptyNameException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ResponseEntity<String> Delete(@RequestBody Student studentToDelete){
        if(studentToDelete.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student cannot be null!");
        }
        try {
            studentService.DeleteStudent(studentToDelete);
            return ResponseEntity.ok("Deleted student.");
        } catch (StudentNonExistException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch (StudentEmptyNameException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    @PostMapping("/assign/{sid}/{cid}")
    public ResponseEntity<String> AssignClass(@PathVariable("sid") Long studentId,
                                              @PathVariable("cid") Long universityId){
        try{
            Student updatedStudent = studentService.AssignClass(studentId, universityId);
            return ResponseEntity.ok("Assigned student to class" + updatedStudent.toString());
        } catch (StudentNonExistException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        } catch(InvalidUniversityClassException err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }
}
