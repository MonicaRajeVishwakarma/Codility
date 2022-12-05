package com.HelloFresh.interview.statistics.service.impl;

import com.HelloFresh.interview.statistics.exception.ErrorCode;
import com.HelloFresh.interview.statistics.exception.ValidationException;
import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.service.EventsService;
import com.HelloFresh.interview.statistics.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class EventsServiceImpl implements EventsService {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventsServiceImpl(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Boolean add(Events events) {
        validateEvents(events);
        if(isEventsUnderInterval(events.getTimestamp())){
            log.debug("Event is valid"+events);
            applicationEventPublisher.publishEvent(events);
            return  Boolean.TRUE;
        }else{
            log.info("Event is old"+System.currentTimeMillis());
            return Boolean.FALSE;
        }
    }

    private void validateEvents(Events events){
        if(Objects.isNull(events)){
            throw new ValidationException(ErrorCode.EMPTY_REQUEST_BODY_VALIDATION);
        }

        if(Objects.isNull(events.getTimestamp())){
            throw new ValidationException(ErrorCode.MISSING_TIMESTAMP);
        }

        if(Objects.isNull(events.getX())){
            throw  new ValidationException(ErrorCode.MISSING_X_FIELD);
        }

        if(Objects.isNull(events.getY())){
            throw new ValidationException(ErrorCode.MISSING_Y_FIELD);
        }
    }

    private Boolean isEventsUnderInterval(Long timeStamp){
        return timeStamp > (System.currentTimeMillis() - Constants.INTERVAL_IN_MILLISECONDS);
    }
}
