package com.javatechie.saga.order.service;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.event.OrderStatus;
import com.javatechie.saga.order.entity.PurchaseOrder;
import com.javatechie.saga.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
        PurchaseOrder purchaseOrder = orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }

    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public PurchaseOrder convertDtoToEntity(OrderRequestDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(dto.getAmount());
        return purchaseOrder;
    }
}
