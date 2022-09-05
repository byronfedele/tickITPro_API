package com.revature.tickITPro.ticket;
import com.revature.tickITPro.subject.SubjectService;
import com.revature.tickITPro.ticket.dto.Requests.NewTicketRequest;
import com.revature.tickITPro.ticket.dto.Responses.TicketResponse;
import com.revature.tickITPro.user.UserService;
import com.revature.tickITPro.subject.SubjectService;

import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
public boolean isTicketValid(Ticket newTicket){ // What is this for?
    if(newTicket==null) return false;
    if(newTicket.getTicketId()== null || newTicket.getTicketId().trim().equals("")) return false;
    if(newTicket.getDescription()== null || newTicket.getDescription().trim().equals("")) return false;
    if(newTicket.getPriority()== null || newTicket.getPriority().equals("")) return false;
    if(newTicket.getStatus()== null || newTicket.getPriority().equals("")) return false;
    if(newTicket.getDate()== null || newTicket.getDate().equals("")) return false;
    if(newTicket.getUserId()== null || newTicket.getUserId().equals("")) return false;
    if(newTicket.getProUserId()== null || newTicket.getProUserId().equals("")) return false;
    if(newTicket.getSubjectId()== null || newTicket.getSubjectId().equals("")) return false;
    return true;
}
@Transactional
public TicketResponse addTicket(NewTicketRequest ticketRequest) throws InvalidUserInputException {
    Ticket newTicket = new Ticket(ticketRequest);
    newTicket.setSubjectId(subjectService.getSubject(ticketRequest.getSubjectId()));
    newTicket.setUserId(userService.getSessionUser());
    return new TicketResponse((ticketRepository.save(newTicket)));
}
@Transactional(readOnly = true)
public List<TicketResponse> findAllTickets(){
    return ((Collection<Ticket>) ticketRepository.findAll())
                                                .stream()
                                                .map(TicketResponse::new)
                                                .collect(Collectors.toList());

}
    @Transactional(readOnly = true)
    public TicketResponse findById(String ticketId){
    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(ResourceNotFoundException::new);
    TicketResponse ticketResponse = new TicketResponse(ticket);
    return ticketResponse;
}
    @Transactional(readOnly = true)
    public List<TicketResponse> findByUserId(String userId){
        return ((Collection<Ticket>) ticketRepository.findByUserId(userId))
            .stream()
            .map(TicketResponse::new)
            .collect(Collectors.toList());
    }
//    @Transactional(readOnly = true)
//    public TicketResponse findByItProId(String itProId){
//        Ticket ticket = ticketRepository.findByUserId(itProId).orElseThrow(ResourceNotFoundException::new);
//        TicketResponse ticketResponse = new TicketResponse(ticket);
//        return ticketResponse;
//    }
//    @Transactional(readOnly = true)
//    public TicketResponse findByPriority(String priority){
//        Ticket ticket = ticketRepository.find(priority).orElseThrow(ResourceNotFoundException::new);
//        TicketResponse ticketResponse = new TicketResponse(ticket);
//        return ticketResponse;
//    }



    //findByPriority
    @Transactional
    public List<TicketResponse> findByPriority(Ticket.Priority priority){
        return ((Collection<Ticket>) ticketRepository.findByPriority(priority)).stream().map(TicketResponse::new).collect(Collectors.toList());
    }
    //findByStatus
    @Transactional
    public List<TicketResponse> findByStatus(Ticket.Status status){
        return ((Collection<Ticket>) ticketRepository.findByStatus(status)).stream().map(TicketResponse::new).collect(Collectors.toList());
    }

    //findBySubject
    //when given the iterator, you can not throw exception
    @Transactional
    public List<TicketResponse> findBySubjectId(String subjectId){
        return ((Collection<Ticket>) ticketRepository.findBySubjectId(subjectId)).stream().map(TicketResponse::new).collect(Collectors.toList());
    }
    //update
    public Ticket update(Ticket updateTicket){
    ticketRepository.save(updateTicket);
    return updateTicket;
    }

    //delete
    public boolean delete(String id){
    ticketRepository.deleteById(id);
    return true;
    }

    public boolean areEnumsValid(Ticket ticket)throws InvalidUserInputException {
        List<String> priorityEnums = Arrays.asList("DEFAULT","LOW_PRIORITY","HIGH_PRIORITY");
        List<String> statusEnums = Arrays.asList("PENDING","CONFIRMED","IN_PROGRESS","RESOLVED");
        List<Boolean> checkPriorityEnums = priorityEnums.stream().map(str -> str.equals(ticket.getPriority().toString().toUpperCase())).collect(Collectors.toList());
        if(!checkPriorityEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Tech was not a valid entry please try the following : " +
                            priorityEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available tech enums
            );
        }
        List<Boolean> checkStatusEnums = statusEnums.stream().map(str -> str.equals(ticket.getStatus().toString().toUpperCase())).collect(Collectors.toList());
        if(!checkPriorityEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Tech was not a valid entry please try the following : " +
                            priorityEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available tech enums
            );
        }
        return true;
    }
}
