package com.nieyue.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Config;
import com.nieyue.service.ConfigService;
import com.nieyue.util.MyDom4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@Configuration
public class AddressTask {


    //每天早上6点到晚上9点，每隔10分钟执行一次
    @Scheduled(cron="0 0/10 6-21 * * ?")
    public void perSeconds(){

    }

}
