package com.itsoft.ab.beans;

import com.itsoft.ab.model.JClientsModel;
import com.itsoft.ab.persistence.JClientsMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 30.05.14
 * Time: 12:59
 */
@Service
public class JournalMaster {
    Logger LOG = Logger.getLogger(JournalMaster.class);

    @Autowired
    private JClientsMapper jClientsMapper;

    public List<JClientsModel> getDateClients(Date fromDate, Date toDate) {

        List<JClientsModel> data = jClientsMapper.getDateClients(fromDate, toDate);

        for(JClientsModel j : data){
            j.setDateS(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(j.getDate()));
        }

        return data;
    }
}
