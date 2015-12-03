package com.itsoft.ab.sys;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 18.12.13
 * Time: 15:00
 */
public class EDesc {
    public static final String UNKNOWN_ERROR = "Неизвестная ошибка.";
    public static final String ERROR400 = "Bad Request.";
    public static final String ERROR404 = "Страница не найдена:(.";
    public static final String ERROR403 = "Forbidden.";
    public static final String ERROR409 = "Конфликт.";
    public static final String ERROR415 = "Unsupported Media Type.";
    public static final String ERROR500 = "Внутренняя ошибка сервера.";
    public static final String ERROR503 = "Сервер временно недоступен.";

    //1101-1200 Ошибки ввода данных в формы
    public static final String ERROR1101 = "Поля Исполнители и Группа Исполнителей пусты.";
    public static final String ERROR1102 = "Поле Дата не заполнено.";
    public static final String ERROR1103 = "Поля Задание не заполнено.";
    public static final String ERROR1104 = "Ошибка создания нового клиента.";
    public static final String ERROR1105 = "Ошибка создания нового платежа. Отсутствуют активные контракты";
    public static final String ERROR1106 = "Ошибка планирования занятий. Контракты дублированы";
    public static final String ERROR1107 = "Клиент не найден";
    public static final String ERROR1108 = "Преподаватель не найден";

    //1201-1300 Прочие ошибки
    public static final String ERROR1201 = "Недостаточно прав для совершения действия.";

    private EDesc(){};
}
