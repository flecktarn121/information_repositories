import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class Experiment {
	private Properties prop;
	public static void main(String[] args) {
		new Experiment();

	}
	
	public Experiment() {
		try {
			readConfig();
		} catch (IOException e) {
			System.err.println("There has been a problem reading the config file.");
		}
		
		try {
			Connection con = DriverManager.getConnection(prop.getProperty("URL"), prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));
			Connection con2ElectricBoongalooo = DriverManager.getConnection(prop.getProperty("URL"), prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));
			String queryResult = "SELECT * FROM TablaResultSet ";
			String queryRows = "SELECT * FROM TablaRowSet ";
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement st2ElectricBoongaloo = con2ElectricBoongalooo.createStatement(RowSet.TYPE_SCROLL_SENSITIVE, RowSet.CONCUR_UPDATABLE);
			ResultSet rs =st.executeQuery(queryResult);
			ResultSet somier = st2ElectricBoongaloo.executeQuery(queryRows);
			JdbcRowSet rws = new JdbcRowSetImpl(somier);
			System.out.println("******ORIGINAL CONTENT OF THE TABLE*******");
			printResult(rs);
			printResult(rws);
			
			System.out.println("******CHANGING ROW  ONE, COL3 TO 0*******");
//			rs.absolute(1);
//			rs.updateInt("COL3", 0);
//			rs.updateRow();
//			rs.moveToCurrentRow();
//			rws.absolute(1);
//			rws.updateInt("COL3", 0);
//			rws.updateRow();
//			rws.moveToCurrentRow();
//			printResult(rs);
//			printResult(rws);
			
			System.out.println("******INSERTING A NEW ROW*******");
			rs.moveToInsertRow();
			rs.updateInt("col1", 41);
			rs.updateInt("col2", 42);
			rs.updateInt("col3", 43);
			rs.updateRow();
			rs.moveToCurrentRow();
			rws.moveToInsertRow();
			rws.updateInt("col1", 41);
			rws.updateInt("col2", 42);
			rws.updateInt("col3", 43);
			rws.updateRow();
			rws.moveToCurrentRow();
			printResult(rs);
			printResult(rws);
			
			System.out.println("******INSERT A NEW ROW VIA SQLDEVELOPER*******");
			pressAnyKey();
			printResult(rs);
			printResult(rws);
			
			System.out.println("******DELETING ROW*******");
			rs.first();
			rs.deleteRow();
			rs.updateRow();
			rs.moveToCurrentRow();
			rws.first();
			rws.deleteRow();
			rws.updateRow();
			rws.moveToCurrentRow();
			printResult(rs);
			printResult(rws);
			
			System.out.println("******DELETE a ROW VIA SQLDEVELOPER*******");
			pressAnyKey();
			printResult(rs);
			printResult(rws);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readConfig() throws IOException {
		prop = new Properties();
		try {
		prop.load(new FileInputStream("config.properties"));
		}catch(FileNotFoundException e) {
			System.err.println("The config file was not found.");
			System.exit(-1);
		}
	}
	
	private void printResult(ResultSet r) {
		try {
			r.beforeFirst();

			while (r.next())
				System.out.println("col1 = " + r.getInt(1) + " col2 = " + r.getInt(2) + " col3 = " + r.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void pressAnyKey() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

}
