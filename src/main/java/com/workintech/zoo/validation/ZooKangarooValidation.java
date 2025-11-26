package com.workintech.zoo.validation;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ZooKangarooValidation {
    public static void isIdValid(Integer id) {
        if(id == null || id < 0){
            throw new ZooException("id is not valid : " + id , HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkKangarooExistence(Map<Integer, Kangaroo> kangaroos, int id, boolean existence) {
        if (existence){
            if(!kangaroos.containsKey(id)){
                throw new ZooException("Record is not exist: " + id, HttpStatus.NOT_FOUND);
            }
        }
        else{
            if(kangaroos.containsKey(id)){
                throw new ZooException("Recodr is already exist: " + id, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public static void checkKangarooWeight(Double weight) {
        if(weight == null || weight <0){
            throw new ZooException("Weight shouldn't be null or less than zero : " + weight, HttpStatus.BAD_REQUEST);
        }
    }
}
