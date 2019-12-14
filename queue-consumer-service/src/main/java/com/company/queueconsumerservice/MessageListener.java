package com.company.queueconsumerservice;


import com.company.queueconsumerservice.util.feign.LevelUpFeign;
import com.company.queueconsumerservice.util.model.LevelUp;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private LevelUpFeign levelUpFeign;


    @RabbitListener(queues = QueueConsumerServiceApplication.QUEUE_NAME)
    public void updateLevelUp(LevelUp levelUp){
        levelUpFeign.updateLevelUp(levelUp);
        System.out.println("Level Up values updated.");
    }
}
