package org.crazyit.hrsystem.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/*
* 对应普通员工的考勤提出申请，包含申请理由、是否被批复及申请改变的类型属性
* */

@Entity
@Table(name="application_inf")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 48L;

    @Id
    @Column(name="app_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //申请理由
    @Column(name="app_reason", length = 50)
    private String reason;

    //是否处理
    @Column(name="app_result")
    private boolean result;

    //关联的出勤记录
    @ManyToOne(targetEntity = Attend.class)
    @JoinColumn(name="attend_id", nullable = false)
    private Attend attend;

    //希望将制定出勤改为的type类型
    @ManyToOne(targetEntity = AttendType.class)
    @JoinColumn(name="type_id", nullable = false)
    private AttendType type;

    //申请的结果
    @OneToOne(targetEntity = CheckBack.class, mappedBy = "app")
    private CheckBack check;

    public Application() {
    }

    public Application(Integer id, String reason, boolean result, Attend attend, AttendType type, CheckBack check) {
        this.id = id;
        this.reason = reason;
        this.result = result;
        this.attend = attend;
        this.type = type;
        this.check = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean getIsResult() {
        return result;
    }

    public void setIsResult(boolean result) {
        this.result = result;
    }

    public Attend getAttend() {
        return attend;
    }

    public void setAttend(Attend attend) {
        this.attend = attend;
    }

    public AttendType getType() {
        return type;
    }

    public void setType(AttendType type) {
        this.type = type;
    }

    public CheckBack getCheck() {
        return check;
    }

    public void setCheck(CheckBack check) {
        this.check = check;
    }
}
