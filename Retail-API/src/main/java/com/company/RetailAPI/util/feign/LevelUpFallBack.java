package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.LevelUp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LevelUpFallBack implements LevelUpFeign  {

    @Override
    public LevelUp getLevelUpByCustomerId(int id) {
        LevelUp levelUp = new LevelUp();
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.of(0,0,0));
        levelUp.setCustomerId(id);
        levelUp.setLevelUpId(0);
        return levelUp;
    }
}
