package com.company.adminapi.controller;

import com.company.adminapi.exception.NotFoundException;
import com.company.adminapi.model.LevelUp;
import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.util.feign.LevelUpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/levelup")
@CacheConfig(cacheNames = {"levelup"})
public class LevelUpController {
    @Autowired
    ServiceLayer service;

    @CachePut(key = "#result.getId()")
    @PostMapping//Another way to set the Rest API Post mapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp) {
        return service.createLevelUp(levelUp);
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int id) {
        LevelUp levelUp = service.getLevelUp(id);
        if(levelUp == null)
            throw new NotFoundException("There is no level up points with this id " + id + ".");

        return levelUp;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        List<LevelUp> levelUps = service.getAllLevelUps();
        if(levelUps.size() == 0)
            throw new NotFoundException("No level up points found.");

        return levelUps;
    }

    @CacheEvict(key = "#levelup.getId()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoiceItem(@PathVariable int id, @RequestBody @Valid LevelUp levelUp) {
        if(levelUp.getId() == 0)
            levelUp.setId(id);
        if(levelUp.getId() != id)
            throw new IllegalArgumentException("Level up ID must match");

        service.updateLevelUp(id,levelUp);
    }
    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id) {
        service.deleteLevelUp(id);
    }
}
