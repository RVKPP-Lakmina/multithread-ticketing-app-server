package com.example.ticketing.model.interfaces;

import com.example.ticketing.enumeration.TicketStatusEnum;

public interface ITicketPool {

    TicketStatusEnum addTickets(int count);

    TicketStatusEnum purchaseTickets(int count);

    int getTotalTickets();

    int getPoolTickets();
}
