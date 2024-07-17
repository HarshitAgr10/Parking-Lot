package controllers;

import dtos.IssueTicketRequestDto;
import dtos.IssueTicketResponseDto;
import dtos.ResponseStatus;
import models.Ticket;
import services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public IssueTicketResponseDto issueTicket(IssueTicketRequestDto requestDto) {
        IssueTicketResponseDto responseDto = new IssueTicketResponseDto();
        Ticket ticket;

        try {
            ticket = ticketService.issueTicket(
                    requestDto.getVehicleType(),
                    requestDto.getVehicleNumber(),
                    requestDto.getVehicleOwnerName(),
                    requestDto.getGateId()
            );
        } catch (Exception ex) {
            responseDto.setFailureReason(ex.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);

            return responseDto;
        }

        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        responseDto.setTicket(ticket);

        return responseDto;
    }
}
