package com.example.testapi.controller.resource;

import com.example.testapi.model.MonthPoints;
import lombok.AllArgsConstructor;
import jdk.jfr.DataAmount;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<MonthPoints> getPointsByMonth() {
        return pointsByMonth;
    }

    public void setPointsByMonth(List<MonthPoints> pointsByMonth) {
        this.pointsByMonth = pointsByMonth;
    }

}
