package com.example.University_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "university_class")
public class UniversityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "number",nullable = false)
    private Integer number;

//    public List<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(List<Student> students) {
//        this.students = students;
//    }

    //
    @OneToMany(mappedBy = "universityClass")
    List<Student> students;

    public UniversityClass(@JsonProperty("id") Long id,
                           @JsonProperty("year")Integer year,
                           @JsonProperty("number") Integer number) {
        this.id = id;
        this.year = year;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonIgnore
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public UniversityClass(){}

    @Override
    public String toString() {
        return "Primary id: "+ this.getId()
                + " Year: " + this.getYear()
                + " Number: " + this.getNumber();

    }
}
