package com.jb.CouponSystem.mapper;

import java.util.List;

public interface Mapper<DAO, DTO> {

    DAO toBean(DTO dto);

    DTO toDto(DAO dao);

    List<DAO> toBeanList(List<DTO> dtos);

    List<DTO> toDtoList(List<DAO> daos);
}

