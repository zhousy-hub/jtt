package net.fxft.gateway.jtt.service;

import net.fxft.gateway.jtt.vo.GpsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author fangli
 */
@Service
public class MessageProcessService {

    public MessageProcessService() {
        time = System.currentTimeMillis();
    }

    public static long num;
    public static long time;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void processMsg(GpsMessage tm) {
        num++;
        System.out.println(tm.toString());
        kafkaTemplate.send("jtt",tm.getPlateNo(),tm.getData());
    }


    int getPlateNoId(String plateNo){
        return 0;
    }
}
