package com.june.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageDTO {
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private int total;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
