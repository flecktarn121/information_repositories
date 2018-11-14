package uo.ri.business.impl.invoice.command;

import java.util.HashMap;
import java.util.Map;

import alb.util.exception.NotYetImplementedException;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class SettleInvoice implements Command<Void> {
	private Long id;
	private Map<Long, Double> cargos;
	private FacturaRepository repoFacturas = Factory.repository.forFactura();

	public SettleInvoice(Long idInvoiceDto, Map<Long, Double> cargos) {
		this.cargos = new HashMap<Long, Double>(cargos);
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Factura factura = repoFacturas.findById(id);
		BusinessCheck.isNotNull(factura);

		factura.settle();
		throw new NotYetImplementedException("Angel, preguntale esto a Alberto");
	}

}
