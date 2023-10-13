package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.dto.response.TicketResponseDto;
import com.example.ticketingsystem.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ticket")
public class TicketingController {

    private final TicketService ticketService;

    public TicketingController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/generateTicket")
    public ResponseEntity<TicketResponseDto> generateSerialNumber() {
        return new ResponseEntity<>(ticketService.generateTicketNumber(), HttpStatus.CREATED);
    }

    @GetMapping("/currentWaitingTicket")
    public ResponseEntity<TicketResponseDto> getCurrentActiveWaitingNumber() {
        return new ResponseEntity<>(ticketService.getCurrentActiveWaitingNumber(),HttpStatus.OK);
    }

    @DeleteMapping("/lastActiveTicket")
    public ResponseEntity<String> deleteLastActiveNumber() {
        return new ResponseEntity<>(ticketService.deleteLastActiveNumber(),HttpStatus.OK);
    }

    @GetMapping("/allTickets")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(),HttpStatus.OK);
    }


}
