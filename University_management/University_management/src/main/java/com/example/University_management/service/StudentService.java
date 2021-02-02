package com.example.University_management.service;

import com.example.University_management.common.exceptions.InvalidUniversityClassException;
import com.example.University_management.common.exceptions.StudentEmptyNameException;
import com.example.University_management.common.exceptions.StudentNonExistException;
import com.example.University_management.dao.StudentDao;
import com.example.University_management.dao.UniversityClassDao;
import com.example.University_management.mapper.StudentMapper;
import com.example.University_management.model.Student;
import com.example.University_management.model.UniversityClass;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

// 很多Validation放在这个service里面！

@Service
public class StudentService {

    StudentDao studentDao;
    UniversityClassDao universityClassDao;
    StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentDao studentDao,
                          UniversityClassDao universityClassDao,
                          StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.universityClassDao = universityClassDao;
        this.studentMapper = studentMapper;
    }


    public Student AddStudent(Student student){
        if(student.getName().isEmpty()){
            throw new StudentEmptyNameException("Student name cannot be Empty!");
        }

        // 会自动区分ADD 和 UPDATE
        return studentDao.save(student);
    }

    public Student UpdateStudent(Student student){
        if(student.getId() == null || !studentDao.existsById(student.getId())){
            throw new StudentNonExistException("ID is null or not in Data Base.");
        }
        if(student.getName().isEmpty()){
            throw new StudentEmptyNameException("Student name cannot be Empty!");
        }
        return studentDao.save(student);
    }

    public Student AssignClass(Long sid, Long cid){
        if(!studentDao.existsById(sid)){
            throw new StudentNonExistException("Cannot find student with Id: "+sid);
        }
        if(!universityClassDao.existsById(cid)){
            throw new InvalidUniversityClassException("Cannot find class with Id: "+cid);
        }

        Student student = GetStudentById(sid).get();
        UniversityClass universityClass = universityClassDao.findById(cid).get();
        student.setUniversityClass(universityClass);
        return studentDao.save(student);
    }

    public List<Student> GetAllStudents(){
        return (List<Student>)studentDao.findAll();
    }

    public Optional<Student> GetStudentById(Long Id){
        return studentDao.findById(Id);
    }

    public void DeleteStudent(Student student){
        if(student.getId() == null || !studentDao.existsById(student.getId())){
            throw new StudentNonExistException("ID is null or not in Data Base.");
        }
        studentDao.delete(student);
    }

    public List<Student> GetStudentsByName (String name){
        return studentDao.findByName(name);
    }

    public List<Student> GetStudentsWithSimilarName(String name){
        return studentMapper.getStudentsWithSimilarName("%" + name + "%");
    }

    public List<Student> GetStudentsInClass(int year, int number){
        return studentMapper.getStudentInClass(year, number);
    }
}
