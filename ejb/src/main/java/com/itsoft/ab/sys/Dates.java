package com.itsoft.ab.sys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 08.03.14
 * Time: 17:51
 */
public class Dates {
    public static long dateLongFromString1(String date) throws ParseException {
        return new SimpleDateFormat("dd MM yyyy").parse(date).getTime();
    };

    public static long dateLongFromString2(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yy").parse(date).getTime();
    };

    public static String dateStringFromLong1(long date){
        return new SimpleDateFormat("dd MMMM yyyy").format(new Date(date));
    };

    public static String dateStringFromLong2(long date){
        return new SimpleDateFormat("dd-MM-yy").format(new Date(date));
    };

    public List<WeekDay> getWeekDays(){
        List<WeekDay> days = new ArrayList<WeekDay>();
        days.add(new WeekDay(1, "Понедельник"));
        days.add(new WeekDay(2, "Вторник"));
        days.add(new WeekDay(3, "Среда"));
        days.add(new WeekDay(4, "Четверг"));
        days.add(new WeekDay(5, "Пятница"));
        days.add(new WeekDay(6, "Суббота"));
        days.add(new WeekDay(7, "Воскресенье"));
        return days;
    }

    public class WeekDay{
        private int id;
        private String name;

        public WeekDay(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "WeekDay{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static Date getNextDay(Date date){
        //Получаем следующий день
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        Date toDate = calendar.getTime();

        return toDate;
    }

    // Calendar's week starts from sunday
    // (means that DAY_OF_WEEK for sunday is 1)
    // Need to adjust it.
    public static int getCalendarWeekday(Calendar date) {
        // monday's number is 2
        return (date.get(Calendar.DAY_OF_WEEK) - 2 + 7) % 7 + 1;
    }
}
