package uo.ri.business.impl.invoice.command;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class FindInvoiceById implements Command<InvoiceDto> {
	private Long number;
	private FacturaRepository repoFacturas = Factory.repository.forFactura();

	public FindInvoiceById(Long number) {
		this.number = number;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Factura factura = repoFacturas.findById(number);
		BusinessCheck.isNotNull(factura, "No se encontro una factura con numero " + number);
		return DtoAssembler.toDto(factura);
	}

}
