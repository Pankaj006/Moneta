package com.example.ticketingsystem.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponseDto {


    Long id;

    int serialNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp createdAt;

    int orderInQueue;
}
