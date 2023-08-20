package com.bluescript.bank.demo.repository;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.bluescript.bank.demo.entity.CsiplntEntity;
import com.bluescript.bank.demo.dto.ICsiplntDto;

public interface ICsiplntRepo extends JpaRepository<CsiplntEntity, String> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true") })
    @Query(value = "SELECT A.NAME as hvsmsuppplantname,B.CNT as hvsuppplantcnt FROM CSIPLNT A , (SELECT count(  distinct ( NAME)) AS CNT FROM CSIPLNT WHERE PLANT_TYPE = 'PC' AND CUSTOMER_SUPP =:hvPmCustomerSupp) AS B WHERE PLANT_TYPE = 'PC' AND CUSTOMER_SUPP =:hvPmCustomerSupp", nativeQuery = true)
    ICsiplntDto getCsiplntByHvPmCustomerSupp(@Param("hvPmCustomerSupp") String hvPmCustomerSupp);

}