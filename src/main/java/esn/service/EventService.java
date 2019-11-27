package esn.service;

import esn.entity.Event;
import esn.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    @Autowired
    public EventService(@Qualifier("eventRepository") EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Iterable<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    public Optional<Event> getEvent(Long id) {return this.eventRepository.findById(id);}

    public Event createEvent(Event newEvent) {
        newEvent.setCreationDate(LocalDateTime.now());
        eventRepository.save(newEvent);
        log.debug("Created Information for User: {}", newEvent);
        return newEvent;
    }

    public Event updateEvent(Event event, Event changedEvent) {
        if(changedEvent.getTitle() != null && !changedEvent.getTitle().isEmpty()) {
            event.setTitle(changedEvent.getTitle());
        }

        if(changedEvent.getAddress() != null && !changedEvent.getAddress().isEmpty()) {
            event.setAddress(changedEvent.getAddress());
        }

        if(changedEvent.getEnglishText() != null && !changedEvent.getEnglishText().isEmpty()) {
            event.setEnglishText(changedEvent.getEnglishText());
        }

        if(changedEvent.getGermanText() != null && !changedEvent.getGermanText().isEmpty()) {
            event.setGermanText(changedEvent.getGermanText());
        }

        if(changedEvent.getStartDate() != null) {
            event.setStartDate(changedEvent.getStartDate());
        }

        if(changedEvent.getEndDate() != null) {
            event.setEndDate(changedEvent.getEndDate());
        }

        if(changedEvent.getMaxParticipants() != 0 && !changedEvent.getAddress().isEmpty()) {
            event.setAddress(changedEvent.getAddress());
        }

        if(changedEvent.getMaxParticipants() >= 0) {
            event.setMaxParticipants(changedEvent.getMaxParticipants());
        }

        if(changedEvent.getFullPrice() >= 0) {
            event.setFullPrice(changedEvent.getFullPrice());
        }

        if(changedEvent.getEsnPrice() >= 0) {
            event.setEsnPrice(changedEvent.getEsnPrice());
        }

        if(changedEvent.getSubsidyRequested() >= 0) {
            event.setSubsidyRequested(changedEvent.getSubsidyRequested());
        }

        if(changedEvent.getSubsidyApproved() >= 0) {
            event.setSubsidyApproved(changedEvent.getSubsidyApproved());
        }

        if(changedEvent.getSubsidyUsed() >= 0) {
            event.setSubsidyUsed(changedEvent.getSubsidyUsed());
        }

        eventRepository.save(event);
        return event;
    }

    public void saveEvent(Event event){
        this.eventRepository.save(event);
    }
}
