package uo.ri.persistence.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uo.ri.business.repository.AveriaRepository;
import uo.ri.model.Averia;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class AveriaJpaRepository extends BaseRepository<Averia> implements AveriaRepository {

	@Override
	public List<Averia> findByIds(List<Long> idsAveria) {
		List<Averia> averias = Collections.synchronizedList(new ArrayList<Averia>());
		// we create a parallel stream, where we launch queries to get the breakdown
		// with the specified id, and if it is not null, we add it to the list.
		idsAveria.parallelStream().forEach((id) -> {
			Averia averia;
			averia = Jpa.getManager().createNamedQuery("Averia.findById", Averia.class).setParameter(1, id)
					.getResultList().stream().findFirst().orElse(null);
			if (averia != null) {
				averias.add(averia);
			}
		});
		return averias;
	}

	@Override
	public List<Averia> findNoFacturadasByDni(String dni) {
		return Jpa.getManager().createNamedQuery("Averia.findNoFacturadas", Averia.class).setParameter(1, dni)
				.getResultList();
	}

}
