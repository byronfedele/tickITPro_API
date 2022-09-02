package com.revature.tickITPro.ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query(value = "FROM Ticket where ticket_id= :ticketId")
    Optional<Ticket> findByTicketId(String ticketId);

    @Query(value = "FROM Ticket where req_user_id= :userId ORDER BY submission_date")
    Iterable<Ticket> findByUserId(String userId);

    @Query(value = "FROM Ticket where pro_user_id= :proUserId ORDER BY submission_date")
    Iterable<Ticket> findByITProId(String proUserId);

    @Query(value = "FROM Ticket where subject_id= :subjectId ORDER BY submission_date")
    Iterable<Ticket> findBySubjectId(String subjectId);

    @Query(value = "FROM Ticket where priority= :priority ORDER BY submission_date")
    Iterable<Ticket> findByPriority(Ticket.Priority priority);

    @Query(value = "FROM Ticket where status= :status ORDER BY submission_date")
    Iterable<Ticket> findByStatus(Ticket.Status status);
}
