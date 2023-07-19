package com.bluescript.bank.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.ToString;
import java.util.*;

import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString

public class HostVariablesPm {
    private String hvPmCustomerSupp;
    private String hvPmLocation;
    private String hvPmItemid;
    private int hvPmLotQuantity;
    private String hvPmKanban;
    private String hvPmEmployee;
    private String hvPmOrderMethod;
    private int hvCountLocation;
    private int hvCountStoradd;
    private int hvPartDescriptionCnt;
    private int hvSuppPlantCnt;

}