package uo.ri.business.impl.invoice.command;

import java.util.List;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Factura;
import uo.ri.model.types.AveriaStatus;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<Long> idsAveria;
	private AveriaRepository repoAverias = Factory.repository.forAveria();
	private FacturaRepository repoFacturas = Factory.repository.forFactura();

	public CreateInvoiceFor(List<Long> idsAveria) {
		this.idsAveria = idsAveria;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long numero = repoFacturas.getNextInvoiceNumber();
		List<Averia> averias = repoAverias.findByIds(idsAveria);
		checkAllFinished(averias);
		Factura f = new Factura(numero, averias);
		repoFacturas.add(f);
		return DtoAssembler.toDto(f);
	}

	private void checkAllFinished(List<Averia> averias)throws BusinessException {
		
		for(Averia averia: averias) {
			if(!averia.getStatus().equals(AveriaStatus.TERMINADA)) {
				throw new BusinessException("No todas las averias estan terminadas");
			}
		}
		
	}

}
