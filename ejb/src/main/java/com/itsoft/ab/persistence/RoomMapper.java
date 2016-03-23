package com.itsoft.ab.persistence;

import com.itsoft.ab.model.RoomModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 13.02.14
 * Time: 15:30
 */
public interface RoomMapper {
    List<RoomModel> selectActiveRooms();
    List<RoomModel> selectActiveFilialRooms(int filialId);
    RoomModel selectRoomById(int roomId);
}
