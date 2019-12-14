package com.company.LevelUp.controller;


import com.company.LevelUp.model.LevelUp;
import com.company.LevelUp.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @GetMapping("/levelUp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpById(@PathVariable int id){
        return serviceLayer.getLevelUpById(id);
    }

    @GetMapping("/levelUp/all")
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUp(){
        return serviceLayer.getAllLevelUp();
    }

    @GetMapping("/levelUp/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomerId(@PathVariable int id){
        return serviceLayer.getLevelUpByCustomerId(id);
    }


    // POST METHODS

    @PostMapping("/levelUp")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp saveLevelUp(@RequestBody LevelUp levelUp){
        return serviceLayer.saveLevelUp(levelUp);
    }

    // UPDATE METHODS

    @PutMapping("/levelUp")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@RequestBody LevelUp levelUp){
        serviceLayer.updateLevelUp(levelUp);
    }

    // DELETE METHODS

    @DeleteMapping("/levelUp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLevelUp(@PathVariable int id){
        serviceLayer.deleteLevelUp(id);
    }
}
