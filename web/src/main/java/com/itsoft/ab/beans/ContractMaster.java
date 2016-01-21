package com.itsoft.ab.beans;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.model.*;
import com.itsoft.ab.persistence.*;
import com.itsoft.ab.sys.ECode;
import com.itsoft.ab.sys.MapMaster;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created withIntelliJ IDEA.
 * User: itertychnyi
 * Date: 02.02.14
 * Time: 18:08
 */
@Service
public class ContractMaster {
    Log LOG = LogFactory.getLog(ContractMaster.class);

    @Autowired
    private TeacherTypeMapper teacherTypeMapper;

    @Autowired
    private LessonsMapper lessonsMapper;

    @Autowired
    private EventsMapper eventsMapper;

    @Autowired
    private ContractsMapper contractsMapper;

    @Autowired
    private PaymentMaster paymentMaster;

    @Autowired
    private JContractsMapper jContractsMapper;

    public ContractMaster() {
    }

    public List<ContractModel> prepareContracts(List<ContractModel> contracts){
        List<ContractModel> l = new ArrayList<ContractModel>();
        for(ContractModel contract : contracts){
            l.add(prepareContract(contract));
        }
        return l;
    }

    public ContractModel prepareContract(ContractModel contract){

        //Преобразование временных констант
        try{
            contract.setDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        try{
            contract.setFreezeDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getFreezeDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        try{
            contract.setFreezeFinishDateS(new SimpleDateFormat("dd MMMM yyyy").format(contract.getFreezeFinishDate()));
        } catch (NullPointerException e){
            //Nothing to do
        }

        contract.setMoneyNeed(contract.getPrice() - contract.getMoneyV());
        return contract;
    }

    public ContractModel setContractPrice(ContractModel c){
        TeacherTypeModel t = teacherTypeMapper.getById(c.getTeacherTypeId());
        int type = c.getContractType();
        if(1 == type){
            c.setPrice(c.getCountLessons() * t.getpPrice());
        }
        if(2 == type){
            c.setPrice(c.getCountLessons() * t.getgPrice());
        }
        if(3 == type){
            c.setPrice(c.getCountLessons() * t.getdPrice());
        }
        if(4 == type){
            c.setPrice(c.getCountLessons() * t.getaPrice());
        }
        return c;

    }

    public ContractModel saveFromWeb(ContractModel contract){

        try {
            contract.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(contract.getDateS()));
        } catch (ParseException e) {
            throw new ApplicationException(ECode.ERROR415);
        }

        //Сохранение объекта контракта
        contract = insertContract(contract);

        if(contract.getPrev() > 0)
            insertPrev(contract.getId(), contract.getPrev());

        //Обновление баланса клиента и контракта
        PaymentModel p = new PaymentModel();
        p.setContractId(contract.getId());
        //!Цену контракта следует вычитать!
        p.setValue(-contract.getPrice());
        paymentMaster.updateVBalance(p);

        return contract;
    }

    private void insertPrev(int id, int prev) {
        jContractsMapper.insertPrev(id, prev);
    }

    public void createSchedule(ContractModel contract) {
//        PROCESS
//        понять день недели начальной даты
//        понять дни недели эвентов
//        выстроить эвенты в логическом следовании после начальной даты
//        запустить цикл записи лессонов в эвенты в соответствии с этим порядком

        String[] dArray = contract.getDays().split(",");

        //Получаем все эвенты
        Map<Integer,Integer> daysMap = new LinkedHashMap<Integer,Integer>();
        for(String s : dArray){
            EventModel event = eventsMapper.getEventById(Integer.parseInt(s));
            //initial.add(event.getWeekday());
            daysMap.put(event.getId(),event.getWeekday());
        }

        //Сортируем по возрастанию
        daysMap = MapMaster.sortByValue(daysMap, MapMaster.ASC);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(contract.getDate());
        Integer startWeekDay = Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        //Переход к системе понедельние - 1, воскресенье - 7
        if(startWeekDay.equals(1)){
            startWeekDay = 7;
        }else{
            startWeekDay = startWeekDay - 1;
        }

        Map<Integer,Integer> before = new LinkedHashMap<Integer,Integer>();
        Map<Integer,Integer> after= new LinkedHashMap<Integer,Integer>();

        //
        for (Map.Entry<Integer,Integer> day : daysMap.entrySet()){
            if (day.getValue().compareTo(startWeekDay) >= 0){
                after.put(day.getKey(), day.getValue() - startWeekDay);
            }else{
                before.put(day.getKey(), 7 + day.getValue() - startWeekDay);
            }
        }


        //Получим необходимую последовательность эвентов - смещений в weekday
        after.putAll(before);


        int n = contract.getCountLessons();
        while(n > 0){

            for (Map.Entry<Integer,Integer> day : after.entrySet()){
                if(n > 0){
                    //Определить дату
                    //Записать лессон
                    calendar.add(Calendar.DATE, day.getValue());
                    //########
                    //Определили дату нового занятия

                        LessonModel lesson = new LessonModel();
                        lesson.setEventId(day.getKey());
                        //Запланирован
                        lesson.setStatusId(1);
                        lesson.setContractId(contract.getId());
                        lesson.setDate(calendar.getTime());

                        lessonsMapper.insertLesson(lesson);
                    //########
                    calendar.add(Calendar.DATE, - day.getValue());
                    n--;
                }
            }
            calendar.add(Calendar.DATE, 7);
        }
    }

    public ContractModel insertContract(ContractModel c) {
        contractsMapper.insertContract(c);
        return c;
    }

    public void updateTransferById(int contractId) {
        contractsMapper.updateTransferById(contractId);
    }

    public void doLesson(LessonModel lesson) {
        contractsMapper.doLesson(lesson);

        //Проверка количества оставшихся запланированных занятий
        //Если 0 - меням статус контракта на 2(finished)
        int count = getCountPlannedLessons(lesson.getContractId());
        if(0 == count){
            updateActive(lesson.getContractId(), 0);
        }
    }

    public int getCountPlannedLessons(int contractId) {
        return contractsMapper.getCountPlannedLessons(contractId);
    }

    public void updateStatus(int contractId, int contractStatus) {
        contractsMapper.updateStatus(contractId, contractStatus);
    }

    public void updateActive(int contractId, int active) {
        contractsMapper.updateActive(contractId, active);
    }

    public void updateCash(int contractId, int cash) {
        contractsMapper.updateCash(contractId, cash);
    }
}
