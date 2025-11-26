package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import com.workintech.zoo.validation.ZooKangarooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer,Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> find(){
        return this.kangaroos.values().stream().toList();
    }

    @GetMapping("{id}")
    public Kangaroo findById(@PathVariable("id") int id){
      ZooKangarooValidation.isIdValid(id);
      ZooKangarooValidation.checkKangarooExistence(kangaroos,id,true);
      return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo){
        if (kangaroo.getName() == null || kangaroo.getName().isEmpty() || kangaroo.getName().isBlank()) {

            throw new ZooException("Name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        ZooKangarooValidation.checkKangarooExistence(kangaroos,kangaroo.getId(),false);
        ZooKangarooValidation.checkKangarooWeight(kangaroo.getWeight());
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("{id}")
    public Kangaroo update(@PathVariable Integer id, @RequestBody Kangaroo kangaroo){
        ZooKangarooValidation.isIdValid(id);
        ZooKangarooValidation.checkKangarooWeight(kangaroo.getWeight());
        kangaroo.setId(id);
        if(kangaroos.containsKey(id)){
            kangaroos.put(id,kangaroo);
            return kangaroos.get(id);
        }else{
            return save(kangaroo);
        }
    }

    @DeleteMapping("{id}")
    public Kangaroo delete(@PathVariable Integer id){
        ZooKangarooValidation.isIdValid(id);
        ZooKangarooValidation.checkKangarooExistence(kangaroos,id, true);
        return kangaroos.remove(id);
    }
}
