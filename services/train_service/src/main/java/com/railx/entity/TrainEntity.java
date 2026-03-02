package com.railx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class TrainEntity {

    @Id
    public int trainNumber;
    public String trainName;
    public String source;
    public String destination;
    public String departureTime;
}
