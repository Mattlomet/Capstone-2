package com.company.adminapi.util.feign;


import com.company.adminapi.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "LEVEL-UP-SERVICE")
public interface LevelUpClient {

    @RequestMapping(value = "/levelup",method = RequestMethod.POST)
    public LevelUp createLevelUp(@RequestBody LevelUp levelUp);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.GET)
    public LevelUp getLevelUp(@PathVariable int id);

    @RequestMapping(value = "/levelup/customer/{id}",method = RequestMethod.GET)
    public LevelUp getLevelUpByCustomerId(@PathVariable int id);

    @RequestMapping(value = "/levelup",method = RequestMethod.GET)
    public List<LevelUp> getAllLevelUps();

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable int id, @RequestBody LevelUp levelUp);

    @RequestMapping(value = "/levelup/{id}",method = RequestMethod.DELETE)
    public void deleteLevelUp(@PathVariable int id);
}
