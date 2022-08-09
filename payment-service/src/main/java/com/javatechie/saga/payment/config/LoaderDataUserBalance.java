package com.javatechie.saga.payment.config;

import com.javatechie.saga.payment.entity.UserBalance;
import com.javatechie.saga.payment.repository.UserBalanceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LoaderDataUserBalance implements InitializingBean {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Override
    public void afterPropertiesSet() {
        userBalanceRepository.saveAll(Stream.of(
                new UserBalance(101, 500),
                new UserBalance(102, 3000)
        ).collect(Collectors.toList()));
    }
}
