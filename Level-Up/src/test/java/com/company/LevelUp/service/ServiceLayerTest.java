package com.company.LevelUp.service;


import com.company.LevelUp.dao.LevelUpDao;
import com.company.LevelUp.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    LevelUpDao levelUpDao;


    @Before
    public void setUp(){
        setUpLevelUpMocks();
    }


    @Test
    public void saveGetGetAllLevelUpTest(){
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(10,10,10));
        levelUp.setCustomerId(1);

        levelUp = serviceLayer.saveLevelUp(levelUp);

        assertEquals(levelUp, serviceLayer.getLevelUpById(levelUp.getLevelUpId()));

        assertEquals(levelUp, serviceLayer.getAllLevelUp().get(0));

    }


    public void setUpLevelUpMocks(){
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(10,10,10));
        levelUp.setCustomerId(1);

        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.of(10,10,10));
        levelUp1.setCustomerId(1);

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp1);

        doReturn(levelUp1).when(levelUpDao).saveLevelUp(levelUp);
        doReturn(levelUp1).when(levelUpDao).getLevelUpById(levelUp1.getLevelUpId());
        doReturn(levelUpList).when(levelUpDao).getAllLevelUps();
    }
}
