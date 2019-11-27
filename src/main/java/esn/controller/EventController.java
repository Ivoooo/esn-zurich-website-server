package esn.controller;

import esn.entity.Event;
import esn.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EventController {
    private final EventService service;
    private String constUsers = "events";

    @Autowired
    EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/events")
    Resources<Resource<Event>> getAllEvents() {
        List<Resource<Event>> events = StreamSupport.stream(this.service.getEvents().spliterator(), false)
                .map(event -> new Resource<>(event,
                        linkTo(methodOn(EventController.class).getEvent(event.getId())).withSelfRel(),
                        linkTo(methodOn(EventController.class).getAllEvents()).withRel(constUsers)))
                .collect(Collectors.toList());

        return new Resources<>(events,
                linkTo(methodOn(EventController.class).getAllEvents()).withSelfRel());
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Resource<Event> createEvent(@RequestBody Event newEvent) {
        Event event = this.service.createEvent(newEvent);

        return new Resource<>(event,
                linkTo(methodOn(EventController.class).getEvent(event.getId())).withSelfRel(),
                linkTo(methodOn(EventController.class).getAllEvents()).withRel(constUsers));
    }

    @GetMapping("/events/{id}")
    Resource<Event> getEvent(@PathVariable Long id) {
        Optional<Event> event = this.service.getEvent(id);
        if(event.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find event [getEvent()]");
        }
        return new Resource<>(event.get(),
                linkTo(methodOn(EventController.class).getEvent(id)).withSelfRel(),
                linkTo(methodOn(EventController.class).getAllEvents()).withRel(constUsers));
    }

    @PutMapping("/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Event updateEvent(@PathVariable Long id, @RequestBody Event changedEvent) {
        Optional<Event> currentEvent = this.service.getEvent(id);
        if(currentEvent.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find event [updateEvent()] with ID" + id);
        }

        return this.service.updateEvent(currentEvent.get(), changedEvent);
    }
}
