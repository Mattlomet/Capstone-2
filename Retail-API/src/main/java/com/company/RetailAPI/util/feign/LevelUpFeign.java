package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "level-up-service",fallback = LevelUpFallBack.class)
public interface LevelUpFeign {

    @GetMapping("/levelUp/customer/{id}")
    LevelUp getLevelUpByCustomerId(@PathVariable int id);

}
