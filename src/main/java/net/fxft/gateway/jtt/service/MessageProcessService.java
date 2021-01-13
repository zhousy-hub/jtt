package net.fxft.gateway.jtt.service;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import net.fxft.gateway.jtt.IndexController;
import net.fxft.gateway.jtt.util.ByteUtil;
import net.fxft.gateway.jtt.vo.GpsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author fangli
 */
@Service
public class MessageProcessService {

    //private Logger logger = LoggerFactory.getLogger(MessageProcessService.class);

    public MessageProcessService() {
        time = System.currentTimeMillis();
    }

    public static long num = 0;
    public static long time;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void processMsg(GpsMessage tm) {
        if (num == Long.MAX_VALUE - 1){
            num = 0;
        }else{
            num++;
        }
        kafkaTemplate.send("jtt",tm.getPlateNo(),tm.getData());
    }

}
