package com.itsoft.ab.persistence;

import com.itsoft.ab.model.CallModel;
import com.itsoft.ab.model.CallTypeModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.11.13
 * Time: 23:51
 */
public interface CallsMapper {
    void createNewCall(CallModel callModel);
    List<CallModel> getAllCalls();
    List<CallModel> getAllClientCalls(int clientId);
    List<CallModel> getCallsFrom(@Param("fromDate")Date fromDate);
    List<CallModel> getCallsTo(@Param("toDate")Date toDate);
    List<CallModel> getCalls(@Param("fromDate")Date fromDate, @Param("toDate")Date toDate);
    CallModel getCallById(@Param("id")int id);
    void updateCall(CallModel call);
    void insertCall(CallModel call);
    void deleteCall(int callId);

    void insertCallTypes(@Param("CallTypeModels") List<CallTypeModel> callTypeModels);
    void deleteCallTypes(@Param("id") int id);

    void updateClientId(@Param("id")int callId, @Param("clientId")int clientId);
}
