package com.bluescript.bank.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Table(name = "CSIPLNT")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : TSTCNTL
public class CsiplntEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUSTOMER_SUPP")
    private String customerSupp;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PLANT_TYPE")
    private String plantType;
    @Column(name = "EFF_START")
    private String effStart;
    @Column(name = "EFF_STOP")
    private String effStop;

}