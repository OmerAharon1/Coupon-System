package com.jb.CouponSystem.mapper;

import java.util.List;

public interface Mapper<DAO, DTO> {

    DAO toCustomer(DTO dto);

    DTO toRegisterDto(DAO dao);

    List<DAO> toCustomerList(List<DTO> dtos);

    List<DTO> toRegisterDtoList(List<DAO> daos);
}

