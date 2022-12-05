package com.HelloFresh.interview.statistics.controller;

import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/event")
public class EventController {

    private final EventsService eventsService;

    @Autowired
    public EventController(EventsService eventsService){
        this.eventsService = eventsService;
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Events events){
        HttpStatus httpStatus = eventsService.add(events) ? HttpStatus.ACCEPTED : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(httpStatus).build();
    }
}
