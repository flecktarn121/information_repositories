package uo.ri.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContractType {
	private String name;
	private int compensationDays;
	private Set<Contract> contracts = new HashSet<Contract>();

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + compensationDays;
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
		if (compensationDays != other.compensationDays)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDays=" + compensationDays + "]";
	}

}
