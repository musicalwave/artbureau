package com.itsoft.ab.beans;

import com.itsoft.ab.persistence.StatMapper;
import com.itsoft.ab.sys.Dates;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 23.08.14
 * Time: 20:33
 */
@Service
public class StatMaster {
    Log LOG = LogFactory.getLog(StatMaster.class);

    @Autowired
    private StatMapper statMapper;

    public Object getDateCalls(Date today) {
        Date tomorrow = Dates.getNextDay(today);
        return statMapper.getNCalls();
    }

    public Object getDateClients(Date today) {
        Date tomorrow = Dates.getNextDay(today);
        return statMapper.getNClients();
    }

    public Object getWeekValuePlan(Date today) {
        return 0;
    }

    public Object getWeekValueDo(Date today) {
        return 0;
    }

    public Object getDateValuePlan(Date today) {
        Date tomorrow = Dates.getNextDay(today);
        Integer sum = statMapper.getValuePlan(today.getTime(), tomorrow.getTime());
        if(sum != null){
            return sum;
        }
        return 0;
    }

    public Object getDateValueDo(Date today) {
        Date tomorrow = Dates.getNextDay(today);
        Integer sum = statMapper.getValueDo(today, tomorrow);
        if(sum != null){
            return sum;
        }
        return 0;
    }

}
