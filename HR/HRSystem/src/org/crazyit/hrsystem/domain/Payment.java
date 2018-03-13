package org.crazyit.hrsystem.domain;


/*
* 对应每月所发的薪水信息，班汉发薪的月份、领薪的员工及薪资数等信息。
* */

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="payment_inf")
public class Payment implements Serializable {
    private static final long serialVersionUID = 48L;

    @Id
    @Column(name="pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //发薪的月份
    @Column(name="pay_month", nullable = false, length = 50)
    private String payMonth;

    //发薪的数量
    @Column(name="pay_amount", nullable = false)
    private double amount;

    //领薪的员工
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name="emp_id", nullable = false)
    private Employee employee;

    public Payment() {
    }

    public Payment(Integer id, String payMonth, double amount, Employee employee) {
        this.id = id;
        this.payMonth = payMonth;
        this.amount = amount;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
