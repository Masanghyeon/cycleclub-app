package com.example.lsh.cycleclubapp;

/**
 * Created by 406 on 2015-08-19.
 */
public class ClubVO {

    private String ccode;
    private String cname;
    private String cplace;
    //   private java.sql.Date ctime;
    private String ctime;
    private String mid;

    public ClubVO() {

    }

    public ClubVO(String ccode, String cname, String cplace, String ctime, String mid) {
        super();
        this.ccode = ccode;
        this.cname = cname;
        this.cplace = cplace;
        this.ctime = ctime;
        this.mid = mid;
    }



    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCplace() {
        return cplace;
    }
    public void setCplace(String cplace) {
        this.cplace = cplace;
    }
    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    /*
    public java.sql.Date getCtime() {
        return ctime;
    }
    public void setCtime(java.sql.Date ctime) {
        this.ctime = ctime;
    }
    */
    public String getMid() {
        return mid;
    }
    public void setMid(String mid) {
        this.mid = mid;
    }




}