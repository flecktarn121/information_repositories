package conn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConInVSConOut {
	private static final int N = 100;
	private static final String QUERY = "SELECT * FROM TFacturas";
	private String user;
	private String password;
	private String url;

	public static void main(String args[]) {
		new ConInVSConOut();
	}

	public ConInVSConOut() {
		try {
			readConfig();
		} catch (IOException e) {
			System.err.println("There has been a problem reading the file.");
			System.exit(-1);
		}
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
		performExperimentWithConnection();
		performExperimentWithoutConnection();
	}

	private void performExperimentWithConnection() {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			try (Connection con = DriverManager.getConnection(url, user, password)) {
				con.createStatement().executeQuery(QUERY);
			} catch (SQLException exc) {
				System.err.println("Something went wrong with the connection.");
				exc.printStackTrace();
				System.exit(-1);
			}
		}
		long t2 = System.currentTimeMillis();

		System.out.println("With the connection inside, the experiment took " + (t2 - t1) + " ms.");

	}

	private void performExperimentWithoutConnection() {
		long t1 = System.currentTimeMillis();
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			for (int i = 0; i < 100; i++) {
				con.createStatement().executeQuery(QUERY);
			}
		} catch (SQLException exc) {
			System.err.println("Something went wrong with the connection.");
			System.exit(-1);
		}
		long t2 = System.currentTimeMillis();

		System.out.println("With the connection inside, the experiment took " + (t2 - t1) + " ms.");
	}

}
