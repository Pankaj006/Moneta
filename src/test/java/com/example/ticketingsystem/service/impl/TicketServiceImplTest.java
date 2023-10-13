package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.dto.response.TicketResponseDto;
import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.mapper.TicketMapper;
import com.example.ticketingsystem.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepositoryMock;

    @InjectMocks
    private TicketServiceImpl ticketServiceMock;


    @Test
    void generateTicketNumber() {

        int nextSerialNumber = 1001;
        when(ticketRepositoryMock.findFirstByOrderBySerialNumberDesc()).thenReturn(Optional.empty());
        when(ticketRepositoryMock.count()).thenReturn(0L);
        when(ticketRepositoryMock.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket ticket = invocation.getArgument(0);
            ticket.setSerialNumber(nextSerialNumber);
            return ticket;
        });

        TicketResponseDto result = ticketServiceMock.generateTicketNumber();
        assertEquals(nextSerialNumber, result.getSerialNumber());
    }

    @Test
    void getCurrentActiveWaitingNumber() {

        Ticket ticket = new Ticket();
        when(ticketRepositoryMock.findFirstByOrderByOrderInQueueAsc()).thenReturn(Optional.of(ticket));
        TicketResponseDto result = ticketServiceMock.getCurrentActiveWaitingNumber();

        assertNotNull(result);
    }

    @Test
    public void testDeleteLastActiveNumber_Success() {

        Ticket lastActiveTicket = new Ticket();
        when(ticketRepositoryMock.findFirstByOrderByOrderInQueueDesc()).thenReturn(Optional.of(lastActiveTicket));
        String result = ticketServiceMock.deleteLastActiveNumber();

        assertEquals("LAST ACTIVE NUMBER DELETED SUCCESSFULLY!", result);
        verify(ticketRepositoryMock, times(1)).delete(lastActiveTicket);
    }

    @Test
    public void testDeleteLastActiveNumber_NoTicket() {

        when(ticketRepositoryMock.findFirstByOrderByOrderInQueueDesc()).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> ticketServiceMock.deleteLastActiveNumber());
        verify(ticketRepositoryMock, never()).delete(any());
    }

    @Test
    void getAllTickets() {

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        List<Ticket> ticketList = List.of(ticket1, ticket2);

        when(ticketRepositoryMock.findAll()).thenReturn(ticketList);

        List<TicketResponseDto> expectedResponse = ticketList.stream()
                .map(TicketMapper.INSTANCE::toDto)
                .collect(Collectors.toList());

        List<TicketResponseDto> result = ticketServiceMock.getAllTickets();

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());
    }
}