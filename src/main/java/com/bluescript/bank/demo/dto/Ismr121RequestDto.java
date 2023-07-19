package com.bluescript.bank.demo.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Ismr121RequestDto {

    private String wsRoutine;
    private String wsInputDate;
    private int wsParm2Num;
    private String wsParm3;
    private String wsParm4;
    private String wsParm5;
}