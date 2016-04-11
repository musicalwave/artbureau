package com.itsoft.ab.persistence;

import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.model.PaymentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 24.11.13
 * Time: 23:52
 */
public interface ClientsMapper {
    List<ClientModel> getAllClients();
    int getIdByObject(ClientModel clientModel);
    int getIdByPhone1(String phone1);
    ClientModel getClientById(int id);
    ClientModel getClientWithContractData(int id);
    void insertClient(ClientModel client);
    void updateClientPart(ClientModel client);
    void updateClient(ClientModel client);
    List<ClientModel> getSiteClients();
    List<ClientModel> findClients(@Param("fname")String fname, @Param("lname")String lname,
                                  @Param("phone")String phone, @Param("bdate")String bdate, @Param("email")String email, @Param("comment")String comment);

    ClientModel getClientByContract(int contractId);

    int getDonePaymentsTotal(@Param("id") int id);

    int getPlannedPaymentsTotal(@Param("id") int id);

    int getMoneySpentOnLessons(@Param("clientId") int clientId);

    int getWriteoffTotal(@Param("clientId") int clientId);

    int getCashbackTotal(@Param("clientId") int clientId);

    int getTotal(@Param("clientId") int clientId);

    //Для платежей "на месте"
    void updateBalance(PaymentModel payment);

    void updateVBalance(PaymentModel payment);

    void updateJdata(ClientModel client);

    ClientModel getClientJdataById(int clientId);

    List<ClientModel> getSiteUndoneClients();

    void deleteClient(int id);

}
