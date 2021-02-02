package com.example.University_management.dao;


import com.example.University_management.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// 第一个是model，第二个是long
public interface StudentDao extends CrudRepository<Student, Long> {

    // select * from student where name =
    List<Student> findByName(String name);
}
