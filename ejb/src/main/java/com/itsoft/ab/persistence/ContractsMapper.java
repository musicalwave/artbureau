package com.itsoft.ab.persistence;

import com.itsoft.ab.model.*;
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

    List<ContractOptionModel> selectContractOptions();

    ContractOptionModel getContractOptionById(@Param("id") int id);

    ContractModel findContract(@Param("client")int clientId, @Param("type")int teacherTypeId, @Param("date")Date date, @Param("count")int countLessons);

    void updateTransferById(int contractId);

    int getPlannedLessonCount(int contractId);

    int getCompletedLessonCount(int contractId);

    int getLessonCount(int contractId);

    void updateStatus(@Param("contractId")int contractId, @Param("contractStatus")int contractStatus);

    void updateActive(@Param("contractId")int contractId, @Param("active")int active);

    void updateCash(@Param("contractId")int contractId, @Param("cash")int cash);

    void freezeContract(ContractModel c);

    List<ContractModel> getContractsByLastLessonDate(String lastLessonDate);

    void deleteContract(@Param("contractId") int contractId);

    void deletePlannedLessons(int contractId);

    void writeoff(@Param("contractId") int contractId,
                  @Param("value") int value);

    void cashback(@Param("contractId") int contractId,
                  @Param("cashback") int cashback,
                  @Param("fine") int fine);

    int getDonePaymentsTotal(@Param("contractId") int contractId);
    int getMoneySpentOnLessons(@Param("contractId") int contractId);
    int getWriteoffTotal(@Param("contractId") int contractId);
    int getCashbackTotal(@Param("contractId") int contractId);

    int getPrice(@Param("contractId") int contractId);

    void updateLessonCount(@Param("contractId") int contractId,
                           @Param("lessonCount") int lessonCount);
}
