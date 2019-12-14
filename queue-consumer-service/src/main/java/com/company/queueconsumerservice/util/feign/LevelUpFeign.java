package com.company.queueconsumerservice.util.feign;

import com.company.queueconsumerservice.util.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "level-up-service")
public interface LevelUpFeign {
    @PutMapping("/levelUp")
    void updateLevelUp(@RequestBody LevelUp levelUp);
}
