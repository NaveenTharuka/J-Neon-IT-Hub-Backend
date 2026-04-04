package com.SE.ITHub.service;

import com.SE.ITHub.dto.PlansUpdateReqDto;
import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.exception.ServiceNotFoundException;
import com.SE.ITHub.mapper.ServicePlanMapper;
import com.SE.ITHub.mapper.ServicesMapper;
import com.SE.ITHub.model.ServicePlans;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.ServicePlansRepository;
import com.SE.ITHub.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicePlansServiceImpl {

    private final ServicePlansRepository servicePlansRepository;
    private final ServiceRepository serviceRepository;

    private final ServicePlanMapper servicePlanMapper;
    private final ServicesMapper servicesMapper;

    public ServicePlanResponseDto createServicePlan(ServicePlanReqDto reqDto){
        ServicePlans servicePlan = servicePlanMapper.toEntity(reqDto);

        return servicePlanMapper.toResponse(servicePlansRepository.save(servicePlan));
    }

    public void deleteServicePlan(UUID id){
        servicePlansRepository.deleteById(id);
    }

    public List<ServicePlanResponseDto> getAllPlans(){
        List<ServicePlanResponseDto> responses = new ArrayList<>();
        List<ServicePlans> plans = servicePlansRepository.findAll();

        for (ServicePlans servicePlans : plans){
            responses.add(servicePlanMapper.toResponse(servicePlans));
        }

        return responses;

    }

    public ServicePlanResponseDto updatePlan(PlansUpdateReqDto updateDto){
        ServicePlans plan = servicePlanMapper.updateEntity(updateDto);

        servicePlansRepository.save(plan);

        return servicePlanMapper.toResponse(plan);
    }

    public List<ServicePlanResponseDto> getServicePlanByServiceId(UUID id){
        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        List<ServicePlans> plans = service.getPlans();
        List<ServicePlanResponseDto> responses = new ArrayList<>();

        for (ServicePlans servicePlans : plans){
            responses.add(servicePlanMapper.toResponse(servicePlans));
        }

        return responses;
    }

}
