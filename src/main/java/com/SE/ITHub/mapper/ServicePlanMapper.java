package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.PlansUpdateReqDto;
import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.exception.PlanNotFoundException;
import com.SE.ITHub.exception.ServiceNotFoundException;
import com.SE.ITHub.model.ServicePlans;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.ServicePlansRepository;
import com.SE.ITHub.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ServicePlanMapper {

    private final ServiceRepository serviceRepository;
    private final ServicePlansRepository servicePlansRepository;

    public ServicePlans toEntity(ServicePlanReqDto reqDto){

        Services service = serviceRepository.findById(reqDto.getServiceId())
                .orElseThrow(() -> new ServiceNotFoundException(reqDto.getServiceId()));

        if(reqDto.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        return ServicePlans.builder()
                .planName(reqDto.getPlanName())
                .service(service)
                .priceType(reqDto.getPriceType())
                .price(reqDto.getPrice())
                .currency(reqDto.getCurrency())
                .billingPeriod(reqDto.getBillingPeriod())
                .description(reqDto.getDescription())
                .features(reqDto.getFeatures())
                .isFeatured(reqDto.isFeatured())
                .isActive(reqDto.isActive())
                .sortOrder(reqDto.getSortOrder())
                .build();

    }

    public ServicePlanResponseDto toResponse(ServicePlans servicePlan){

        if (servicePlan.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        return ServicePlanResponseDto.builder()
                .serviceType(servicePlan.getService().getTitle())
                .planName(servicePlan.getPlanName())
                .price(servicePlan.getPrice())
                .priceType(servicePlan.getPriceType())
                .description(servicePlan.getDescription())
                .features(servicePlan.getFeatures())
                .isFeatured(servicePlan.isFeatured())
                .isActive(servicePlan.isActive())
                .build();
    }

    public ServicePlans updateEntity(PlansUpdateReqDto updateDto){

        ServicePlans plan = servicePlansRepository.findById(updateDto.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(updateDto.getPlanId()));

        Services service = serviceRepository.findById(updateDto.getServiceId())
                .orElseThrow(() -> new ServiceNotFoundException(updateDto.getServiceId()));

        if (updateDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        plan.setService(service);
        plan.setPlanName(updateDto.getPlanName());
        plan.setPrice(updateDto.getPrice());
        plan.setPriceType(updateDto.getPriceType());
        plan.setCurrency(updateDto.getCurrency());
        plan.setBillingPeriod(updateDto.getBillingPeriod());
        plan.setDescription(updateDto.getDescription());
        plan.setFeatures(updateDto.getFeatures());
        plan.setFeatured(updateDto.isFeatured());
        plan.setActive(updateDto.isActive());
        plan.setSortOrder(updateDto.getSortOrder());

        return plan;
    }

}