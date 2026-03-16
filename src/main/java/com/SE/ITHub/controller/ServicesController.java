package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.service.ServicesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServicesController {

    private final ServicesServiceImpl servicesService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable UUID id){
        servicesService.deleteService(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateService(@RequestBody ServiceUpdateDto updateDto, @PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(servicesService.updateService(id, updateDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceResponseDto> createService(@RequestBody ServiceRequestDto reqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicesService.createService(reqDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceResponseDto>> getAllServices(){
        return ResponseEntity.status(HttpStatus.OK).body(servicesService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(servicesService.getServiceById(id));
    }
}
