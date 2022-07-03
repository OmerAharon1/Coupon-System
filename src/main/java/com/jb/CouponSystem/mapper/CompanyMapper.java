package com.jb.CouponSystem.mapper;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.dto.CompanyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper implements Mapper<Company, CompanyDto> {

    @Override
    public Company toBean(CompanyDto companyDto) {
        return Company.builder()
                .id(companyDto.getCompanyId())
                .email(companyDto.getEmail())
                .password(companyDto.getPassword())
                .name(companyDto.getName())
                .build();
    }

    @Override
    public CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .companyId(company.getId())
                .email(company.getEmail())
                .coupons(company.getCoupons())
                .password(company.getPassword())
                .name(company.getName())
                .build();
    }

    @Override
    public List<Company> toBeanList(List<CompanyDto> companyDtos) {
        return companyDtos.stream().map(this::toBean).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> toDtoList(List<Company> companies) {
        return companies.stream().map(this::toDto).collect(Collectors.toList());
    }
}
