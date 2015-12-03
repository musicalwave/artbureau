package com.itsoft.ab.controllers;

import com.itsoft.ab.exceptions.ApplicationException;
import com.itsoft.ab.sys.ECode;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 18.12.13
 * Time: 14:31
 */
@ControllerAdvice
public class ExceptionsHandler {
    Logger LOG = Logger.getLogger(ExceptionsHandler.class);

    @ExceptionHandler
    ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception{
        ModelAndView mav = new ModelAndView("error");
        ApplicationException error;
        if (e.getClass() != ApplicationException.class){
            e.printStackTrace();
            error = new ApplicationException(ECode.UNKNOWN_ERROR);
        }else{
            e.printStackTrace();
            error = (ApplicationException)e;
        }
        mav.addObject("errorId", error.getId());
        mav.addObject("errorDesc", error.getDesc());
        mav.addObject("errorTodo", error.getTodo());

        return mav;
    }
}
