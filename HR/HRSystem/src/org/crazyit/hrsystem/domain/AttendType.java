package org.crazyit.hrsystem.domain;

/*
* 对应考勤的类别，包含考勤的名词，如吃到、早退等名称
* */

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="attend_type_inf")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class AttendType implements Serializable{
    private static final long serialVersionUID = 48L;

    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //出勤类型的名称
    @Column(name="type_name", nullable = false, length = 50)
    private String name;

    //此类出勤对应的罚款
    @Column(name = "amerce_amount", nullable = false)
    private double amerce;

    public AttendType() {
    }

    public AttendType(Integer id, String name, double amerce) {
        this.id = id;
        this.name = name;
        this.amerce = amerce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmerce() {
        return amerce;
    }

    public void setAmerce(double amerce) {
        this.amerce = amerce;
    }
}
