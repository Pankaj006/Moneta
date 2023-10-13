package com.example.ticketingsystem.mapper;

import com.example.ticketingsystem.entity.Ticket;
import com.example.ticketingsystem.dto.response.TicketResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketResponseDto toDto(Ticket ticket);

    List<TicketResponseDto> toDtoList(List<Ticket> ticket);


}