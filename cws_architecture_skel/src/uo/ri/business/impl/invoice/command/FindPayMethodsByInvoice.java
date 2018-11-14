package uo.ri.business.impl.invoice.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;
import uo.ri.model.MedioPago;

public class FindPayMethodsByInvoice implements Command<List<PaymentMeanDto>> {
	private Long number;
	private FacturaRepository repoFacturas = Factory.repository.forFactura();

	public FindPayMethodsByInvoice(Long idInvoiceDto) {
		this.number = idInvoiceDto;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		List<MedioPago> menas = new ArrayList<MedioPago>();
		Factura factura = repoFacturas.findById(number);
		BusinessCheck.isNotNull(factura);
		factura.getCargos().parallelStream().forEach((cargo) -> {
			menas.add(cargo.getMedioPago());
		});
		return DtoAssembler.toPaymentMeanDtoList(menas);
	}

}
