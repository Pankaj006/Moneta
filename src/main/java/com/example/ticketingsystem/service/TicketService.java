package com.example.ticketingsystem.service;

import com.example.ticketingsystem.dto.response.TicketResponseDto;

import java.util.List;

public interface TicketService {

     TicketResponseDto generateTicketNumber();
     TicketResponseDto getCurrentActiveWaitingNumber() ;
     String deleteLastActiveNumber() ;
     List<TicketResponseDto> getAllTickets();

}
