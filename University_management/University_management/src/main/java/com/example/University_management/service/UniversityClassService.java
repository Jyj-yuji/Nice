package com.example.University_management.service;


import com.example.University_management.common.exceptions.InvalidUniversityClassException;
import com.example.University_management.dao.UniversityClassDao;
import com.example.University_management.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UniversityClassService {

    private UniversityClassDao universityClassDao;

    @Autowired
    public UniversityClassService(UniversityClassDao UCD){
        this.universityClassDao = UCD;
    }

    public List<UniversityClass> GetAllUniversityClasses(){
        return (List<UniversityClass>)universityClassDao.findAll();
    }

    public UniversityClass AddUniversityClass(UniversityClass universityClass){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(universityClass.getYear() < currentYear){
            throw new InvalidUniversityClassException("Cannot add class in the past!");
        } else if (universityClass.getYear() > currentYear + 2){
            throw new InvalidUniversityClassException("Cannot add class in the far future");
        }

        if(universityClass.getNumber() < 0){
            throw new InvalidUniversityClassException("Class number cannot be negative");
        }

        return universityClassDao.save(universityClass);
    }
}
