package uo.ri.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.Dates;
import uo.ri.model.types.ContractStatus;

@Entity
@Table(name="TCONTRATOS")
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Column(name="basesalary")
	private double baseSalaryPerYear;
	private double compensation;
	@Enumerated(EnumType.STRING)
	private ContractStatus status;
	@ManyToOne
	private Mecanico mechanic;
	private double irpf;
	@OneToMany(mappedBy = "contract")
	private Set<Payroll> payrolls = new HashSet<Payroll>();
	@ManyToOne
	private ContractCategory category;
	@ManyToOne
	private ContractType type;

	public Contract() {
	
	}
	
	public Contract(Mecanico mechanic, Date startDate2, double baseSalary) {
		this.setDate(startDate2);
		this.setSalary(baseSalary);
		this.calculatreIrpf(baseSalary);
		this.status = ContractStatus.ACTIVE;
		Association.Vinculate.link(this, mechanic);
	}

	public Contract(Mecanico mechanic2, Date startDate2, Date endDate2, double baseSalary) {
		this(mechanic2, startDate2, baseSalary);
		this.endDate = endDate2;
	}

	private void calculatreIrpf(double baseSalary) {
		if (baseSalary < 12000) {
			this.irpf = 0.0;
		} else if (baseSalary < 30000) {
			this.irpf = 0.1;
		} else if (baseSalary < 40000) {
			this.irpf = 0.15;
		} else if (baseSalary < 50000) {
			this.irpf = 0.20;
		} else if (baseSalary < 60000) {
			this.irpf = 0.30;
		} else {
			this.irpf = 0.40;
		}

	}

	public Date getStartDate() {
		return new Date(startDate.getTime());
	}

	private void setSalary(double salary) {
		if (salary < 0) {
			throw new IllegalArgumentException("The salary cannot be negative.");
		}
		this.baseSalaryPerYear = salary;
	}

	private void setDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		this.startDate = calendar.getTime();
	}

	public Date getEndDate() {
		return new Date(endDate.getTime());
	}

	public double getBaseSalaryPerYear() {
		return baseSalaryPerYear;
	}

	public double getCompensation() {
		compensation = this.baseSalaryPerYear / 365 * this.getContractType().getCompensationDays();
		if (this.payrolls.size() < 12) {
			compensation = 0.0;
		}
		return compensation;
	}

	public ContractStatus getStatus() {
		return status;
	}

	public Mecanico getMechanic() {
		return mechanic;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<Payroll>(payrolls);
	}

	Set<Payroll> _getPayrolls() {
		return (payrolls);
	}

	public ContractCategory getCategory() {
		return category;
	}

	public ContractType getType() {
		return type;
	}

	void _setCategory(ContractCategory category) {
		this.category = category;
	}

	void _setType(ContractType type) {
		this.type = type;
	}

	void _setMechanic(Mecanico m) {
		this.mechanic = m;
	}

	@Override
	public String toString() {
		return "Contract [startDate=" + startDate + ", endDate=" + endDate + ", baseSalaryPerYear=" + baseSalaryPerYear
				+ ", compensation=" + compensation + ", status=" + status + ", mechanic=" + mechanic + ", payrolls="
				+ payrolls + ", category=" + category + ", type=" + type + "]";
	}

	public void markAsFinished(Date endDate2) {
		if (this.status.equals(ContractStatus.FINISHED)) {
			throw new IllegalStateException("The contract is already finished.");
		}
		if (endDate2.before(this.startDate)) {
			throw new IllegalArgumentException("The end date cannot be before the start date.");
		}

		Date date = Dates.lastDayOfMonth(endDate2);

		this.endDate = date;
		this.status = ContractStatus.FINISHED;

	}

	public boolean isFinished() {

		return this.status.equals(ContractStatus.FINISHED);
	}

	public ContractType getContractType() {
		return this.type;
	}

	public void setEndDate(Date ldom) {
		this.endDate = new Date(ldom.getTime());

	}

	public Payroll getLastPayroll() {
		Payroll payroll = null;
		if (!payrolls.isEmpty()) {

			for (Payroll pr : payrolls) {
				if (payroll == null) {
					payroll = pr;
				} else {
					if (Dates.isAfter(pr.getDate(), payroll.getDate())) {
						payroll = pr;
					}
				}
			}
		}
		return payroll;
	}

	public double getIrpfPercent() {
		return this.irpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(baseSalaryPerYear);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mechanic == null) ? 0 : mechanic.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		if (Double.doubleToLongBits(baseSalaryPerYear) != Double.doubleToLongBits(other.baseSalaryPerYear))
			return false;
		if (mechanic == null) {
			if (other.mechanic != null)
				return false;
		} else if (!mechanic.equals(other.mechanic))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
