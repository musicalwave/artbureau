package com.itsoft.ab.beans;

import com.itsoft.ab.model.ContractScheduleModel;
import com.itsoft.ab.persistence.ContractScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContractScheduleMaster {

    @Autowired
    private ContractScheduleMapper contractScheduleMapper;

    public
    List<ContractScheduleModel>
    getContractSchedule(int contractId) {
        return  contractScheduleMapper.getContractSchedule(contractId);
    }

    public
    boolean
    isScheduleItemAvailable(ContractScheduleModel item, Date date) {
        return contractScheduleMapper.isScheduleItemAvailable(item, date);
    }

    public
    ContractScheduleModel
    findItemByLesson(int lessonId) {
        return contractScheduleMapper.findItemByLesson(lessonId);
    }

    public
    void
    insertContractSchedule(int contractId, List<ContractScheduleModel> schedule) {
        for(ContractScheduleModel item: schedule)
            item.setContractId(contractId);
        contractScheduleMapper.insertContractSchedule(schedule);
    }

    public
    void
    insertContractScheduleItem(ContractScheduleModel item) {
        contractScheduleMapper.insertContractScheduleItem(item);
    }

    public
    void
    updateContractScheduleItem(ContractScheduleModel item) {
        contractScheduleMapper.updateContractScheduleItem(item);
    }

    public
    void
    deleteContractScheduleItem(int itemId) {
        contractScheduleMapper.deleteContractScheduleItem(itemId);
    }

    public
    List<ContractScheduleModel>
    filterItemsByWeekday(List<ContractScheduleModel> items, int weekday) {
        List<ContractScheduleModel> filteredItems = new ArrayList<>();
        for(ContractScheduleModel item: items)
            if(item.getWeekday() == weekday)
                filteredItems.add(item);

        return filteredItems;
    }
}
