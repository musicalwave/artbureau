package com.itsoft.ab.persistence;

import com.itsoft.ab.model.FinanceModel;
import com.itsoft.ab.model.TypeModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.03.14
 * Time: 18:19
 */
public interface FinanceMapper {
    void insertNew(FinanceModel f);

    List<TypeModel> selectAllTypesDefault();
}
