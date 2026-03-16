package com.SE.ITHub.repository;

import com.SE.ITHub.model.ServicePlans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicePlansRepository extends JpaRepository<ServicePlans, UUID> {
}
