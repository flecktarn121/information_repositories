package uo.ri.business.impl.contract.command;

import java.util.Date;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Mecanico;
import uo.ri.model.Association.Categorize;
import uo.ri.model.Association.Typefy;

public class AddContract implements Command<Void> {
	private ContractRepository repo = Factory.repository.forContract();
	private MecanicoRepository repoMechanic = Factory.repository.forMechanic();
	private ContractCategoryRepository repoCategory = Factory.repository.forContractCategory();
	private ContractTypeRepository repoType = Factory.repository.forContractTpe();
	private ContractDto dto;

	public AddContract(ContractDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico mechanic = repoMechanic.findById(dto.mechanicId);
		if (mechanic.getActiveContract() != null) {
			mechanic.getActiveContract().markAsFinished(new Date(System.currentTimeMillis()));
		}
		Contract contract = new Contract(mechanic, dto.startDate, dto.yearBaseSalary);
		ContractCategory category = repoCategory.findById(dto.categoryId);
		BusinessCheck.isNotNull(category, "No existe una categoría con id " + dto.categoryId);
		Categorize.link(contract, category);
		ContractType type = repoType.findById(dto.typeId);
		BusinessCheck.isNotNull(type, "No existe un tipo con id " + dto.typeId);
		Typefy.link(contract, type);
		if (dto.endDate == null) {
			repo.add(contract);
		} else {
			contract.setEndDate(dto.endDate);
			repo.add(contract);
		}
		return null;
	}

}
