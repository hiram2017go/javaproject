package org.crazyit.hrsystem.vo;

import java.io.Serializable;

public class EmpBean implements Serializable {
    private static final long serialVersionUID = 48L;

    private String empName;
    private String empPass;
    private double amuont;

    public EmpBean() {
    }

    public EmpBean(String empName, String empPass, double amuont) {
        this.empName = empName;
        this.empPass = empPass;
        this.amuont = amuont;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPass() {
        return empPass;
    }

    public void setEmpPass(String empPass) {
        this.empPass = empPass;
    }

    public double getAmuont() {
        return amuont;
    }

    public void setAmuont(double amuont) {
        this.amuont = amuont;
    }
}
