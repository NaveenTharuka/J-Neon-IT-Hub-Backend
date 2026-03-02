package com.SE.ITHub.service;


import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.exception.ServiceNotFoundException;
import com.SE.ITHub.mapper.ServicesMapper;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl {

    private final ServiceRepository serviceRepository;

    private final ServicesMapper servicesMapper;

    public ServiceResponseDto createService(ServiceRequestDto reqDto) {

        Services service = servicesMapper.toEntity(reqDto);

        serviceRepository.save(service);

        return servicesMapper.toResponse(service);

    }

    public List<ServiceResponseDto> getAllServices(){
        List<Services> services = serviceRepository.findAll();
        List<ServiceResponseDto> responses = new ArrayList<>();

        for(Services service : services){
            responses.add(servicesMapper.toResponse(service));
        }

        return responses;
    }

    public ServiceResponseDto getServiceById(UUID id) {
        Services service = serviceRepository.findById(id)
                .orElseThrow(()-> new ServiceNotFoundException(id));
        return servicesMapper.toResponse(service);
    }

    public ServiceResponseDto updateService(UUID id, ServiceUpdateDto updateDto){

        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        Services saved = serviceRepository.save(servicesMapper.updateEntity(service, updateDto));

        return servicesMapper.toResponse(saved);
    }

    public void deleteService(UUID id){
        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        serviceRepository.delete(service);
    }


}
