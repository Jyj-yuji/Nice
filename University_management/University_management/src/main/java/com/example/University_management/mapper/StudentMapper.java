package com.example.University_management.mapper;

import com.example.University_management.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    // @param对应数据库语句
    // Select * from student where name like "%name%"
    @Select("SELECT * FROM student WHERE name Like #{name}")
    List<Student> getStudentsWithSimilarName(@Param("name") String name);

//    @Select("SELECT * FROM student WHERE university_class_id IN"
//             + "(SELECT id FROM university_class WHERE year = #{year} and number = #{number}) ")
//    List<Student> getStudentInClass(@Param("year") int year, @Param("number") int number);

    @Select("SELECT * FROM student s INNER JOIN university_class c "+
            "ON s.university_class_id = c.id " +
            "WHERE year = #{year} and number = #{number}")
    List<Student> getStudentInClass(@Param("year") int year, @Param("number") int number);
}
