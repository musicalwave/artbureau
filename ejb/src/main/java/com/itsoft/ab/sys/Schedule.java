package com.itsoft.ab.sys;

import com.itsoft.ab.exceptions.ApplicationException;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 01.01.14
 * Time: 19:13
 */
public class Schedule {
    private Schedule(){};

    /*
        Методы определения наличия в расписании работы соответствующего дня (Расписание представлено в виде десятичного числа, двоичная форма которого представляет собой обозначение рабочих дней)
        Например: Расписание "104" представимо в виде 1101000, что соответствует рабочим Понедельнику, Вторнику и Четвергу.
    */

    public static boolean hasMonday(int s){
        if (1 == gets(s,6)){
            return true;
        }
        return false;
    }

    public static boolean hasTuesday(int s){
        if (1 == gets(s,5)){
            return true;
        }
        return false;
    }

    public static boolean hasWednesday(int s){
        if (1 == gets(s,4)){
            return true;
        }
        return false;
    }

    public static boolean hasThursday(int s){
        if (1 == gets(s,3)){
            return true;
        }
        return false;
    }

    public static boolean hasFriday(int s){
        if (1 == gets(s,2)){
            return true;
        }
        return false;
    }

    public static boolean hasSaturday(int s){
        if (1 == gets(s,1)){
            return true;
        }
        return false;
    }

    public static boolean hasSunday(int s){
        if (1 == gets(s,0)){
            return true;
        }
        return false;
    }

    private static int gets(int s, int n){
        s = s / (int)Math.pow(2,n);
        return s % 2;
    }

    public static int create(int mon, int tue, int wed, int thu, int fri, int sat, int sun){
        if((0==mon && 1==mon)||(0==tue && 1==tue)||(0==wed && 1==wed)||(0==thu && 1==thu)||(0==fri && 1==fri)||(0==sat && 1==sat)||(0==sun && 1==sun)) {
            return mon*64 + tue*32 + wed*16 + thu*8 + fri*4 + sat*2 + sun;
        }
        throw new ApplicationException(ECode.ERROR400);
    }

}
