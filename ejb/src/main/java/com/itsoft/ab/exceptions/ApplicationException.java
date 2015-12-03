package com.itsoft.ab.exceptions;

import com.itsoft.ab.sys.ECode;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 14.11.13
 * Time: 9:25
 */
public class ApplicationException extends RuntimeException{
    private ECode error;

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(ECode error) {
        this.error = error;
    }

    public int getId(){
        return error.id();
    }

    public String getDesc(){
        return error.desc();
    }

    public String getTodo(){
        return error.todo();
    }
}
