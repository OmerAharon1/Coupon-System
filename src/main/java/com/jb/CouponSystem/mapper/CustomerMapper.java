package com.jb.CouponSystem.mapper;

import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.dto.RegisterReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerMapper implements Mapper<Customer, RegisterReqDto> {
    @Autowired
    private DateMapper dateMapper;

    @Override
    public Customer toCustomer(RegisterReqDto registerReqDto) {
        return Customer.builder()
                .firstName(registerReqDto.getFirstName())
                .lastName(registerReqDto.getLastName())
                .email(registerReqDto.getEmail())
                .password(registerReqDto.getPassword())
                .build();
    }

    @Override
    public RegisterReqDto toRegisterDto(Customer customer) {
        return RegisterReqDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();
    }

    @Override
    public List<Customer> toCustomerList(List<RegisterReqDto> registerReqDtos) {
        return null;
    }

    @Override
    public List<RegisterReqDto> toRegisterDtoList(List<Customer> customers) {
        return null;
    }
}
