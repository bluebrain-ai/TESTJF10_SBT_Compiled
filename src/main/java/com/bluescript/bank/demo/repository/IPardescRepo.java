package com.bluescript.bank.demo.repository;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.bluescript.bank.demo.entity.PardescEntity;
import com.bluescript.bank.demo.dto.IPardescDto;

public interface IPardescRepo extends JpaRepository<PardescEntity, String> {

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"),
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true") })
    @Query(value = "SELECT A.DESCRIPTION as hvsmpartdescription,B.CNT as hvpartdescriptioncnt FROM PARDESC A , (SELECT count(  distinct ( DESCRIPTION)) AS CNT FROM PARDESC WHERE ITEMID =:hvPmItemid) AS B WHERE A .ITEMID =:hvPmItemid", nativeQuery = true)
    IPardescDto getPardescByHvPmItemid(@Param("hvPmItemid") String hvPmItemid);

}