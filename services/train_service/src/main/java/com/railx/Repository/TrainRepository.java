package com.railx.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.railx.entity.TrainEntity;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Integer> {

    
} 