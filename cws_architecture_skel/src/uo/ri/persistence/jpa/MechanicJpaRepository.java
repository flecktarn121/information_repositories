package uo.ri.persistence.jpa;

import uo.ri.business.repository.MecanicoRepository;
import uo.ri.model.Mecanico;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class MechanicJpaRepository extends BaseRepository<Mecanico> implements MecanicoRepository {

	@Override
	public Mecanico findByDni(String dni) {
		return Jpa.getManager().createNamedQuery("Mecanico.findByDni", Mecanico.class)
				.setParameter(1, dni)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
		// creo la consulta externalizada, le pongo el parametro, consigo una lista, la
		// paso a stream, recojo el primer elemento, si no exite devuelve null
	}

}
