package jdbc.hsqldb.RS.ForwardOnly.TryUpdate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	static Connection conn = null;
	static Statement stmnt = null;
	static ResultSet result = null;
	static Properties config;
	
public static void main(String[] args) {
		try {
			getPropiedades();
			conectar();
			operateDB();
			desconectar();	
	} catch (SQLException e) {
		System.out.println(e.getErrorCode());
		System.out.println(e.getSQLState());
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("fichero de configuracion no existe o esta mal formado");
		e.printStackTrace();
	}
	finally {
		try { result.close();	} catch (SQLException e) {e.printStackTrace();	}	
		try { stmnt.close();	} catch (SQLException e) {e.printStackTrace();	}	
		try { conn.close();	} catch (SQLException e) {e.printStackTrace();	}	
	}
}
	
	private static void getPropiedades() throws IOException {
		config = new Properties();
		FileInputStream fis = new FileInputStream("config.properties");
		config.load(fis);
}

	public static void conectar() throws SQLException {
			conn = DriverManager.getConnection(config.getProperty("URL"), config.getProperty("USERNAME"), config.getProperty("PASSWORD"));
	} // conectar()
	
	public static void desconectar() throws SQLException {
			conn.close();
	}
	public static void operateDB () throws SQLException {
		String queryTable_SQL = "SELECT ID, MARCA, MATRICULA FROM TVEHICULOS";
	   // Create statement
		stmnt = conn.createStatement();
		// execute SQL statement
		result = stmnt.executeQuery(queryTable_SQL);
		process_Result();
	} //método
   

	private static void process_Result() {
		int x;
		try {
			while (result.next()) {
				x = result.getInt(1);
				result.updateInt(1, x+1); 
			}
			} catch (SQLException e) { 
				e.printStackTrace();
			}
	}
}




