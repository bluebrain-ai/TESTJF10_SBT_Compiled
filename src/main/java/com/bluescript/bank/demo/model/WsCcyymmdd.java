package com.bluescript.bank.demo.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class WsCcyymmdd {
    private String wsCcyyDate;
    private String wsMmDate;
    private String wsDdDate;

}