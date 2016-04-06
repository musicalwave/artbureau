package com.itsoft.ab.controllers.a;

import com.itsoft.ab.beans.AuthMaster;
import com.itsoft.ab.beans.FinanceMaster;
import com.itsoft.ab.beans.PaymentMaster;
import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.ClientModel;
import com.itsoft.ab.model.ContractModel;
import com.itsoft.ab.model.PaymentModel;
import com.itsoft.ab.persistence.ClientsMapper;
import com.itsoft.ab.persistence.ContractsMapper;
import com.itsoft.ab.persistence.PaymentMapper;
import com.itsoft.ab.sys.ECode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 09.02.14
 * Time: 1:51
 */
@Controller
public class PaymentController {
    private Log LOG = LogFactory.getLog(PaymentController.class);
    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentMaster paymentMaster;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private AuthMaster authMaster;

    @Autowired
    private FinanceMaster financeMaster;

    @RequestMapping(value="/payment/{paymentId}", method = RequestMethod.GET)
    public String showPayment(@PathVariable String paymentId, Model m){
        m = authMaster.setModel(m);

        PaymentModel payment = paymentMapper.selectPayment(Integer.parseInt(paymentId));
        ClientModel client = clientsMapper.getClientByContract(payment.getContractId());
        ContractModel contract = contractsMapper.selectContract(payment.getContractId());

        //TODO Проверить наличие объектов в выборке
        m.addAttribute("client", client);
        m.addAttribute("contract", contract);
        m.addAttribute("payment", paymentMaster.preparePayment(payment));
        return "/a/payments/show";
    }

    @RequestMapping(value="/client/{clientId}/payment", method = RequestMethod.GET)
    public String newPayment(@PathVariable String clientId, @RequestParam("c") String contract, Model m){
        m = authMaster.setModel(m);
        ClientModel client = clientsMapper.getClientById(Integer.parseInt(clientId));
        List<ContractModel> contracts = new ArrayList<ContractModel>();

        if(contract.equals("all")){
            contracts = contractsMapper.getActiveClientContracts(client.getId());

            if(null == contracts || contracts.size() == 0){
                throw new ApplicationException(ECode.ERROR1105);
            }
        }else{
            int contractId = Integer.parseInt(contract);
            contracts.add(contractsMapper.getContractById(contractId));
        }


        m.addAttribute("client", client);
        m.addAttribute("contracts", contracts);
        m.addAttribute("payment", new PaymentModel());
        m.addAttribute("total", contracts.isEmpty() ? 0 : contracts.get(0).getPrice());
        return "/a/payments/new";
    }

    @RequestMapping(value="/client/payment", method = RequestMethod.POST)
    public String postPayment(@ModelAttribute PaymentModel payment, Model m){
        paymentMaster.savePaymentFromWeb(payment);
        return "redirect:/client/" + contractsMapper.getContractById(payment.getContractId()).getClientId();
    }

    @RequestMapping(value="/client/{clientId}/payments", method = RequestMethod.GET)
    public String allPayments(@PathVariable String clientId, Model m){
        m = authMaster.setModel(m);
        ClientModel client = clientsMapper.getClientById(Integer.parseInt(clientId));
        List<PaymentModel> payments = paymentMapper.getClientPayments(client.getId());
        List<PaymentModel> pws = new ArrayList<PaymentModel>();

        if(!payments.isEmpty()){
            for (PaymentModel payment:payments){
                pws.add(paymentMaster.preparePayment(payment));
            }
        }
        m.addAttribute("client", client);
        m.addAttribute("payments", pws);
        return "/a/payments/list";
    }



    //Доступны только топменеджерам
    @RequestMapping(value="/tm/payment/delete", method = RequestMethod.POST)
    public String deletePayment(@ModelAttribute PaymentModel payment, Model m){
        //TODO проверить, если ли права на удаление

        if(null != payment.getCommentNew() && !payment.getCommentNew().trim().equals("")){
            payment.setComment(payment.getComment() + "<b>" + authMaster.getLoggedUserName() + "</b>" + ": Удален " + payment.getDateS() + "<br>" + payment.getCommentNew() + "<br><br>");
            payment.setActive(0);

            paymentMapper.updateActive(payment);
            paymentMapper.updateComment(payment);

            //Получение данных платежа платежа
            payment = paymentMapper.selectPayment(payment.getId());

            //Списание виртуальных денег с контракта и счета клиента
            paymentMaster.updateDelete(payment);
        }else{
            throw new ApplicationException(ECode.ERROR415);
        }

        return "redirect:/payment/" + payment.getId();
    }

