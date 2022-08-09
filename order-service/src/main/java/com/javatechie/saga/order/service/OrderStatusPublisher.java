package com.javatechie.saga.order.service;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@AllArgsConstructor
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSink;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSink.tryEmitNext(orderEvent);
    }
}
