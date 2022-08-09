package com.javatechie.saga.commons.event;

import com.javatechie.saga.commons.dto.PaymentRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event {

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDto paymentRequestDto;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public PaymentRequestDto getPaymentRequestDto() {
        return paymentRequestDto;
    }

    public void setPaymentRequestDto(PaymentRequestDto paymentRequestDto) {
        this.paymentRequestDto = paymentRequestDto;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
