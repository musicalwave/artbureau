package com.itsoft.ab.persistence;

import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.LessonModel;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.model.SimpleModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.02.14
 * Time: 18:38
 */
public interface ContractsMapper {
    List<ContractModel> getActiveClientContracts(int id);
    List<ContractModel> getClientContracts(int id);

    ContractModel selectContract(int id);
    void updateContractMoney(PaymentModel payment);
    void updateContractVMoney(PaymentModel payment);
    void updateContractDoMoney(PaymentModel payment);
    void updateDeletePlanned(PaymentModel payment);

    List<ContractModel> getActiveTeacherContracts(int teacherId);
    List<ContractModel> getTeacherContracts(int teacherId);

    ContractModel getContractById(int contractId);

    void doLesson(LessonModel lesson);


    void insertContract(ContractModel c);

    List<SimpleModel> selectContractTypes();

    ContractModel findContract(@Param("client")int clientId, @Param("type")int teacherTypeId, @Param("date")Date date, @Param("count")int countLessons);

    void updateTransferById(int contractId);

    int getCountPlannedLessons(int contractId);

    void updateStatus(@Param("contractId")int contractId, @Param("contractStatus")int contractStatus);

    void updateActive(@Param("contractId")int contractId, @Param("active")int active);

    void updateCash(@Param("contractId")int contractId, @Param("cash")int cash);
}
