package com.bluescript.bank.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.ToString;
import java.util.*;

import org.springframework.stereotype.Component;
import com.bluescript.bank.demo.model.WsWorkDate;
import com.bluescript.bank.demo.model.WsCcyymmdd;

@Getter
@Setter
@Component
@ToString

public class WsDateReformatAreas {
    private WsWorkDate wsWorkDate;
    private WsCcyymmdd wsCcyymmdd;
    private String wsCurrentDate = "0000-00-00";
    private String wsTodayDate;
    private String wsTodayTime;
    private String wsStartDate = "0000-00-00";

}