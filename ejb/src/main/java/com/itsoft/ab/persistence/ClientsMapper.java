package com.itsoft.ab.persistence;

import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.model.PaymentModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.11.13
 * Time: 23:52
 */
public interface ClientsMapper {
    // SELECT
    ClientModel getClientById(int id);
    ClientModel getClientByContract(int contractId);
    List<ClientModel> findClients(
        @Param("fname")String fname,
        @Param("lname")String lname,
        @Param("phone")String phone,
        @Param("bdate")String bdate,
        @Param("email")String email,
        @Param("comment")String comment);
    Date getFirstContractDate(@Param("clientId") int clientId);
    int getContractCount(@Param("clientId") int clientId);
    int getActiveContractCount(@Param("clientId") int clientId);
    int getDonePaymentsTotal(@Param("id") int id);
    int getPlannedPaymentsTotal(@Param("id") int id);
    int getMoneySpentOnLessons(@Param("clientId") int clientId);
    int getWriteoffTotal(@Param("clientId") int clientId);
    int getCashbackTotal(@Param("clientId") int clientId);
    int getTotal(@Param("clientId") int clientId);
    List<ClientModel> getSiteUndoneClients();

    // INSERT
    void insertClient(ClientModel client);

    // UPDATE
    void updateClientPart(ClientModel client);
    void updateClient(ClientModel client);
    void updateBalance(PaymentModel payment);
    void updateVBalance(PaymentModel payment);
    void updateJdata(ClientModel client);
    void deleteClient(int id);
}
