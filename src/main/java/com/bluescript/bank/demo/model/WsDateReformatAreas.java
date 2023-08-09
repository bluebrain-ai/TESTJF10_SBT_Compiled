package com.bluescript.bank.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class WsDateReformatAreas {
    @Autowired
    private WsWorkDate wsWorkDate;
    @Autowired
    private WsCcyymmdd wsCcyymmdd;
    private String wsCurrentDate = "0000-00-00";
    private String wsTodayDate;
    private String wsTodayTime;
    private String wsStartDate = "0000-00-00";

}