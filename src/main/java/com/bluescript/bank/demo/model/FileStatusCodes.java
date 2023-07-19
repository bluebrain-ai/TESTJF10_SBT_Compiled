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

public class FileStatusCodes {
    private String ismr121 = "ISMR121";
    private String wsRoutine;
    private String wsInputDate;
    private int wsParm2Num;
    private String wsParm3;
    private String wsParm4;
    private String wsParm5;

}