package com.itsoft.ab.persistence;

import com.itsoft.ab.model.TypeModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 27.01.14
 * Time: 17:17
 */
public interface TypesMapper {
    List<TypeModel> selectAll();
    List<TypeModel> selectAllActive();

    List<TypeModel> getTeacherTypes(int teacherId);

    TypeModel getTypeById(int id);

    void insertType(TypeModel type);

    void updateType(TypeModel type);

    void insertPrice(TypeModel type);

    void updatePrice(TypeModel type);

    TypeModel getTypeByName(String name);
}
