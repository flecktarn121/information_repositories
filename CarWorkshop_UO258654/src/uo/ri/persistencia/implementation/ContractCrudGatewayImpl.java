package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.events.EndDocument;

import alb.util.date.Dates;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.ContractCrudGateway;

public class ContractCrudGatewayImpl implements ContractCrudGateway {
	private Connection connection;
	private Configuration config = Configuration.getInstance();

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;

	}

	@Override
	public ContractDto read(Long id) {
		try (PreparedStatement prep = connection.prepareStatement(config.getProperty("SQL_SELECT_CONTRACT"))) {
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			Date startDate;
			Date endDate;
			double compensation;
			String status;
			ContractDto dto = null;
			while (rs.next()) {
				dto = new ContractDto();
				compensation = rs.getDouble(2);
				status = rs.getString(3);
				startDate = rs.getDate(4);
				endDate = rs.getDate(6);
				dto.id = id;
				dto.compensation = compensation;
				dto.status = status;
				dto.startDate = new java.util.Date(startDate.getTime());
				dto.mechanicId = rs.getLong(5);
				if (endDate == null) {
					dto.endDate = null;
				} else {
					dto.endDate = new java.util.Date(endDate.getTime());
				}
			}
			return dto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement pst = connection.prepareStatement(config.getProperty("SQL_DELETE_CONTRACT"));) {
			pst.setLong(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(ContractDto dto) {
		try (PreparedStatement pst = connection.prepareStatement(config.getProperty("SQL_UPDATE_CONTRACT"));) {

			pst.setDate(1, new java.sql.Date(dto.endDate.getTime()));
			pst.setDouble(2, dto.yearBaseSalary);
			pst.setLong(3, dto.id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<ContractDto> getContractsForMechanicId(Long id) {
		try (PreparedStatement prep = connection
				.prepareStatement(config.getProperty("SQL_SELECT_CONTRACTs_BY_MECHANIC"))) {
			List<ContractDto> contracts = new ArrayList<ContractDto>();
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			Date startDate;
			double compensation;
			String status;
			ContractDto dto = null;
			while (rs.next()) {
				dto = new ContractDto();
				compensation = rs.getDouble(2);
				status = rs.getString(3);
				startDate = rs.getDate(4);
				dto.id = rs.getLong(1);
				dto.compensation = compensation;
				dto.status = status;
				dto.startDate = new java.util.Date(startDate.getTime());
				contracts.add(dto);
			}
			return contracts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void finish(Long id, Date endDate) {
		try (PreparedStatement pst = connection.prepareStatement(config.getProperty("SQL_FINISH_CONTRACT"));) {

			pst.setDate(1, new java.sql.Date(endDate.getTime()));
			pst.setLong(2, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public ContractCategoryDto getCategoryByContractId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PayrollDto> getPayrollsForContractId(Long id) {
		List<PayrollDto> payrolls = new ArrayList<PayrollDto>();
		try (PreparedStatement prep = connection
				.prepareStatement(config.getProperty("SQL_SELECT_PAYROLLS_BY_CONTRACT"))) {
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			Date date;
			double baseSalary;
			double extraSalary;
			double productivity;
			double triennium;
			double irpf;
			double socialSecurity;
			PayrollDto dto = null;
			while (rs.next()) {
				dto = new PayrollDto();
				date = new Date(rs.getDate(1).getTime());
				baseSalary = rs.getDouble(2);
				extraSalary = rs.getDouble(3);
				productivity = rs.getDouble(4);
				triennium = rs.getDouble(5);
				irpf = rs.getDouble(6);
				socialSecurity = rs.getDouble(7);
				dto.id = id;
				dto.baseSalary = baseSalary;
				dto.date = date;
				dto.extraSalary = extraSalary;
				dto.productivity = productivity;
				dto.triennium = triennium;
				dto.irpf = irpf;
				dto.socialSecurity = socialSecurity;
				payrolls.add(dto);
			}
			return payrolls;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(ContractDto dto) {
		try (PreparedStatement prep = connection.prepareStatement(config.getProperty("SQL_ADD_CONTRACT"));) {
			Date date = Dates.addMonths(Dates.firstDayOfMonth(dto.startDate), 1);
			prep.setDate(1, new java.sql.Date(date.getTime()));
			if (dto.endDate == null) {
				prep.setDate(2, null);
			} else {
				prep.setDate(2, new java.sql.Date(dto.endDate.getTime()));
			}
			prep.setDouble(3, dto.yearBaseSalary);
			prep.setDouble(4, dto.compensation);
			prep.setString(5, dto.status);
			prep.setLong(6, dto.mechanicId);
			prep.setLong(7, dto.categoryId);
			prep.setLong(8, dto.typeId);
			prep.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
