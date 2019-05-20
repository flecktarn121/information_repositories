package uo.ri.persistencia.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import alb.util.jdbc.Jdbc;
import uo.ri.bussiness.dto.ContractCategoryDto;
import uo.ri.bussiness.dto.ContractDto;
import uo.ri.bussiness.dto.ContractTypeDto;
import uo.ri.bussiness.dto.PayrollDto;
import uo.ri.configuration.Configuration;
import uo.ri.persistencia.PayrollGateway;
import uo.ri.persistencia.PersistanceException;

public class PayrollGatewayImpl implements PayrollGateway {
	Configuration config = Configuration.getInstance();

	@Override
	public List<PayrollDto> findAllPayrolls() {
		List<PayrollDto> payrolls = new LinkedList<PayrollDto>();
		try (Connection conn = Jdbc.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(config.getProperty("SQL_LIST_ALL_PAYROLLS"))) {
			PayrollDto dto;
			while (rs.next()) {
				dto = new PayrollDto();
				dto.id = rs.getLong(1);
				dto.date = rs.getDate(2);
				dto.baseSalary = rs.getDouble(3);
				dto.extraSalary = rs.getDouble(4);
				dto.productivity = rs.getDouble(5);
				dto.triennium = rs.getDouble(6);
				dto.irpf = rs.getDouble(7);
				dto.socialSecurity = rs.getDouble(8);
				payrolls.add(dto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return payrolls;
	}

	@Override
	public List<PayrollDto> findPayrollsByMechanicId(Long id) {
		List<PayrollDto> payrolls = new LinkedList<PayrollDto>();
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_LIST_PAYROLLS_BY_MECHANIC"));) {
			PayrollDto dto;
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				dto = new PayrollDto();
				dto.id = rs.getLong(1);
				dto.date = rs.getDate(2);
				dto.baseSalary = rs.getDouble(3);
				dto.extraSalary = rs.getDouble(4);
				dto.productivity = rs.getDouble(5);
				dto.triennium = rs.getDouble(6);
				dto.irpf = rs.getDouble(7);
				dto.socialSecurity = rs.getDouble(8);
				payrolls.add(dto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return payrolls;
	}

	@Override
	public PayrollDto findPayrollById(Long id) {
		PayrollDto dto = null;
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_LIST_PAYROLLS_BY_ID"));) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				dto = new PayrollDto();
				dto.id = rs.getLong(1);
				dto.date = rs.getDate(2);
				dto.baseSalary = rs.getDouble(3);
				dto.extraSalary = rs.getDouble(4);
				dto.productivity = rs.getDouble(5);
				dto.triennium = rs.getDouble(6);
				dto.irpf = rs.getDouble(7);
				dto.socialSecurity = rs.getDouble(8);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return dto;
	}

	@Override
	public void deleteLastPayrollForMechanicId(Long id) {
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_DELETE_PAYROLL_BY_MECHANIC"));) {
			st.setLong(1, id);
			int result = st.executeUpdate();
			if (result <= 0) {
				throw new PersistanceException("No había nómina correspondiente con ese ID de mecánico.");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public int deleteLastGenetaredPayrolls() {
		int result = 0;
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_DELETE_PAYROLLS"));
				PreparedStatement st2 = conn.prepareStatement(config.getProperty("SQL_DELETE_PAYROLLS"));) {
			Date date = getPreviousMonthDate(new Date(System.currentTimeMillis()));
			st.setDate(1, date);
			// date of two months ago, in case the last month's payrolls were not generated
			// yet
			st2.setDate(1, getPreviousMonthDate(date));
			result = st.executeUpdate();
			if (result <= 0) {
				result = st2.executeUpdate();
				if (result <= 0) {
					throw new PersistanceException("No hay nóminas que borrar.");
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public void generatePayroll(PayrollDto dto, Long contractId) {
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_GENERATE_PAYROLL"));) {
			st.setDate(1, this.getPreviousMonthDate(new Date(System.currentTimeMillis())));
			st.setDouble(2, dto.baseSalary);
			st.setDouble(3, dto.extraSalary);
			st.setDouble(4, dto.productivity);
			st.setDouble(5, dto.triennium);
			st.setDouble(6, dto.irpf);
			st.setDouble(7, dto.socialSecurity);
			st.setLong(8, contractId);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Date getPreviousMonthDate(Date date2) {
		// auxiliary method to get the date of the last day of the previous moth
		LocalDate date = date2.toLocalDate();
		date = date.withDayOfMonth(1);
		date = date.minusDays(1);
		return Date.valueOf(date);

	}

	@Override
	public void getContracts(List<ContractDto> contracts, List<ContractCategoryDto> categories,
			List<ContractTypeDto> types) {
		Date date = getPreviousMonthDate(new Date(System.currentTimeMillis()));
		try (Connection conn = Jdbc.getConnection();
				PreparedStatement st = conn.prepareStatement(config.getProperty("SQL_LIST_COMPLETE_CONTRACTS"));) {
			st.setDate(1, Date.valueOf(date.toLocalDate().withDayOfMonth(1)));
			ResultSet rs = st.executeQuery();
			ContractDto contract;
			ContractCategoryDto category;
			ContractTypeDto type;
			while (rs.next()) {
				contract = new ContractDto();
				contract.id = rs.getLong(1);
				contract.startDate = rs.getDate(2);
				contract.endDate = rs.getDate(3);
				contract.compensation = rs.getDouble(4);
				contract.mechanicId = rs.getLong(5);

				category = new ContractCategoryDto();
				category.id = rs.getLong(6);
				category.name = rs.getString(7);
				category.trieniumSalary = rs.getDouble(8);
				category.productivityPlus = rs.getDouble(9);

				type = new ContractTypeDto();
				type.id = rs.getLong(10);
				type.name = rs.getString(11);
				type.compensationDays = rs.getInt(12);
				
				contract.yearBaseSalary = rs.getDouble(13);
				
				contracts.add(contract);
				categories.add(category);
				types.add(type);

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
