package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.repository.TicketRepository;
import com.example.ticketingsystem.service.TicketService;
import com.example.ticketingsystem.dto.response.TicketResponseDto;
import com.example.ticketingsystem.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Value("${ticket.start.number}")
    private int ticketStartNum;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketResponseDto generateTicketNumber() {

        int nextSerialNumber = ticketRepository.findFirstByOrderBySerialNumberDesc()
                .map(maxSerialNumber -> maxSerialNumber.getSerialNumber() + 1)
                .orElse(ticketStartNum);
        int orderInQueue = (int) (ticketRepository.count() + 1);
        Ticket ticket = new Ticket();
        ticket.setSerialNumber(nextSerialNumber);
        ticket.setOrderInQueue(orderInQueue);
        return TicketMapper.INSTANCE.toDto(ticketRepository.save(ticket));

    }

    @Override
    public TicketResponseDto getCurrentActiveWaitingNumber() {

        return ticketRepository.findFirstByOrderByOrderInQueueAsc()
                .map(ticket -> TicketMapper.INSTANCE.toDto(ticket))
                .orElseThrow(() -> new NoSuchElementException("No Current Active Number Found!"));

    }

    @Override
    public String deleteLastActiveNumber() {

        return ticketRepository.findFirstByOrderByOrderInQueueDesc()
                .map(ticket -> {
                    ticketRepository.delete(ticket);
                    return "LAST ACTIVE NUMBER DELETED SUCCESSFULLY!";
                })
                .orElseThrow(() -> new NoSuchElementException("No Last Active Number Found!"));

    }

    @Override
    public List<TicketResponseDto> getAllTickets() {

        return TicketMapper.INSTANCE.toDtoList(ticketRepository.findAll());
    }
}
