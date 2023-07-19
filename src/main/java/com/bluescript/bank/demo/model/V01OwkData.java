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

public class V01OwkData {
    private String v01OwkPartnerCode;
    private String v01OwkPartnerName;
    private String v01OwkPntCode;
    private String v01OwkPntShortName;
    private String v01OwkPntName;
    private String v01OwkRespLpFlag;
    private String v01OwkRteCode;
    private String v01OwkRteDepartDt;
    private String v01OwkRteArriveDt;

}