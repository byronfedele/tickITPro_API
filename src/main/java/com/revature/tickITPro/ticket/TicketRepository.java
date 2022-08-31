package com.revature.tickITPro.ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query(value = "FROM Ticket where ticketId= :ticketId")
    Optional<Ticket> findByTicketId(String ticketId);

    @Query(value = "FROM Ticket where userId= :userId")
    Optional<Ticket> findByUserId(String userId);
}
