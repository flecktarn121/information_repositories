package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ContractType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(name = "compensationdaysperyear")
	private int compensationDays;
	@OneToMany(mappedBy = "type")
	private Set<Contract> contracts = new HashSet<Contract>();

	public ContractType() {
	}

	public ContractType(String name, int compensationDays) {
		this.name = name;
		this.compensationDays = compensationDays;
	}

	public String getName() {
		return name;
	}

	public int getCompensationDays() {
		return compensationDays;
	}

	public Set<Contract> getContracts() {
		return new HashSet<Contract>(contracts);
	}

	Set<Contract> _getContracts() {
		return (contracts);
	}

	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDays=" + compensationDays + "]";
	}

	public Long getId() {

		return this.id;
	}

	public void setCompensationDays(int days) {
		this.compensationDays = days;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ContractType other = (ContractType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
