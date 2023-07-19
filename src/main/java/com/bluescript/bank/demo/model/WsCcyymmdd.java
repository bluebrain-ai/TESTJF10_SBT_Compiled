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

public class WsCcyymmdd {
    private String wsCcyyDate;
    private String wsMmDate;
    private String wsDdDate;

}