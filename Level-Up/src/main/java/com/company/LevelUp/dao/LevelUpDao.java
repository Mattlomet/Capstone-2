package com.company.LevelUp.dao;

import com.company.LevelUp.model.LevelUp;

import java.util.List;

public interface LevelUpDao {
    LevelUp saveLevelUp(LevelUp levelUp);
    LevelUp getLevelUpById(int id);
    List<LevelUp> getAllLevelUps();
    void updateLevelUp(LevelUp levelUp);
    void deleteLevelUp(int id);
    LevelUp getLevelUpByCustomerId(int customerId);
}
