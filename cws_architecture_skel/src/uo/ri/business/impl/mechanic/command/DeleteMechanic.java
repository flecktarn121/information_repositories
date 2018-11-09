package uo.ri.business.impl.mechanic.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class DeleteMechanic implements Command<Void> {

	private Long idMecanico;

	private MecanicoRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public Void execute() throws BusinessException {

		Mecanico m = repo.findById(idMecanico);// puede devolver null
		checkCanBeDeleted(m);
		repo.remove(m);
		return null;
	}

	private void checkCanBeDeleted(Mecanico m) throws BusinessException {
		BusinessCheck.isNotNull(m, "No existe el mecanico.");

		BusinessCheck.isTrue(m.getAverias().isEmpty(), "No se puede borrar, tiene averias asginadas");

		BusinessCheck.isTrue(m.getIntervenciones().isEmpty(), "No se puede borrar, tiene intervenciones asginadas");

	}

}
