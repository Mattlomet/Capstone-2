package com.company.LevelUp.dao;


import com.company.LevelUp.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpDaoTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp(){
        levelUpDao.getAllLevelUps().stream().forEach(levelUp -> levelUpDao.deleteLevelUp(levelUp.getLevelUpId()));
    }

    @Test
    public void createGetGetAllDeleteLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(10,10,10));
        levelUp.setCustomerId(1);

        levelUp = levelUpDao.saveLevelUp(levelUp);

        assertEquals(levelUp, levelUpDao.getLevelUpById(levelUp.getLevelUpId()));

        assertEquals(1, levelUpDao.getAllLevelUps().size());

        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());

        assertEquals(0, levelUpDao.getAllLevelUps().size());
    }

    @Test
    public void updateLevelUp(){
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(10,10,10));
        levelUp.setCustomerId(1);

        levelUp = levelUpDao.saveLevelUp(levelUp);

        levelUp.setPoints(1000);

        levelUpDao.updateLevelUp(levelUp);

        assertEquals(levelUp.getPoints(), levelUpDao.getLevelUpById(levelUp.getLevelUpId()).getPoints());
    }

    @Test
    public void getLevelUpByCustomerIdTest(){
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(10,10,10));
        levelUp.setCustomerId(1);

        levelUp = levelUpDao.saveLevelUp(levelUp);

        assertEquals(levelUp, levelUpDao.getLevelUpByCustomerId(levelUp.getCustomerId()));
    }

}
