package com.SE.ITHub.controller;

import com.SE.ITHub.dto.PlansUpdateReqDto;
import com.SE.ITHub.dto.ServicePlanReqDto;
import com.SE.ITHub.dto.ServicePlanResponseDto;
import com.SE.ITHub.service.ServicePlansServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/plans")
public class ServicePlansController {

    private final ServicePlansServiceImpl servicePlansService;

    @GetMapping("/all")
    public ResponseEntity<List<ServicePlanResponseDto>> getAllPlans(){
        return ResponseEntity.status(HttpStatus.OK).body(servicePlansService.getAllPlans());
    }

    @GetMapping("/serviceId/{id}")
    public ResponseEntity<List<ServicePlanResponseDto>> getServicePlansById(@PathVariable UUID id){
        List<ServicePlanResponseDto> responses =  servicePlansService.getServicePlanByServiceId(id);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


    @PostMapping
    public ResponseEntity<ServicePlanResponseDto> createPlan(@RequestBody ServicePlanReqDto reqDto){
        return ResponseEntity.status(HttpStatus.OK).body(servicePlansService.createServicePlan(reqDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable UUID id){
        servicePlansService.deleteServicePlan(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/id")
    public ResponseEntity<?> updatePlan(@RequestBody PlansUpdateReqDto updateDto){
        servicePlansService.updatePlan(updateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
