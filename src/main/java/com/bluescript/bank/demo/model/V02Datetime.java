package com.bluescript.bank.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString

public class V02Datetime {
    private String v02ProcessDate;
    private String v02ProcessTime;
    private String v02Filler1;

}