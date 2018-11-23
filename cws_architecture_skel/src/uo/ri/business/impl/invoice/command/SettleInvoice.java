package uo.ri.business.impl.invoice.command;

import java.util.Map;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class SettleInvoice implements Command<Void> {
	private Long id;
	private FacturaRepository repoFacturas = Factory.repository.forFactura();

	public SettleInvoice(Long idInvoiceDto, Map<Long, Double> cargos) {
		this.id = idInvoiceDto;
	}

	@Override
	public Void execute() throws BusinessException {
		Factura factura = repoFacturas.findById(id);
		BusinessCheck.isNotNull(factura);
		factura.settle();
		return null;
	}

}
