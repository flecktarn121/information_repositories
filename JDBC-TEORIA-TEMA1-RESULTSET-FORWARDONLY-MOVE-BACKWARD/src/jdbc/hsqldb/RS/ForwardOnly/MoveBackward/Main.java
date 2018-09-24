package jdbc.hsqldb.RS.ForwardOnly.MoveBackward;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	static String URL = "jdbc:hsqldb:hsql://localhost";
	static String USERNAME = "sa";
	static String PASSWORD = "";

	static Connection conn = null;
	static Statement stmnt = null;
	static ResultSet result = null;

	
	public static void main(String[] args) {
		try {
			conectar();
			operateDB();
			desconectar();	
	} catch (SQLException e) {
		System.out.println(e.getErrorCode());
		System.out.println(e.getSQLState());
		e.printStackTrace();
	}
	finally {
		try { result.close();	} catch (SQLException e) {e.printStackTrace();	}	
		try { stmnt.close();	} catch (SQLException e) {e.printStackTrace();	}	
		try { conn.close();	} catch (SQLException e) {e.printStackTrace();	}	
	}
}
	
	public static void conectar() throws SQLException {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	} // conectar()
	
	public static void desconectar() throws SQLException {
			conn.close();
	}

	public static void operateDB () throws SQLException {
		String queryTable_SQL = "SELECT * FROM TVEHICULOS ";

		   // Create statement
			stmnt = conn.createStatement();
			// execute SQL statement
			result = stmnt.executeQuery(queryTable_SQL);
			process_Result(result);
			// close statement
			stmnt.close();
	} //método
   

	private static void process_Result(ResultSet r) throws SQLException {
			while (r.next()) 
				System.out.println("id = " + r.getInt(1)+ " col2 = " + r.getString(2) + "\n");
			while (r.previous())
				System.out.println("id = " + r.getInt(1)+ " col2 = " + r.getString(2) + "\n");
			}
}