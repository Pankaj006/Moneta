package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.dto.response.TicketResponseDto;
import com.example.ticketingsystem.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicketingController.class)
class TicketingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketServiceMock;

    @Test
    public void testGenerateSerialNumber() throws Exception {
        TicketResponseDto responseDto = new TicketResponseDto();
        when(ticketServiceMock.generateTicketNumber()).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/generateTicket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void getCurrentActiveWaitingTicket() throws Exception {
        TicketResponseDto responseDto = new TicketResponseDto();
        when(ticketServiceMock.getCurrentActiveWaitingNumber()).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/currentWaitingTicket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testDeleteLastActiveNumber() throws Exception {

        when(ticketServiceMock.deleteLastActiveNumber()).thenReturn("LAST ACTIVE NUMBER DELETED SUCCESSFULLY!");

        mockMvc.perform(MockMvcRequestBuilders.delete("/ticket/lastActiveTicket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("LAST ACTIVE NUMBER DELETED SUCCESSFULLY!"));
    }

    @Test
    public void testDeleteLastActiveNumberNoTicket() throws Exception {

        when(ticketServiceMock.deleteLastActiveNumber()).thenThrow(new NoSuchElementException("No Last Active Number Found!"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/ticket/lastActiveTicket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllTickets() throws Exception {

        List<TicketResponseDto> responseDtos = List.of(new TicketResponseDto(), new TicketResponseDto());
        when(ticketServiceMock.getAllTickets()).thenReturn(responseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/ticket/allTickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(responseDtos.size()));
    }
}