package com.example.testapi.controller.resource;

import com.example.testapi.model.MonthPoints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerPoints {
    String name;
    int totalPoints;
    List<MonthPoints> pointsByMonth;

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public List<MonthPoints> getPointsByMonth() {
        return pointsByMonth;
    }
}
