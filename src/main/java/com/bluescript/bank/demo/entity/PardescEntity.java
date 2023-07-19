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
@Table(name = "PARDESC")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : TSTCNTL
public class PardescEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ITEMID")
    private String itemid;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "EFF_START")
    private String effStart;
    @Column(name = "EFF_STOP")
    private String effStop;

}