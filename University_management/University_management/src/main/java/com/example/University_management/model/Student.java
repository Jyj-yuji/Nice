package com.example.University_management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Student {

    // auto increment 是默认的，不加也可以
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    // Many Students to One Class
    @ManyToOne
    @JoinColumn(name = "university_class_id")
    private UniversityClass universityClass;

    public Student (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "name: " + getName() + ", id: " + getId();
        if(getUniversityClass() == null){
            str += ". This student has not joined any class.";
        }else{
            str += (". In class" + getUniversityClass().toString());
        }
        return str;
    }

    public UniversityClass getUniversityClass() {
        return universityClass;
    }

    public void setUniversityClass(UniversityClass universityClass) {
        this.universityClass = universityClass;
    }

    // @OneTOMany
    // private List<test> tests;
}
