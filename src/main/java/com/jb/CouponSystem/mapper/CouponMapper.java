package com.jb.CouponSystem.mapper;


import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.repos.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponMapper implements Mapper<Coupon, CouponDto> {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private DateMapper dateMapper;


    @Override
    public Coupon toBean(CouponDto couponDto) {
        return Coupon.builder()
                .title(couponDto.getTitle())
                .category(Category.valueOf(couponDto.getCategory()))
                .company(companyRepository.getById(couponDto.getCompanyId()))
                .amount(couponDto.getAmount())
                .price(couponDto.getPrice())
                .image(couponDto.getImage())
                .description(couponDto.getDescription())
                .endDate(dateMapper.toTimestamp(couponDto.getEndDate()))
                .startDate(dateMapper.toTimestamp(couponDto.getStartDate()))
                .id(couponDto.getId())
                .build();
    }

    @Override
    public CouponDto toDto(Coupon coupon) {
        return CouponDto.builder()
                .companyId(coupon.getCompany().getId())
                .amount(coupon.getAmount())
                .category(coupon.getCategory().toString())
                .id(coupon.getId())
                .description(coupon.getDescription())
                .endDate(dateMapper.toLocalDateTime(coupon.getEndDate()))
                .startDate(dateMapper.toLocalDateTime(coupon.getStartDate()))
                .image(coupon.getImage())
                .price(coupon.getPrice())
                .title(coupon.getTitle())
                .build();

    }

    @Override
    public List<Coupon> toBeanList(List<CouponDto> couponDtos) {
        return couponDtos.stream().map(this::toBean).collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> toDtoList(List<Coupon> coupons) {
        return coupons.stream().map(this::toDto).collect(Collectors.toList());
    }
}
