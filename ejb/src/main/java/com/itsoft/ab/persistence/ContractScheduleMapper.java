package com.itsoft.ab.persistence;

import com.itsoft.ab.model.ContractScheduleModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ContractScheduleMapper {
    List<ContractScheduleModel> getContractSchedule(@Param("contractId") int contractId);
    boolean isScheduleItemAvailable(
      @Param("item") ContractScheduleModel item,
      @Param("date") Date date);
    ContractScheduleModel findItemByLesson(@Param("lessonId") int lessonId);
    void insertContractScheduleItem(@Param("item") ContractScheduleModel item);
    void insertContractSchedule(@Param("items") List<ContractScheduleModel> items);
    void updateContractScheduleItem(@Param("item") ContractScheduleModel item);
    void deleteContractScheduleItem(@Param("id") int id);
}
