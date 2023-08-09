package com.bluescript.bank.demo.repository;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import java.util.stream.*;
import com.bluescript.bank.demo.entity.ParkanbEntity;
import com.bluescript.bank.demo.dto.IC1ParkanbDto;
import com.bluescript.bank.demo.dto.IC2ParkanbDto;

public interface IParkanbRepo extends JpaRepository<ParkanbEntity, String> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "100"), // modify
                                                                                                             // based on
                                                                                                             // performance
            @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "false"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true") })
    @Query(value = "SELECT CUSTOMER_SUPP as hvPmCustomerSupp,LOCATION as hvPmLocation,EMPLOYEE as hvPmEmployee,KANBAN as hvPmKanban,ITEMID as hvPmItemid,LOT_QUANTITY as hvPmLotQuantity,EFF_START as hvPmEffStart,ORDER_METHOD as hvPmOrderMethod FROM PARKANB WHERE TYPE = 'CD' AND CSI_TYPE = 'SU' AND (EFF_START <=:wsStartDate AND (EFF_STOP >=:wsCurrentDate OR EFF_STOP IS NULL)) ORDER BY CUSTOMER_SUPP , LOCATION , ITEMID , EFF_START", nativeQuery = true)
    @Transactional
    Stream<IC1ParkanbDto> getC1ParkanbByWsStartDateAndWsCurrentDate(@Param("wsStartDate") String wsStartDate,
            @Param("wsCurrentDate") String wsCurrentDate);

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "100"), // modify
                                                                                                             // based on
                                                                                                             // performance
            @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "false"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true") })
    @Query(value = "SELECT SHARE as hvPkShare,LOCATION as hvPkLocation,STORE_ADDRESS_PRIM as hvPkStoreAddrPrim,PACKING_STYLE as hvPkPackingStyle FROM PARKANB WHERE TYPE = 'CL 'AND CSI_TYPE = 'SU 'AND ITEMID =:wsPartNumber AND CUSTOMER_SUPP =:wsCustomerSupp AND SUBSTR (LOCATION , 1 , 2) =:wsDock AND (EFF_START < =:wsStartDate AND (EFF_STOP > =:wsCurrentDate OR EFF_STOP IS NULL)) ORDER BY SHARE DESC , LOCATION DESC", nativeQuery = true)
    Stream<IC2ParkanbDto> getC2ParkanbByWsPartNumberAndWsCustomerSuppAndWsDock(
            @Param("wsPartNumber") String wsPartNumber, @Param("wsCustomerSupp") String wsCustomerSupp,
            @Param("wsDock") String wsDock, @Param("wsStartDate") String wsStartDate,
            @Param("wsCurrentDate") String wsCurrentDate);

}