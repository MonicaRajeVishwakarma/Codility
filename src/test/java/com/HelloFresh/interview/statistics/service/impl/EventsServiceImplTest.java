package com.HelloFresh.interview.statistics.service.impl;

import com.HelloFresh.interview.statistics.eventlistener.InputEventListener;
import com.HelloFresh.interview.statistics.exception.ValidationException;
import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.service.EventsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EventsServiceImplTest {

    private ApplicationEventPublisher applicationEventPublisher;

    private EventsService eventsServiceTest;

    @Before
    public void setUp(){
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        eventsServiceTest = new EventsServiceImpl(applicationEventPublisher);
    }

    @Test(expected = ValidationException.class)
    public void add_ShouldThrowValidationExceptionWhenEventIsNull(){
        Events events = null;
        eventsServiceTest.add(events);
    }

    @Test(expected = ValidationException.class)
    public void add_ShouldThrowValidationExceptionWhenEventTimeStampIsNull(){
        Events events = new Events(null,new BigDecimal(0.123456789),new BigDecimal(123456789));
        eventsServiceTest.add(events);
    }

    @Test(expected = ValidationException.class)
    public void add_ShouldThrowValidationExceptionWhenEventXIsNull(){
        Events events = new Events(System.currentTimeMillis(),null,new BigDecimal(123456789));
        eventsServiceTest.add(events);
    }

    @Test(expected = ValidationException.class)
    public void add_ShouldThrowValidationExceptionWhenEventYIsNull(){
        Events events = new Events(System.currentTimeMillis(),new BigDecimal(0.123456789),null);
        eventsServiceTest.add(events);
    }

    @Test
    public void add_shouldReturnTrueWhenEventTimestampIsCurrentTimestamp() {

        doNothing().when(applicationEventPublisher).publishEvent(any());
        Events event = new Events(System.currentTimeMillis(),new BigDecimal(0.123456789),new BigDecimal(123456789));
        Boolean result = eventsServiceTest.add(event);
        assertTrue(result);
    }

    @Test
    public void add_shouldReturnTrueWhenEventTimestampIsOld() {

        Long oldTimestamp = System.currentTimeMillis() - 80000L;
        Events event = new Events(oldTimestamp,new BigDecimal(0.123456789),new BigDecimal(123456789));
        Boolean result = eventsServiceTest.add(event);
        assertFalse(result);
    }

    @Test
    public void add_shouldCallApplicationEventPublisherOnce() {

        doNothing().when(applicationEventPublisher).publishEvent(any());
        Events event = new  Events(System.currentTimeMillis(),new BigDecimal(0.123456789),new BigDecimal(123456789));
        eventsServiceTest.add(event);
        verify(applicationEventPublisher, times(1))
                .publishEvent(event);
    }
}