package com.itsoft.ab.beans;

import com.itsoft.ab.model.RoomModel;
import com.itsoft.ab.persistence.RoomMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 13.02.14
 * Time: 15:37
 */
@Service
public class RoomMaster {
    Logger LOG = Logger.getLogger(RoomMaster.class);

    @Autowired
    private RoomMapper roomMapper;

    public Model setRooms(Model m){
        m.addAttribute("rooms", roomMapper.selectActiveRooms());
        return m;
    }
}
