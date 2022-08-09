package com.javatechie.saga.payment.service;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.dto.PaymentRequestDto;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.PaymentEvent;
import com.javatechie.saga.commons.event.PaymentStatus;
import com.javatechie.saga.payment.config.LoaderDataUserBalance;
import com.javatechie.saga.payment.entity.UserTransaction;
import com.javatechie.saga.payment.repository.UserBalanceRepository;
import com.javatechie.saga.payment.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Autowired
    private LoaderDataUserBalance loaderDataUserBalance;

    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount());

        loaderDataUserBalance.afterPropertiesSet();

        return userBalanceRepository.findById(orderRequestDto.getUserId()).filter(ub -> ub.getPrice() > orderRequestDto.getAmount()).map(ub -> {
            ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
            userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount()));
            return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
        }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId()).ifPresent(ut -> {
            userTransactionRepository.delete(ut);
            userTransactionRepository.findById(ut.getUserId()).ifPresent(ub -> ub.setAmount(ub.getAmount() + ut.getAmount()));
        });
    }
}
