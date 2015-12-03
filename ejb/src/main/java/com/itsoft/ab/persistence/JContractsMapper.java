package com.itsoft.ab.persistence;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.09.14
 * Time: 15:24
 */

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;

@Controller
public interface JContractsMapper {

    void insertPrev(@Param("toId")int id, @Param("fromId")int prev);
}
