package uo.ri.persistencia;

import java.util.List;

import uo.ri.bussiness.dto.BreakdownDTO;

public interface BreakdownGateway {
	public void add(BreakdownDTO dto);

	public void delete(BreakdownDTO dto);

	public void update(BreakdownDTO dto);

	public List<BreakdownDTO> read();
}
