package com.bluescript.bank.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString

public class WsWorkDate {
    private String wsWorkDateCcyy;
    private String dash1;
    private String wsWorkDateMm;
    private String dash2;
    private String wsWorkDateDd;

}