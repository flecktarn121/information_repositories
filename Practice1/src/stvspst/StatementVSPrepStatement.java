package stvspst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementVSPrepStatement {
	private static final int N = 100;
	private static final String QUERY = "SELECT * FROM TFacturas";
	private String user;
	private String password;
	private String url;

	public static void main(String args[]) {
		new StatementVSPrepStatement();
	}
	
	public StatementVSPrepStatement() {
		performExperiment();
	}
	
	private void readConfig() throws IOException {
		File config = new File("config");
		try {
			BufferedReader content = new BufferedReader(new FileReader(config));
			try {
				url = content.readLine();
				user = content.readLine();
				password = content.readLine();
			} finally {
				content.close();
			}
		} catch (FileNotFoundException e) {
		}
	}

	public Connection getConnection() throws SQLException {
		try {
			this.readConfig();
		} catch (IOException e) {
			System.err.println("There has been a problem reading the config file.");
		}
		return DriverManager.getConnection(url, user, password);
	}

	public void performExperiment() {
		Connection con = null;
		try {
			con = this.getConnection();
		} catch (SQLException e) {
			System.err.println("There has been a problem stablishing the connection.");
			try {
				con.close();
			} catch (SQLException e1) {
			}
		}
		try {
			experimentWithStatement(con);
			experimentWithPrepStatement(con);
		} catch (SQLException e) {
			System.err.println("There has been a prblem executing the query.");
			try {
				con.close();
			} catch (SQLException e1) {
			}
		}
	}

	private void experimentWithPrepStatement(Connection con) throws SQLException {
		long t1 = System.currentTimeMillis();
		String query = QUERY;
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(query);
			for (int i = 0; i < N; i++) {
				pst.executeQuery(query);
			}
		} catch (SQLException exc) {
			System.out.println(exc.getErrorCode());
			System.out.println(exc.getSQLState());
			throw exc;
		} finally {
			pst.close();
		}
		long t2 = System.currentTimeMillis();
		System.out.println("The prepared statement took " + (t2 - t1));
	}

	private void experimentWithStatement(Connection con) throws SQLException {
		long t1 = System.currentTimeMillis();
		String query = QUERY;
		Statement st = null;
		try {
			st = con.createStatement();
			for (int i = 0; i < N; i++) {
				st.executeQuery(query);
			}
		} catch (SQLException exc) {
			throw exc;
		} finally {
			st.close();
		}
		long t2 = System.currentTimeMillis();
		System.out.println("THe statement took " + (t2 - t1));

	}

}
