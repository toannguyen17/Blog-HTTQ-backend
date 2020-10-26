package com.httq.dto.report;

public class ReportDTO {
    private int numberOfPostLast7Days;
    private int numberOfPostLast30Days;

    public ReportDTO() {
    }

    public int getNumberOfPostLast7Days() {
        return numberOfPostLast7Days;
    }

    public void setNumberOfPostLast7Days(int numberOfPostLast7Days) {
        this.numberOfPostLast7Days = numberOfPostLast7Days;
    }

    public int getNumberOfPostLast30Days() {
        return numberOfPostLast30Days;
    }

    public void setNumberOfPostLast30Days(int numberOfPostLast30Days) {
        this.numberOfPostLast30Days = numberOfPostLast30Days;
    }
}
