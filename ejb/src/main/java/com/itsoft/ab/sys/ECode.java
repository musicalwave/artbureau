package com.itsoft.ab.sys;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 18.12.13
 * Time: 14:39
 */
public enum ECode {
    UNKNOWN_ERROR(0, "severe", EDesc.UNKNOWN_ERROR, ETodo.UNKNOWN_ERROR),
    ERROR404(404, "severe", EDesc.ERROR404, ETodo.ERROR404),
    ERROR403(403, "severe", EDesc.ERROR403, ETodo.ERROR403),
    ERROR409(409, "severe", EDesc.ERROR409, ETodo.ERROR409),
    ERROR415(415, "severe", EDesc.ERROR415, ETodo.ERROR415),
    ERROR500(500, "severe", EDesc.ERROR500, ETodo.ERROR500),
    ERROR503(503, "severe", EDesc.ERROR503, ETodo.ERROR503),
    ERROR400(400, "severe", EDesc.ERROR400, ETodo.ERROR400),
    ERROR1101(1101, "warn", EDesc.ERROR1101, ETodo.ERROR1101),
    ERROR1102(1102, "warn", EDesc.ERROR1102, ETodo.ERROR1102),
    ERROR1103(1103, "warn", EDesc.ERROR1103, ETodo.ERROR1103),
    ERROR1104(1104, "warn", EDesc.ERROR1104, ETodo.ERROR1104),
    ERROR1105(1105, "warn", EDesc.ERROR1105, ETodo.ERROR1105),
    ERROR1106(1106, "warn", EDesc.ERROR1106, ETodo.ERROR1106),
    ERROR1107(1107, "warn", EDesc.ERROR1107, ETodo.ERROR1107),
    ERROR1108(1108, "warn", EDesc.ERROR1108, ETodo.ERROR1108),
    ERROR1201(1201, "warn", EDesc.ERROR1201, ETodo.ERROR1201);

    private final int id;
    private final String level;
    private final String desc;
    private final String todo;

    ECode(int id, String level, String desc, String todo){
        this.id = id;
        this.level = level;
        this.desc = desc;
        this.todo = todo;
    }

    public int id(){ return id; }
    public String level(){ return level; }
    public String desc(){ return desc; }
    public String todo(){ return todo; }
}
