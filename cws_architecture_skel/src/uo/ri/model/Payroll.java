package uo.ri.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.Dates;

@Entity
@Table(name = "TNOMINAS")
public class Payroll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date date;
	@Column(name = "salariobase")
	private double baseSalary;
	@Column(name = "salarioextra")
	private double extraSalary;
	@Column(name = "productividad")
	private double productivity;
	@Column(name = "trienios")
	private double triennium;
	private double irpf;
	@Column(name = "seguridadsocial")
	private double socialSecurity;
	@ManyToOne
	private Contract contract;
	private double totalOfInterventions;

	public Payroll() {

	}

	public Payroll(Contract contract2, Date payrollDate, double totalInterventions) {
		Association.Percibir.link(contract2, this);
		this.setDate(payrollDate);
		this.setTotalOfInterventions(totalInterventions);
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	private void setDate(Date date) {
		date = Dates.lastDayOfMonth(Dates.subMonths(new Date(date.getTime()), 1));
		if (Dates.isAfter(contract.getStartDate(), date)) {
			throw new IllegalArgumentException(
					"The date of the payroll cannot be before the start date of the contract");
		}

		this.date = date;
	}

	public double getBaseSalary() {
		this.baseSalary = contract.getBaseSalaryPerYear() / 14;
		return baseSalary;
	}

	public double getExtraSalary() {
		if (Dates.month(this.date) == 6 || Dates.month(this.date) == 12) {
			extraSalary = contract.getBaseSalaryPerYear() / 14;
		} else {
			extraSalary = 0.0;
		}
		return extraSalary;
	}

	public double getProductivity() {
		productivity = this.getTotalOfInterventions() * contract.getCategory().getProductivityPlus();
		return productivity;
	}

	public double getTriennium() {
		triennium = this.contract.getCategory().getTrienniumSalary()
				* (Dates.diffDays(date, contract.getStartDate()) / 365 / 3);
		return triennium;
	}

	public double getIrpf() {
		this.irpf = this.contract.getIrpfPercent() * this.getGrossTotal();
		return irpf;
	}

	public double getSocialSecurity() {
		socialSecurity = (this.contract.getBaseSalaryPerYear() / 12) * 0.05;
		return socialSecurity;
	}

	public Contract getContract() {
		return contract;
	}

	public double getGrossTotal() {
		double salary = this.getBaseSalary();
		double extra = this.getExtraSalary();
		double prod = (this.getProductivity());
		double tri = this.getTriennium();
		double gross = salary + extra + prod + tri;
		return gross;
	}

	public double getDiscountTotal() {
		return this.getIrpf() + (this.getSocialSecurity());
	}

	public double getNetTotal() {

		return this.getGrossTotal() - this.getDiscountTotal();
	}

	private void setTotalOfInterventions(double baseSalary) {
		if (baseSalary < 0) {
			throw new IllegalArgumentException("The base salary cannot be negative.");
		}
		this.totalOfInterventions = baseSalary;
	}

	private double getTotalOfInterventions() {
		return totalOfInterventions;
	}

	void setContract(Contract c) {
		this.contract = c;
	}

	public Long getId() {
		return id;
	}

}