    //Подтверждение платежа финдиректором
    @RequestMapping(value="/tm/payment/approve", method = RequestMethod.POST)
    public String approvePayment(@ModelAttribute PaymentModel payment, Model m){
        //TODO проверить, есть ли права на подтверждение
        payment.setApproved(1);
        payment.setApprovedBy(authMaster.getLoggedUserId());
        payment.setApprovedDate(new Date().getTime());
        payment.setComment(payment.getComment() + "<b>" + authMaster.getLoggedUserName() + "</b>" + ": Подтвержден " + new Date().toString() + "<br><br>");

        paymentMapper.updateApproved(payment);
        paymentMapper.updateComment(payment);

        return "redirect:/payment/" + payment.getId();
    }

    //Доступны всем авторизованным
    //Перенос запланированного платежа
    @RequestMapping(value="/payment/transfer", method = RequestMethod.POST)
    public String transferPayment(@ModelAttribute PaymentModel payment, Model m){
        //TODO проверить, есть ли права на перенос

        try {
            payment.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(payment.getDateS()).getTime());
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }

        if(null != payment.getCommentNew() && !payment.getCommentNew().trim().equals("")){
            payment.setComment(payment.getComment() + "<b>" + authMaster.getLoggedUserName() + "</b>" + ": Перенос на " + payment.getDateS() + "<br>" + payment.getCommentNew() + "<br><br>");

            paymentMapper.updateDate(payment);
            paymentMapper.updateComment(payment);
        }

        return "redirect:/payment/" + payment.getId();
    }

    //Добавление комментария к платежу
    @RequestMapping(value="/payment/comment", method = RequestMethod.POST)
    public String commentPayment(@ModelAttribute PaymentModel payment, Model m){
        //TODO проверить, есть ли права на комментирование

        if(null != payment.getCommentNew() && !payment.getCommentNew().trim().equals("")){
            payment.setComment(payment.getComment() + "<b>" + authMaster.getLoggedUserName() + "</b>" + ":<br>" + payment.getCommentNew() + "<br><br>");

            paymentMapper.updateComment(payment);
        }

        return "redirect:/payment/" + payment.getId();
    }

    //Проведение платежа
    @RequestMapping(value="/payment/do", method = RequestMethod.POST)
    public String doPayment(@ModelAttribute PaymentModel payment, Model m){
        //TODO проверить, есть ли права на комментирование

        payment.setComment(payment.getComment() + "<b>" + authMaster.getLoggedUserName() + "</b>" + ": Проведен " + new Date().toString() + "<br><br>");
        payment.setDone(1);

        paymentMapper.updateDone(payment);
        paymentMapper.updateComment(payment);

        payment = paymentMapper.selectPayment(payment.getId());

        //изменение реальных счетов клиента и контракта
        paymentMaster.updateDoContract(payment);

        //Проведение платежа в базе
        financeMaster.doPayment(payment);

        return "redirect:/payment/" + payment.getId();
    }

    // Ajax

    @RequestMapping(value = "/do/payment/commit", method = RequestMethod.POST)
    public
    @ResponseBody
    void commitPayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMaster.commitPayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/restore", method = RequestMethod.POST)
    public
    @ResponseBody
    void restorePayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMaster.restorePayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    void deletePayment(@RequestParam(value = "paymentId") int paymentId) {
        paymentMapper.deletePayment(paymentId);
    }

    @RequestMapping(value = "/do/payment/insert", method = RequestMethod.POST)
    public
    @ResponseBody
    void insertPayment(@RequestParam(value = "contractId") int contractId,
                       @RequestParam(value = "date") long date,
                       @RequestParam(value = "value") int value,
                       @RequestParam(value = "planned") int planned,
                       @RequestParam(value = "done") int done) {
        PaymentModel payment = new PaymentModel();
        payment.setContractId(contractId);
        payment.setDate(date);
        payment.setValue(value);
        payment.setPlanned(planned);
        payment.setDone(done);
        paymentMapper.insertPayment(payment);
    }

    @RequestMapping(value = "/do/payment/update", method = RequestMethod.POST)
    public
    @ResponseBody
    void updatePayment(@RequestParam(value = "paymentId") int paymentId,
                       @RequestParam(value = "date") long date,
                       @RequestParam(value = "value") int value) {
        PaymentModel payment = paymentMapper.selectPayment(paymentId);
        payment.setDate(date);
        payment.setValue(value);
        paymentMapper.updatePayment(payment);
    }
}
