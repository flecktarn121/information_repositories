package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

public class ContractCategory {
	private String name;
	private double trienniumSalary;
	private double productivityPlus;
	private Set<Contract> contracts = new HashSet<Contract>();

	public ContractCategory(String name, double trienniumSalary, double productivityPlus) {
		this.name = name;
		this.trienniumSalary = trienniumSalary;
		this.productivityPlus = productivityPlus;
	}

	public String getName() {
		return name;
	}

	public double getTrienniumSalary() {
		return trienniumSalary;
	}

	public double getProductivityPlus() {
		return productivityPlus;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(productivityPlus);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(trienniumSalary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ContractCategory other = (ContractCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(productivityPlus) != Double.doubleToLongBits(other.productivityPlus))
			return false;
		if (Double.doubleToLongBits(trienniumSalary) != Double.doubleToLongBits(other.trienniumSalary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractCategory [name=" + name + ", trienniumSalary=" + trienniumSalary + ", productivityPlus="
				+ productivityPlus + "]";
	}

}
