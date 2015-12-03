package com.itsoft.ab.beans;

import com.itsoft.ab.model.FinanceModel;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.model.TypeModel;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.FinanceMapper;
import com.itsoft.ab.sys.FinanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.03.14
 * Time: 20:50
 */
@Service
public class FinanceMaster {

    @Autowired
    private FinanceMapper financeMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    public void doPayment(PaymentModel payment){
        //Запись в журнал прихода/расхода
        FinanceModel f = new FinanceModel();
        f.setValue(payment.getValue());
        f.setDirection(FinanceType.CONTRACTPAYMENT.direction());
        f.setClientId(contractsMapper.getContractById(payment.getContractId()).getClientId());
        f.setType(FinanceType.CONTRACTPAYMENT.id());

        financeMapper.insertNew(f);
    }

    public List<TypeModel> getAllTypesDefault(){
        List<TypeModel> types = financeMapper.selectAllTypesDefault();
        return types;
    }
}
