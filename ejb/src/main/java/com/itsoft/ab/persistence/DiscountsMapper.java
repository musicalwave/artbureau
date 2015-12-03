package com.itsoft.ab.persistence;

import com.itsoft.ab.model.SimpleModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 21.06.14
 * Time: 9:53
 */
public interface DiscountsMapper {
    List<SimpleModel> selectAll();
    SimpleModel selectById(int id);

    List<SimpleModel> selectAllActive();
}
