package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findFirstByOrderBySerialNumberDesc();

    Optional<Ticket> findFirstByOrderByOrderInQueueAsc();

    Optional<Ticket> findFirstByOrderByOrderInQueueDesc();


}