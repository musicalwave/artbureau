package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.persistence.ClientsMapper;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.PaymentMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.itsoft.ab.logger.LoggerConstants.PAY_LOGGER;
/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.02.14
 * Time: 21:05
 */
@Service
public class PaymentMaster {
    private final static Logger LOGGER = Logger.getLogger(PAY_LOGGER);

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private FinanceMaster financeMaster;

    public PaymentMaster(){};

    public PaymentModel preparePayment(PaymentModel payment){
        //Преобразование временных констант
        try{
            payment.setDateS(new SimpleDateFormat("dd MMMM yyyy").format(new Date(payment.getDate())));
        } catch (NullPointerException e){
            throw new ApplicationException(ECode.ERROR415);
        }

        if(payment.getApproved() > 0){
            try{
                payment.setApprovedDateS(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(new Date(payment.getApprovedDate())));
            } catch (NullPointerException e){
                throw new ApplicationException(ECode.ERROR415);
            }
        }

        return payment;
    }

    public List<PaymentModel> preparePayments(List<PaymentModel> payments){
        List<PaymentModel> p = new ArrayList<PaymentModel>();
        for(PaymentModel payment : payments){
            p.add(preparePayment(payment));
        }
        return p;
    }

    public int savePaymentFromWeb(PaymentModel payment){
        //Сохранение платежа
        savePayment(payment);

        return 200;
    }

    private int savePayment(PaymentModel payment){
        if(null != payment.getPlannedS() && payment.getPlannedS().equals("true")){
            payment.setPlanned(1);
            payment.setDone(0);

            //Действия с балансами
            //clients.moneyV + value
            //contracts.moneyV + value

            //Обновление виртуального баланса клиента
            updateVBalance(payment);

            //Перевод с виртуального баланса на контракт
            updateVContract(payment);

            try {
                payment.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(payment.getDateS()).getTime());
            } catch (ParseException e) {
                throw new ApplicationException(ECode.ERROR415);
            }
        }else{
            payment.setPlanned(0);
            payment.setDate((new Date().getTime()));
            payment.setDone(1);

            //Нужно с балансами
            //clients.moneyV + value
            //clients.moneyR + value
            //contracts.moneyV + value
            //contracts.moneyR + value

            //Обновление баланса клиента
            updateBalance(payment);

            //Перевод с баланса на контракт
            updateContract(payment);

            financeMaster.doPayment(payment);
        }

        paymentMapper.insertPayment(payment);

        return 200;
    }

    //Обновление счета клиента
    //clients.moneyR + value
    //clients.moneyV + value
    private int updateBalance(PaymentModel payment){
        clientsMapper.updateBalance(payment);
        return 200;
    }

    //Перенос денег на контракт
    //contracts.moneyR + value
    //contracts.moneyV + value
    public int updateContract(PaymentModel payment){
        contractsMapper.updateContractMoney(payment);
        return 200;
    }

    //Обновление виртуального счета клиента
    //clients.moneyV + value
    public int updateVBalance(PaymentModel payment){
        clientsMapper.updateVBalance(payment);
        return 200;
    }

    //Перенос виртуальных денег на контракт
    //contracts.moneyV + value
    public int updateVContract(PaymentModel payment){
        contractsMapper.updateContractVMoney(payment);
        return 200;
    }

    //Перенос виртуальных денег на контракт
    //clients.moneyR + value
    //contracts.moneyR + value
    public int updateDoContract(PaymentModel payment){
        contractsMapper.updateContractDoMoney(payment);
        return 200;
    }

    //Списание виртуальных денег
    //clients.moneyV - value
    //contracts.moneyV - value
    public int updateDelete(PaymentModel payment){
        contractsMapper.updateDeletePlanned(payment);
        return 200;
    }

    public List<PaymentModel> getDatePlannedPayments(Date fromDate, Date toDate) {
        List<PaymentModel> payments = paymentMapper.getPlannedPayments(fromDate.getTime(), toDate.getTime());
        return preparePayments(payments);
    }

    public Object getDateDonePayments(Date fromDate, Date toDate) {
        List<PaymentModel> payments = paymentMapper.getDonePayments(fromDate.getTime(), toDate.getTime());
        return preparePayments(payments);
    }
}
