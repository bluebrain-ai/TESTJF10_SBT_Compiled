package com.bluescript.bank.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.bluescript.bank.demo.dto.IC1ParkanbDto;
import com.bluescript.bank.demo.dto.IC2ParkanbDto;
import com.bluescript.bank.demo.dto.ICsiplntDto;
import com.bluescript.bank.demo.dto.IPardescDto;
import com.bluescript.bank.demo.dto.Ismr121RequestDto;
import com.bluescript.bank.demo.dto.Ismr121ResponseDto;
import com.bluescript.bank.demo.model.FileStatusCodes;
import com.bluescript.bank.demo.model.HostVariablesPm;
import com.bluescript.bank.demo.model.V01OwkData;
import com.bluescript.bank.demo.model.V01Rec;
import com.bluescript.bank.demo.model.V02Datetime;
import com.bluescript.bank.demo.model.WsCcyymmdd;
import com.bluescript.bank.demo.model.WsDateReformatAreas;
import com.bluescript.bank.demo.model.WsWorkDate;
import com.bluescript.bank.demo.repository.ICsiplntRepo;
import com.bluescript.bank.demo.repository.IPardescRepo;
import com.bluescript.bank.demo.repository.IParkanbRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Getter
@Setter
@RequiredArgsConstructor
@Service
@Log4j2
public class Testjf10Service implements ITestjf10Service {
    @Autowired
    private FileStatusCodes fileStatusCodes;
    @Autowired
    private WsDateReformatAreas wsDateReformatAreas;
    @Autowired
    private WsWorkDate wsWorkDate;
    @Autowired
    private WsCcyymmdd wsCcyymmdd;
    @Autowired
    private HostVariablesPm hostVariablesPm;
    @Autowired
    private V01Rec v01Rec;
    @Autowired
    private V01OwkData v01OwkData;
    @Autowired
    private V02Datetime v02Datetime;
    @Autowired
    private IParkanbRepo parkanbRepo;
    @Autowired
    private ICsiplntRepo csiplntRepo;
    @Autowired
    private IPardescRepo pardescRepo;
    @Autowired
    private StreamBridge streamBridge;
    private String recOut01;
    private boolean wsParkanbSwitch = true;
    private boolean suppNotFound = false;
    private boolean ordMtdNotFound = false;
    private int wsOut01Counter;
    private long wsOut01DisplayCount;
    private String t1SuppCode;
    private Set<String> validSuppCode = Set.of("00001", "00002", "00003", "00004", "00005");
    private String t2OrderMethod;
    private Set<String> validOrderMethod = Set.of("A", "B", "C", "D", "E");
    private String hvSmSuppPlantName;
    private String hvSmPartDescription;
    private String hvPkLocation;
    private String hvPkStoreAddrPrim;
    private String hvPkPackingStyle;
    private String wsPartNumber;
    private String wsCustomerSupp;
    private String wsDock;
    private String wsKanban;
    private int wsQtyPerBox;
    private String v03ErrorMessage;
    private String v04OwkBusEnt;
    private String v04OwkRelType;
    private String v04OwkDateTime;
    @Autowired
    private WebClient webClient;
    @Value("${ismr121.url}")
    private String ismr121Api;

    @Transactional
    public String mainModule() {
        log.debug("Method mainModulestarted..");
        log.info("owkb010 start");
        initialization(v02Datetime);// item.getV02Datetime());
        mainline(v02Datetime);// item.getV02Datetime());
        closeFiles();
        log.debug("Method mainModule completed..");
        return "Success";
    }

