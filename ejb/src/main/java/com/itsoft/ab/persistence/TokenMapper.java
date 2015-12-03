package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TokenModel;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 09.03.14
 * Time: 2:51
 * To change this template use File | Settings | File Templates.
 */
public interface TokenMapper {
    void deleteToken(String username);
    TokenModel getTokenForSeries(String series);
    void updateToken(@Param("tokenValue") String tokenValue, @Param("date") java.util.Date date, @Param("series") String series);
    void insertToken(@Param("tokenValue") String tokenValue, @Param("date") java.util.Date date,
                     @Param("series") String series, @Param("username") String username);
}
