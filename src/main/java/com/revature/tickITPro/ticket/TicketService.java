package com.revature.tickITPro.ticket;
import com.revature.tickITPro.subject.SubjectService;
import com.revature.tickITPro.ticket.dto.Requests.EditTicketRequest;
import com.revature.tickITPro.ticket.dto.Requests.NewTicketRequest;
import com.revature.tickITPro.ticket.dto.Responses.TicketResponse;
import com.revature.tickITPro.user.UserService;

import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
public class TicketService {
    private final UserService userService;
    private final SubjectService subjectService;
    private final TicketRepository ticketRepository;
    @Autowired
    public TicketService(UserService userService, SubjectService subjectService, TicketRepository ticketRepository){
        this.userService=userService;
        this.subjectService=subjectService;
        this.ticketRepository=ticketRepository;
    }
    public boolean isTicketValid(Ticket testTicket){ // What is this for?
        if(testTicket == null) throw new InvalidUserInputException("Inputted ticket was null");
        if(testTicket.getTicketId()== null || testTicket.getTicketId().trim().equals("")) throw new InvalidUserInputException("Ticket ID was empty or null");
        if(testTicket.getDescription()== null || testTicket.getDescription().trim().equals("")) throw new InvalidUserInputException("Description was empty or null");
        if(testTicket.getDate()== null || testTicket.getDate().equals("")) throw new InvalidUserInputException("Ticket submission Date was empty or null");
        if(testTicket.getUserId()== null || testTicket.getUserId().equals("")) throw new InvalidUserInputException("Requested User ID was empty or null");
        if(testTicket.getSubjectId()== null || testTicket.getSubjectId().equals("")) throw new InvalidUserInputException("Subject ID was empty or null");
        areEnumsValid(testTicket);
        return true;
    }
    @Transactional
    public TicketResponse addTicket(NewTicketRequest ticketRequest) throws InvalidUserInputException {
        Ticket newTicket = new Ticket(ticketRequest);
        newTicket.setSubjectId(subjectService.getSubject(ticketRequest.getSubjectId()));
        newTicket.setUserId(userService.getSessionUser());
        isTicketValid(newTicket);
        return new TicketResponse((ticketRepository.save(newTicket)));
    }
    @Transactional(readOnly = true)
    public List<TicketResponse> findAllTickets(){
        return ((Collection<Ticket>) ticketRepository.findAll())
                                                .stream()
                                                .map(TicketResponse::new)
                                                .collect(Collectors.toList());

    }
    // find a ticket by their id
    @Transactional(readOnly = true)
    public TicketResponse findById(String ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(ResourceNotFoundException::new);
        return new TicketResponse(ticket);
    }
    // find all tickets created by a certain user
    @Transactional(readOnly = true)
    public List<TicketResponse> findByCreatorId(String userId){
        return ((Collection<Ticket>) ticketRepository.findByUserId(userId))
                .stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    // find all tickets confirmed by a certain ITPro
    @Transactional(readOnly = true)
    public List<TicketResponse> findByItProId(String itProId){
        return ((Collection<Ticket>) ticketRepository.findByITProId(itProId))
                .stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    // find all tickets by their given priority (LOW_PRIORITY, DEFAULT, HIGH_PRIORITY)
    @Transactional(readOnly = true)
    public List<TicketResponse> findByPriority(String priority){
        arePriorityEnumsValid(priority);
        return ((Collection<Ticket>) ticketRepository.findByPriority(Ticket.Priority.valueOf(priority)))
                .stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }
    // find all tickets by their given status (PENDING, CONFIRMED, RESOLVED)
    @Transactional(readOnly = true)
    public List<TicketResponse> findByStatus(String status){
        areStatusEnumsValid(status);
        return ((Collection<Ticket>) ticketRepository.findByStatus(Ticket.Status.valueOf(status)))
                .stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    // find all tickets by their subject-matter
    // when given the iterator, you can not throw exception
    @Transactional(readOnly = true)
    public List<TicketResponse> findBySubjectId(String subjectId){
        return ((Collection<Ticket>) ticketRepository.findBySubjectId(subjectId))
                .stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }
    //update
    @Transactional
    public TicketResponse update(EditTicketRequest editTicket) throws RuntimeException {

        Ticket updateTicket = ticketRepository.findById(editTicket.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editTicket.getDescription())) updateTicket.setDescription(editTicket.getDescription());
        if (notNullOrEmpty.test(editTicket.getSubjectId())) updateTicket.setSubjectId(subjectService.getSubject(editTicket.getSubjectId()));
        if (notNullOrEmpty.test(editTicket.getProUserId())) updateTicket.setProUserId(userService.getUser(editTicket.getProUserId()));
        if (notNullOrEmpty.test(editTicket.getPriority())) {
            arePriorityEnumsValid(editTicket.getPriority());
            updateTicket.setPriority(Ticket.Priority.valueOf(editTicket.getPriority()));
        }
        if (notNullOrEmpty.test(editTicket.getStatus())) {
            areStatusEnumsValid(editTicket.getStatus());
            updateTicket.setStatus(Ticket.Status.valueOf(editTicket.getStatus()));
        }
        return new TicketResponse(ticketRepository.save(updateTicket));
    }

    // delete
    public boolean remove(String ticketId){
        ticketRepository.deleteById(ticketId);
        return true;
    }

    public boolean areEnumsValid(Ticket ticket) throws InvalidUserInputException {
        List<String> priorityEnums = Arrays.asList("DEFAULT","LOW_PRIORITY","HIGH_PRIORITY");
        List<Boolean> checkPriorityEnums = priorityEnums.stream()
                .map(str -> str.equals(ticket.getPriority().toString().toUpperCase()))
                .collect(Collectors.toList());
        if(!checkPriorityEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Priority was not a valid entry please try the following : " +
                            priorityEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available priority enums
            );
        }
        List<String> statusEnums = Arrays.asList("PENDING","CONFIRMED","RESOLVED");
        List<Boolean> checkStatusEnums = statusEnums.stream()
                .map(str -> str.equals(ticket.getStatus().toString().toUpperCase()))
                .collect(Collectors.toList());
        if(!checkStatusEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Status was not a valid entry please try the following : " +
                            statusEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available status enums
            );
        }
        return true;
    }

    public boolean arePriorityEnumsValid(String ticketPriority) throws InvalidUserInputException {
        List<String> priorityEnums = Arrays.asList("DEFAULT","LOW_PRIORITY","HIGH_PRIORITY");
        List<Boolean> checkPriorityEnums = priorityEnums.stream()
                .map(str -> str.equals(ticketPriority))
                .collect(Collectors.toList());
        if(!checkPriorityEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Priority was not a valid entry please try the following : " +
                            priorityEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available priority enums
            );
        }
        return true;
    }

    public boolean areStatusEnumsValid(String ticketStatus) throws InvalidUserInputException {
        List<String> statusEnums = Arrays.asList("PENDING","CONFIRMED","RESOLVED");
        List<Boolean> checkStatusEnums = statusEnums.stream()
                .map(str -> str.equals(ticketStatus))
                .collect(Collectors.toList());
        if(!checkStatusEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Status was not a valid entry please try the following : " +
                            statusEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available status enums
            );
        }
        return true;
    }
}
