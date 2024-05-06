package com.wl.emall.config;

import com.wl.emall.common.batch.CornJob;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import jakarta.annotation.Resource;

@EnableScheduling
@Configuration
public class ScheduleConfigure implements SchedulingConfigurer {

    @Resource
    private List<CornJob> jobList;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        jobList.forEach(x -> registrar.addCronTask(x, x.corn()));
    }

}
