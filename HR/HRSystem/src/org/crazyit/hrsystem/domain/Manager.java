package org.crazyit.hrsystem.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorValue(value = "2")
public class Manager extends Employee implements Serializable {
    //方便反序列化用
    private static final long serialVersionUID = 48L;

    //该经理管理的部门
    @Column(name = "dept_name", length = 50)
    private String dept;

    //该经理管理的部门
    @OneToMany(targetEntity = Employee.class, mappedBy = "manager")
    private Set<Employee> employees = new HashSet<>();

    //该经理签署的所有批复
    @OneToMany(targetEntity = CheckBack.class, mappedBy = "manager")
    private Set<CheckBack> checks = new HashSet<>();

    public Manager() {
    }

    public Manager(String dept, Set<Employee> employees, Set<CheckBack> checks) {
        this.dept = dept;
        this.employees = employees;
        this.checks = checks;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<CheckBack> getChecks() {
        return checks;
    }

    public void setChecks(Set<CheckBack> checks) {
        this.checks = checks;
    }
}
