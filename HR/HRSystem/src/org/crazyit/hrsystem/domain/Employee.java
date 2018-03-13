package org.crazyit.hrsystem.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="employee_inf")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorColumn(name="emp_type", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value="1")
public class Employee implements Serializable {

    private static final long serialVersionUID = 48L;

    @Id
    @Column(name="emp_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //标识属性
    private Integer id;

    @Column(name="emp_name", nullable = false, length = 50, unique = true)
    //员工姓名
    private String name;

    @Column(name="emp_pass", nullable = false, length = 50)
    //员工密码
    private String pass;

    //员工工资
    @Column(name="emp_salary", nullable = false)
    private double salary;

    //员工对应的经理
    @ManyToOne(targetEntity = Manager.class)
    @JoinColumn(name="mgr_id")
    private Manager manager;

    //员工对应出勤记录
    @OneToMany(targetEntity = Attend.class, mappedBy = "employee")
    private Set<Attend> attends = new HashSet<>();

    //员工对应的工资支付记录
    @OneToMany(targetEntity = Payment.class, mappedBy = "employee")
    private Set<Payment> payments = new HashSet<>();

    public Employee() {
    }

    public Employee(Integer id, String name, String pass, double salary, Manager manager, Set<Attend> attends, Set<Payment> payments) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.salary = salary;
        this.manager = manager;
        this.attends = attends;
        this.payments = payments;
    }

    public Set<Attend> getAttends() {
        return attends;
    }

    public void setAttends(Set<Attend> attends) {
        this.attends = attends;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    //根据name和pass来重写hascode()方法
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pass == null) ? 0 : pass.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return true;

        Employee other = (Employee)obj;
        if(name == null) {
            if (other.name != null) return false;
        }else if(!name.equals(other.name)){
            return false;
        }
        if(pass == null){
            if(other.pass != null) return  false;
        }else if(!pass.equals(other.pass)){
            return false;
        }

        return true;
    }
}
