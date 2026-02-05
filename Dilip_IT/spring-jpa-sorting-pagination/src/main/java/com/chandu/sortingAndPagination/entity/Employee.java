package com.chandu.sortingAndPagination.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
public class Employee {
	
	     @Id
	    @Column(name = "eid")
	    private Integer eid;

	    private String ename;

	    private BigDecimal esalary;

	    private Integer eage;

	    private String ecity;

		public Integer getEid() {
			return eid;
		}

		public void setEid(Integer eid) {
			this.eid = eid;
		}

		public String getEname() {
			return ename;
		}

		public void setEname(String ename) {
			this.ename = ename;
		}

		public BigDecimal getEsalary() {
			return esalary;
		}

		public void setEsalary(BigDecimal esalary) {
			this.esalary = esalary;
		}

		public Integer getEage() {
			return eage;
		}

		public void setEage(Integer eage) {
			this.eage = eage;
		}

		public String getEcity() {
			return ecity;
		}

		public void setEcity(String ecity) {
			this.ecity = ecity;
		}

		public Employee(Integer eid, String ename, BigDecimal esalary, Integer eage, String ecity) {
			super();
			this.eid = eid;
			this.ename = ename;
			this.esalary = esalary;
			this.eage = eage;
			this.ecity = ecity;
		}

		public Employee() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Employee [eid=" + eid + ", ename=" + ename + ", esalary=" + esalary + ", eage=" + eage + ", ecity="
					+ ecity + "]";
		}

	    
	    
	

}
