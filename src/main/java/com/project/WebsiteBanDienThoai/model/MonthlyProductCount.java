package com.project.WebsiteBanDienThoai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthlyProductCount {
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @JsonProperty("month")
    private int month;

    @JsonProperty("year")
    private int year;

    @JsonProperty("count")
    private long count;

    public MonthlyProductCount(int month, int year, long count) {
        this.month = month;
        this.year = year;
        this.count = count;
    }

    // Getters and setters
}
