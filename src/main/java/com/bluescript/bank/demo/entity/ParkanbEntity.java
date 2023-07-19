package com.bluescript.bank.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Table(name = "PARKANB")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : TSTCNTL
public class ParkanbEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUSTOMER_SUPP")
    private String customerSupp;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "EMPLOYEE")
    private String employee;
    @Column(name = "KANBAN")
    private String kanban;
    @Column(name = "ITEMID")
    private String itemid;
    @Column(name = "LOT-QUANTITY")
    private int lotQuantity;
    @Column(name = "EFF_START")
    private String effStart;
    @Column(name = "EFF_STOP")
    private String effStop;
    @Column(name = "ORDER_METHOD")
    private String orderMethod;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "CSI_TYPE")
    private String csiType;
    @Column(name = "SHARE")
    private double share;
    @Column(name = "STORE_ADDRESS_PRIM")
    private String storeAddressPrim;
    @Column(name = "PACKING_STYLE")
    private String packingStyle;

}