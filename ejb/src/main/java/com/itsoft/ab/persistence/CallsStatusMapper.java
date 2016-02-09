package com.itsoft.ab.persistence;

import com.itsoft.ab.model.SimpleModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 27.01.14
 * Time: 18:12
 */
public interface CallsStatusMapper {
    List<SimpleModel> selectAll();
    SimpleModel selectById(int id);
    boolean redirectToNewContract(int id);
}