    public void initialization(V02Datetime v02Datetime) {
        log.debug("Method initializationstarted..");
        wsDateReformatAreas.setWsTodayDate(LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")));

        wsDateReformatAreas.getWsCcyymmdd().setWsCcyyDate(wsDateReformatAreas.getWsTodayDate().substring(0, 4));
        log.warn(wsDateReformatAreas.getWsCcyymmdd().getWsCcyyDate());
        wsDateReformatAreas.getWsCcyymmdd().setWsMmDate(wsDateReformatAreas.getWsTodayDate().substring(4, 6));
        log.warn(wsDateReformatAreas.getWsCcyymmdd().getWsMmDate());
        wsDateReformatAreas.getWsCcyymmdd().setWsDdDate(wsDateReformatAreas.getWsTodayDate().substring(6, 8));
        log.warn(wsDateReformatAreas.getWsCcyymmdd().getWsDdDate());

        v02Datetime.setV02ProcessDate(wsDateReformatAreas.getWsTodayDate());
        wsDateReformatAreas.setWsTodayTime(LocalTime.now().toString());

        List<String> v02DatetimeList = Arrays.asList(v02Datetime.getV02ProcessDate(), v02Datetime.getV02ProcessTime(),
                v02Datetime.getV02Filler1());

        v01Rec.setV01OwkOrdRelDt(v02DatetimeList.stream().collect(Collectors.joining("")));
        /*
         * List<String> v02DatetimeList = Arrays.asList(v02Datetime.getV02ProcessDate(),
         * v02Datetime.getV02ProcessTime(), v02Datetime.getV02Filler1());
         */
        v04OwkDateTime = v02DatetimeList.stream().collect(Collectors.joining(""));

        v02Datetime.setV02ProcessTime(wsDateReformatAreas.getWsTodayTime());
        wsDateReformatAreas.getWsWorkDate().setWsWorkDateCcyy(wsDateReformatAreas.getWsCcyymmdd().getWsCcyyDate());
        wsDateReformatAreas.getWsWorkDate().setWsWorkDateMm(wsDateReformatAreas.getWsCcyymmdd().getWsMmDate());
        wsDateReformatAreas.getWsWorkDate().setWsWorkDateDd(wsDateReformatAreas.getWsCcyymmdd().getWsDdDate());
        wsDateReformatAreas.getWsWorkDate().setDash1("-");
        wsDateReformatAreas.getWsWorkDate().setDash2("-");
        List<String> wsWorkDateList = Arrays.asList(
                String.valueOf(wsDateReformatAreas.getWsWorkDate().getWsWorkDateCcyy()),
                String.valueOf(wsDateReformatAreas.getWsWorkDate().getDash1()),
                String.valueOf(wsDateReformatAreas.getWsWorkDate().getWsWorkDateMm()),
                String.valueOf(wsDateReformatAreas.getWsWorkDate().getDash2()),
                String.valueOf(wsDateReformatAreas.getWsWorkDate().getWsWorkDateDd()));
        wsDateReformatAreas.setWsCurrentDate(wsWorkDateList.stream().collect(Collectors.joining("")));
        /*
         * fileStatusCodes.setWsRoutine("BUMPWORK");
         * fileStatusCodes.setWsInputDate(wsDateReformatAreas.getWsCurrentDate()); fileStatusCodes.setWsParm2Num(20);
         * fileStatusCodes.setWsParm3("+"); fileStatusCodes.setWsParm4(""); fileStatusCodes.setWsParm5("");
         */

        Ismr121RequestDto ismr121RequestDto = new Ismr121RequestDto();

        ismr121RequestDto.setWsRoutine("BUMPWORK");
        ismr121RequestDto.setWsInputDate(wsDateReformatAreas.getWsCurrentDate());
        ismr121RequestDto.setWsParm2Num(20);
        ismr121RequestDto.setWsParm3("+");
        ismr121RequestDto.setWsParm4("");
        ismr121RequestDto.setWsParm5("");

        Ismr121ResponseDto response = new Ismr121ResponseDto();
        try {
            log.debug("Calling Api {}", ismr121Api);
            Mono<Ismr121ResponseDto> ismr121Resp = webClient.post().uri(ismr121Api)
                    .body(Mono.just(ismr121RequestDto), Ismr121RequestDto.class).retrieve()
                    .bodyToMono(Ismr121ResponseDto.class);
            response = ismr121Resp.block();
            if (response != null) {
            }
        } catch (Exception e) {
            log.error(e);
        }
        response.setWsParm5("2023-08-06");
        if (response.getWsParm5() == "") {
            log.info("program name : owkb010");
            log.info("forced abend - calendar routine abend");

        }
        wsDateReformatAreas.setWsStartDate(response.getWsParm5());
        log.info("ws start date:  {}  ", wsDateReformatAreas.getWsStartDate());
        log.debug("Method initialization completed..");
    }

    @Transactional
    public void mainline(V02Datetime v02Datetime) {
        log.debug("Method mainlinestarted..");
        List<String> v02DatetimeList = Arrays.asList(v02Datetime.getV02ProcessDate(), v02Datetime.getV02ProcessTime());
        v01Rec.setV01OwkPartDesc(v02DatetimeList.stream().collect(Collectors.joining("")));
        System.out.println("=== Getvalue ===" + v01Rec.getV01OwkPartDesc());
        v02Datetime.setV02ProcessDate(v01Rec.getV01OwkPartDesc().substring(0, 8));
        v02Datetime.setV02ProcessTime(v01Rec.getV01OwkPartDesc().substring(8, 14));
        // v02Datetime.setV02Filler1(v01Rec.getV01OwkPartDesc().substring(14, 30));
        v01Rec.setV01OwkBusinessEntity("BK005");
        v04OwkBusEnt = "BK005";
        v01Rec.setV01OwkOrdRelTypeCode("DO");
        v04OwkRelType = "DO";
        v01Rec.setV01OwkOrdRelStatus("RP");
        v01Rec.setV01OwkNamcData("");
        try {
            mainProcessLoop(v02Datetime, wsDateReformatAreas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("Method mainline completed..");
    }

    @Transactional
    public void mainProcessLoop(V02Datetime v02Datetime, WsDateReformatAreas wsDateReformatAreas) {
        log.debug("Method mainProcessLoopstarted..");
        Stream<IC1ParkanbDto> c1ParkanbData = null;
        log.warn("Start Date.." + wsDateReformatAreas.getWsStartDate());
        log.warn("Current Date.." + wsDateReformatAreas.getWsCurrentDate());
        try {
            c1ParkanbData = parkanbRepo.getC1ParkanbByWsStartDateAndWsCurrentDate(wsDateReformatAreas.getWsStartDate(),
                    wsDateReformatAreas.getWsCurrentDate());
        } catch (Exception se) {
            se.printStackTrace();
            log.error("SQL exception : {}", se.getMessage());
        }

        System.out.println("--Size ---" + c1ParkanbData.collect(Collectors.toList()).size());
        c1ParkanbData.forEach(item -> {
            suppNotFound = true;
            ordMtdNotFound = true;
            /*
             * hostVariablesPm.setHvPmCustomerSupp(item.getHvPmCustomerSupp());
             * hostVariablesPm.setHvPmItemid(item.getHvPmItemid()); hostVariablesPm.setHvPmKanban(item.getHvPmKanban());
             * hostVariablesPm.setHvPmEmployee(item.getHvPmEmployee());
             * hostVariablesPm.setHvPmLocation(item.getHvPmEmployee());
             * hostVariablesPm.setHvPmLotQuantity(item.getHvPmLotQuantity());
             */
            lookForSupplier(item.getHvPmCustomerSupp());
            lookForOrdMetd(item.getHvPmOrderMethod());
            if (suppNotFound && ordMtdNotFound) {
                moveReformat(item.getHvPmItemid(), item.getHvPmKanban(), item.getHvPmEmployee(), item.getHvPmLocation(),
                        item.getHvPmCustomerSupp(), v02Datetime, item.getHvPmLotQuantity());
            }
        });
        log.debug("Method mainProcessLoop completed..");
    }

    public void lookForSupplier(String hvPmCustomerSupp) {
        log.debug("Method lookForSupplierstarted..");
        // t1SuppCode = hostVariablesPm.getHvPmCustomerSupp();

        if (validSuppCode.contains(hvPmCustomerSupp)) {
            suppNotFound = true;
        }
        log.debug("Method lookForSupplier completed..");
    }

    public void lookForOrdMetd(String hvPmOrderMethod) {
        log.debug("Method lookForOrdMetdstarted..");
        // t2OrderMethod = hostVariablesPm.getHvPmOrderMethod();

        if (validOrderMethod.contains(hvPmOrderMethod)) {
            ordMtdNotFound = true;
        }
        log.debug("Method lookForOrdMetd completed..");
    }

    public void moveReformat(String hvPmItemid, String hvPmKanban, String hvPmEmployee, String hvPmLocation,
            String hvPmCustomerSupp, V02Datetime v02Datetime, int hvPmLotQuantity) {
        log.debug("Method moveReformatstarted..");
        v01Rec.setV01OwkPartNum(hvPmItemid);// hostVariablesPm.getHvPmItemid());
        v01Rec.setV01OwkKanbanNum(hvPmKanban);// hostVariablesPm.getHvPmKanban());
        v01Rec.setV01OwkOrdSpecialist(hvPmEmployee);// hostVariablesPm.getHvPmEmployee());

        v01Rec.setV01OwkBeDock(hvPmLocation.substring(1, 2));

        v01Rec.setV01OwkSupPlantCode(hvPmCustomerSupp.substring(3, 5));// .getHvPmCustomerSupp().substring(3, 5));
        List<String> v02DatetimeList = Arrays.asList(v02Datetime.getV02ProcessDate(), v02Datetime.getV02ProcessTime(),
                v02Datetime.getV02Filler1());
        v01Rec.setV01OwkOrdRelDt(v02DatetimeList.stream().collect(Collectors.joining("")));
        wsQtyPerBox = hvPmLotQuantity;// hostVariablesPm.getHvPmLotQuantity();
        v01Rec.setV01OwkQtyPerBox(String.valueOf(wsQtyPerBox));
        csiplntRead(hvPmCustomerSupp);
        processPardesc(hvPmItemid);
        processParkanbCl(hvPmItemid, hvPmLocation, hvPmCustomerSupp, hvPmKanban);
        writePartmstr(v01Rec);
        log.debug("Method moveReformat completed..");
    }

    public void csiplntRead(String hvPmCustomerSupp) {
        log.debug("Method csiplntReadstarted..");
        hostVariablesPm.setHvSuppPlantCnt(0);
        ICsiplntDto iCsiplntDto = null;
        try {
            iCsiplntDto = csiplntRepo.getCsiplntByHvPmCustomerSupp(hostVariablesPm.getHvPmCustomerSupp());
        } catch (Exception se) {
            log.error("Sql Exception : {}", se);
        }
        if (iCsiplntDto != null) {
            v01Rec.setV01OwkSupPlantName(hvSmSuppPlantName.substring(1, 30));
            if (hostVariablesPm.getHvSuppPlantCnt() > 1) {
                v03ErrorMessage = "MULTIPLE SUPPLIER PLANT NAME";
            }
        } else {
            v01Rec.setV01OwkSupPlantName("NO DATA");
            v03ErrorMessage = "NO SUPPLIER PLANT NAME";
        }
        log.debug("Method csiplntRead completed..");
    }

    public void processPardesc(String hvPmItemid) {
        log.debug("Method processPardescstarted..");
        hostVariablesPm.setHvPartDescriptionCnt(0);
        IPardescDto iPardescDto = null;
        try {
            iPardescDto = pardescRepo.getPardescByHvPmItemid(hostVariablesPm.getHvPmItemid());
        } catch (Exception se) {
            log.error("Sql Exception : {}", se);
        }
        if (iPardescDto != null) {
            v01Rec.setV01OwkPartDesc(hvSmPartDescription.substring(1, 30));
            if (hostVariablesPm.getHvPartDescriptionCnt() > 1) {
                v03ErrorMessage = "MULTIPLE PART DESCRIPTION";
            }
        } else {
            v01Rec.setV01OwkPartDesc("NO DATA");
            v03ErrorMessage = "NO PART DESCRIPTION";
        }
        log.debug("Method processPardesc completed..");
    }

    public void processParkanbCl(String hvPmItemid, String hvPmLocation, String hvPmCustomerSupp, String hvPmKanban) {
        log.debug("Method processParkanbClstarted..");
        wsPartNumber = hvPmItemid; // hostVariablesPm.getHvPmItemid();
        wsDock = hvPmLocation.substring(1, 2); // hostVariablesPm.getHvPmLocation().substring(1, 2);
        wsCustomerSupp = hvPmCustomerSupp; // hostVariablesPm.getHvPmCustomerSupp();
        wsKanban = hvPmKanban; // hostVariablesPm.getHvPmKanban();
        hvPkLocation = "";
        hvPkStoreAddrPrim = "";
        hostVariablesPm.setHvCountLocation(0);
        hostVariablesPm.setHvCountStoradd(0);
        Stream<IC2ParkanbDto> c2ParkanbData = null;
        try {
            System.out.println("-- Part Number --" + wsPartNumber + " " + wsCustomerSupp + " " + wsDock + " "
                    + wsDateReformatAreas.getWsStartDate() + " " + wsDateReformatAreas.getWsCurrentDate());

            c2ParkanbData = parkanbRepo.getC2ParkanbByWsPartNumberAndWsCustomerSuppAndWsDock(wsPartNumber,
                    wsCustomerSupp, wsDock, wsDateReformatAreas.getWsStartDate(),
                    wsDateReformatAreas.getWsCurrentDate());

        } catch (Exception se) {
            log.error("SQL exception : {}", se);
        }
        c2ParkanbData.forEach(item -> {
            v01Rec.setV01OwkLinesideAddress(hvPkLocation.substring(3, 10));
            v01Rec.setV01OwkStoreAddress(hvPkStoreAddrPrim);
            v01Rec.setV01OwkNamcData(hvPkPackingStyle.substring(1, 1));
            v01Rec.setV01OwkNamcData(hvPkPackingStyle.substring(2, 2));
        });
        log.debug("Method processParkanbCl completed..");
    }

    public void closeFiles() {
        log.debug("Method closeFilesstarted..");
        wsOut01DisplayCount = wsOut01Counter;
        log.info("owkb010 extract records written =  {}  ", wsOut01DisplayCount);
        log.debug("Method closeFiles completed..");
    }

    public void writePartmstr(V01Rec v01Rec) {
        System.out.println("--- Final Return value --" + v01Rec);
        log.debug("Method writePartmstrstarted..");
        /*
         * List<String> v01RecList = Arrays.asList(v01Rec.getV01OwkBusinessEntity(), v01Rec.getV01OwkOrdRelTypeCode(),
         * v01Rec.getV01OwkOrdRelStatus(), v01Rec.getV01OwkOrdRelDt(), v01Rec.getV01OwkBeDock(),
         * v01Rec.getV01OwkDockDesc(), v01Rec.getV01OwkOrdNum(), v01Rec.getV01OwkOrdSeriesNum(),
         * v01Rec.getV01OwkOrdDelNum(), v01Rec.getV01OwkSupPlantCode(), v01Rec.getV01OwkSupPlantName(),
         * v01Rec.getV01OwkOrdSpecialist(), v01Rec.getV01OwkOrdHdlTypeCode(), v01Rec.getV01OwkBeUnloadDt(),
         * v01Rec.getV01OwkRpChecksheetPrint(), v01Rec.getV01OwkRpPicklistPrint(), v01Rec.getV01OwkRpSkidmfstPrint(),
         * v01Rec.getV01OwkPuRouteStartDt(), v01Rec.getV01OwkSupArvDt(), v01Rec.getV01OwkSupShipDock(),
         * 
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkPartnerCode()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkPartnerName()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkPntCode()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkPntShortName()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkPntName()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkRespLpFlag()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkRteCode()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkRteDepartDt()),
         * String.valueOf(v01Rec.getV01OwkData().getV01OwkRteArriveDt()), v01Rec.getV01OwkKanbanNum(),
         * v01Rec.getV01OwkPartNum(), v01Rec.getV01OwkPartDesc(), v01Rec.getV01OwkQtyPerBox(),
         * v01Rec.getV01OwkLastBoxQty(), v01Rec.getV01OwkTtlBoxOrd(), v01Rec.getV01OwkBoRemainToOrd(),
         * v01Rec.getV01OwkStoreAddress(), v01Rec.getV01OwkLinesideAddress(), v01Rec.getV01OwkNamcData(),
         * v01Rec.getV01OwkFiller());
         */// recOut01 = v01RecList.stream().collect(Collectors.joining(""));

        // V01Rec -- Convert it to json and write it
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = Obj.writeValueAsString(v01Rec);

            System.out.println("--- Json Value ---" + jsonStr);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        streamBridge.send("output01File", v01Rec);
        log.debug("Method writePartmstr completed..");
    }
}