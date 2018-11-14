package uo.ri.persistence.jpa;

import uo.ri.business.repository.FacturaRepository;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class FacturaJpaRepository extends BaseRepository<Factura> implements FacturaRepository {

	@Override
	public Factura findByNumber(Long numero) {
		return Jpa.createEntityManager().createNamedQuery("Factura.findByNumber", Factura.class).setParameter(1, numero)
				.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Long getNextInvoiceNumber() {
		Factura factura = Jpa.createEntityManager().createNamedQuery("Factura.findLatest", Factura.class)
				.getResultList().stream().findFirst().orElse(null);
		long number = 0;
		if (factura != null) {
			number = factura.getNumero() + 1;
		}
		return number;
	}

}
