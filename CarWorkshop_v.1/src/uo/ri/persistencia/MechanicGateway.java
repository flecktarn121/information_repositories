package uo.ri.persistencia;

import java.util.List;

import uo.ri.bussiness.dto.MechanicDTO;

public interface MechanicGateway {
	void create(MechanicDTO dto);
	List<MechanicDTO> read();
	void update(MechanicDTO dto);
	void delete(MechanicDTO dto);
}
