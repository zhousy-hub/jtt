package net.fxft.gateway.jtt;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import net.fxft.gateway.jtt.service.MessageProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Scheduled(fixedDelay = 10000)
    private void print(){
        long time2 = System.currentTimeMillis();
        String day = DateUtil.formatBetween(time2 -MessageProcessService.time, BetweenFormater.Level.MINUTE);
        StringBuffer sb = new StringBuffer();
        sb.append("接收交通厅数据量：").append(MessageProcessService.num).append(";")
                .append("接收时间：").append(day).append(";")
                .append("接收速率:").append(MessageProcessService.num*1000/(time2 -MessageProcessService.time));

         logger.debug(sb.toString());
    }

    @RequestMapping("/index")
    public String index(){
        long time2 = System.currentTimeMillis();
        String day = DateUtil.formatBetween(time2 -MessageProcessService.time, BetweenFormater.Level.MINUTE);
        StringBuffer sb = new StringBuffer();
        sb.append("接收数据：").append(MessageProcessService.num).append("<br/>")
                .append("接收时间：").append(day).append("<br/>")
                .append("接收速率:").append(MessageProcessService.num*1000/(time2 -MessageProcessService.time));
        return sb.toString();
    }

    @RequestMapping("/selRecvByPlateNo")
    public String selRecvByPlateNo(String plateNoColor,String date){
        long time2 = System.currentTimeMillis();
        String day = DateUtil.formatBetween(time2 -MessageProcessService.time, BetweenFormater.Level.MINUTE);
        StringBuffer sb = new StringBuffer();
        sb.append("接收数据：").append(MessageProcessService.num).append("<br/>")
                .append("接收时间：").append(day).append("<br/>")
                .append("接收速率:").append(MessageProcessService.num*1000/(time2 -MessageProcessService.time));
        return sb.toString();
    }
}
