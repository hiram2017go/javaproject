package org.crazyit.hrsystem.domain;

/*
* 对应批复，包含该批复对应的申请、是否通过申请、由哪个经理完成批复等属性。
* */
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="checkback_inf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CheckBack implements Serializable{

    private  static final long serialVersionUID = 48L;

    @Id
    @Column(name="check_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //是否同意申请
    @Column(name="check_result", nullable = false)
    private boolean result;

    //批复理由
    @Column(name="check_reason", length = 255)
    private String reason;

    //该批复对应的申请
    @OneToOne(targetEntity = Application.class)
    @JoinColumn(name="app_id", nullable = false, unique = true)
    private Application app;

    @ManyToOne(targetEntity = Manager.class)
    @JoinColumn(name="mgr_id", nullable = false)
    private Manager manager;

    public CheckBack() {
    }

    public CheckBack(Integer id,boolean result, String reason, Application app, Manager manager) {
        this.id = id;
        this.result = result;
        this.reason = reason;
        this.app = app;
        this.manager = manager;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
