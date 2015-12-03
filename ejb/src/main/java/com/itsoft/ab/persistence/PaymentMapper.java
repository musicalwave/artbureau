package com.itsoft.ab.persistence;

import com.itsoft.ab.model.PaymentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 25.12.13
 * Time: 15:42
 */
public interface PaymentMapper {
    void insertPayment(PaymentModel payment);

    void updateDone(PaymentModel payment);
    void updateComment(PaymentModel payment);
    void updateDate(PaymentModel payment);
    void updateApproved(PaymentModel payment);
    void updateActive(PaymentModel payment);

    List<PaymentModel> getPlannedClientPayments(int clientId);

    List<PaymentModel> getClientPayments(int id);
    List<PaymentModel> getContractPayments(int id);

    PaymentModel selectPayment(int i);

    List<PaymentModel> getPlannedPayments(@Param("fromDate")long fromDate, @Param("toDate")long toDate);

    List<PaymentModel> getDonePayments(@Param("fromDate")long fromDate, @Param("toDate")long toDate);
}
