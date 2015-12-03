package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.TypeModel;
import com.itsoft.ab.persistence.TypesMapper;
import com.itsoft.ab.sys.DataMaster;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itsoft.ab.logger.LoggerConstants.ERROR_LOGGER;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.05.14
 * Time: 16:32
 */
@Service
public class TypeMaster {
    private Logger LOG = Logger.getLogger(TypeMaster.class);
    private final static Logger LOGGER = Logger.getLogger(ERROR_LOGGER);

    @Autowired
    private TypesMapper typesMapper;

    public TypeModel getTypeById(int id){
        List<TypeModel> result = typesMapper.getTypeById(id);

        if (result.size() > 1 || result.size() < 1) {
            LOGGER.error("Полученный массив данных имеет неподходящий размер.");
            throw new ApplicationException(ECode.ERROR500);
        }

        return result.get(0);
    }

    public void insertType(TypeModel type) {
        if (type != null && type instanceof TypeModel){
            typesMapper.insertType(type);
        }else{
            throw new ApplicationException(ECode.ERROR500);
        }
    }

    public void insertPrice(TypeModel type) {
        if (type != null && type instanceof TypeModel){
            typesMapper.insertPrice(type);
        }else{
            throw new ApplicationException(ECode.ERROR500);
        }
    }


    public void updateType(TypeModel type) {
        if (type != null && type instanceof TypeModel){
            typesMapper.updateType(type);
            typesMapper.updatePrice(type);

        }else{
            throw new ApplicationException(ECode.ERROR500);
        }
    }

    public TypeModel getTypeByName(String name) {
        name = DataMaster.checkString(name);
        TypeModel type = typesMapper.getTypeByName(name);
        return type;
    }

    public boolean checkExistance(TypeModel type) {
        TypeModel t = typesMapper.getTypeByName(type.getName());
        if(t == null){
            return false;
        }
        return true;
    }

    public List<TypeModel> getTeacherTypes(int teacherId) {
        List<TypeModel> types = typesMapper.getTeacherTypes(teacherId);
        return types;
    }
}
