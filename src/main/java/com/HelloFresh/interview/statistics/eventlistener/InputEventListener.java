package com.HelloFresh.interview.statistics.eventlistener;

import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InputEventListener {

    public final StatisticsService statisticsService;

    @Autowired
    public InputEventListener(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @Async
    @EventListener
    public void handleEvent(Events events){
        log.debug("[EVENT-LISTENER] event triggered with the data: {}", events);
        statisticsService.compute(events, System.currentTimeMillis());
    }

}
