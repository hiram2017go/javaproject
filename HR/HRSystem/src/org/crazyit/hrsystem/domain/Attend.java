package org.crazyit.hrsystem.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
* 对应每天的考勤，包含考勤时间、考勤员工、是否上班及考勤类别等信息
*
*/
@Entity
@Table(name="attend_inf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attend implements Serializable {

    private static final long serialVersionUID = 48L;

    @Id
    @Column(name="attend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //出勤日期
    @Column(name="duty_day", nullable = false, length = 50)
    private String dutyDay;

    //打卡时间
    @Column(name="punch_time")
    private Date punchTime;

    //代表本次打卡是否为上班打卡
    @Column(name="is_come")
    private boolean isCome;

    //本次出勤的类型
    @ManyToOne(targetEntity = AttendType.class)
    @JoinColumn(name = "type_id", nullable = false)
    private AttendType type;

    //本次出勤关联的员工
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name="emp_id", nullable = false)
    private Employee employee;

    public Attend() {
    }

    public Attend(Integer id, String dutyDay, Date punchTime, boolean isCome, AttendType type, Employee employee) {
        this.id = id;
        this.dutyDay = dutyDay;
        this.punchTime = punchTime;
        this.isCome = isCome;
        this.type = type;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDutyDay() {
        return dutyDay;
    }

    public void setDutyDay(String dutyDay) {
        this.dutyDay = dutyDay;
    }

    public Date getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(Date punchTime) {
        this.punchTime = punchTime;
    }

    public boolean getIsCome() {
        return isCome;
    }

    public void setIsCome(boolean come) {
        isCome = come;
    }

    public AttendType getType() {
        return type;
    }

    public void setType(AttendType type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attend attend = (Attend) o;

        if (isCome != attend.isCome) return false;
        if (!dutyDay.equals(attend.dutyDay)) return false;
        return employee.equals(attend.employee);
    }

    @Override
    public int hashCode() {
        int result = dutyDay.hashCode();
        result = 31 * result + (isCome ? 1 : 0);
        result = 31 * result + employee.hashCode();
        return result;
    }
}
