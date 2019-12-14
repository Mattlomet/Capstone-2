package com.company.LevelUp.service;

import com.company.LevelUp.dao.LevelUpDao;
import com.company.LevelUp.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {
    
    @Autowired
    LevelUpDao levelUpDao;

    public LevelUp saveLevelUp(LevelUp levelUp) {
        return levelUpDao.saveLevelUp(levelUp);
    }

    public LevelUp getLevelUpById(int id) {
        return levelUpDao.getLevelUpById(id);
    }

    public LevelUp getLevelUpByCustomerId(int id){ return levelUpDao.getLevelUpByCustomerId(id); }

    public List<LevelUp> getAllLevelUp() {
        return levelUpDao.getAllLevelUps();
    }

    public void updateLevelUp(LevelUp levelUp) {
        levelUpDao.updateLevelUp(levelUp);
    }

    public void deleteLevelUp(int id) {
        levelUpDao.deleteLevelUp(id);
    }
}
