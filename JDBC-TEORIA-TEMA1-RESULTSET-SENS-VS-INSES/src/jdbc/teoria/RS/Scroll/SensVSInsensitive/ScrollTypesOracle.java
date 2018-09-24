/**
 * https://download.oracle.com/otn_hosted_doc/jdeveloper/905/jdbc-javadoc/oracle/jdbc/OracleDatabaseMetaData.html
 */
package jdbc.teoria.RS.Scroll.SensVSInsensitive;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleDatabaseMetaData;

public class ScrollTypesOracle {

	public static void main(String[] args) throws IOException {
		
		Properties config;
		
		String queryTable_SQL = "SELECT col1, col2, col3 FROM PRUEBA where col1=11 or col2 = 22 or col3 = 33";
		
		config = new Properties();
		FileInputStream fis = new FileInputStream("config.properties");
		config.load(fis);
		Connection connS = null, connI = null;
		Statement stmntS = null, stmntI = null;
		ResultSet resultS = null, resultI = null;
		
		
			try {
				connS = DriverManager.getConnection(config.getProperty("URL"), config.getProperty("USERNAME"), config.getProperty("PASSWORD"));
				OracleDatabaseMetaData metaS = (OracleDatabaseMetaData) connS.getMetaData();
				stmntS = connS.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				resultS = stmntS.executeQuery(queryTable_SQL);
				int typeS = resultS.getType();

				connI = DriverManager.getConnection(config.getProperty("URL"), config.getProperty("USERNAME"), config.getProperty("PASSWORD"));
				stmntI = connI.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				resultI = stmntI.executeQuery(queryTable_SQL);
				OracleDatabaseMetaData metaI = (OracleDatabaseMetaData) connS.getMetaData();
				int typeI = resultI.getType();
				
				showMessage("TYPE_SCROLL_INSENSITIVE");
				System.out.println(" Own Updates are visible? "+ metaI.ownUpdatesAreVisible(typeI));
				System.out.println(" Own Deletes are visible? "+ metaI.ownDeletesAreVisible(typeI));
				System.out.println(" Own Inserts are visible? "+ metaI.ownInsertsAreVisible(typeI));

				System.out.println(" Others Updates are visible? "+ metaI.othersUpdatesAreVisible(typeI));
				System.out.println(" Others Deletes are visible? "+ metaI.othersDeletesAreVisible(typeI));
				System.out.println(" Others Inserts are visible? "+ metaI.othersInsertsAreVisible(typeI));

				System.out.println(" Updates are detected? "+ metaI.updatesAreDetected(typeI));
				System.out.println(" Deletes are detected? "+ metaI.deletesAreDetected(typeI));
				System.out.println(" Inserts are detected? "+ metaI.insertsAreDetected(typeI));

				showMessage("TYPE_SCROLL_SENSITIVE");
				System.out.println(" Own Updates are visible? "+ metaS.ownUpdatesAreVisible(typeS));
				System.out.println(" Own Deletes are visible? "+ metaS.ownDeletesAreVisible(typeS));
				System.out.println(" Own Inserts are visible? "+ metaS.ownInsertsAreVisible(typeS));

				System.out.println(" Others Updates are visible? "+ metaS.othersUpdatesAreVisible(typeS));
				System.out.println(" Others Deletes are visible? "+ metaS.othersDeletesAreVisible(typeS));
				System.out.println(" Others Inserts are visible? "+ metaS.othersInsertsAreVisible(typeS));

				System.out.println(" Updates are detected? "+ metaS.updatesAreDetected(typeS));
				System.out.println(" Deletes are detected? "+ metaS.deletesAreDetected(typeS));
				System.out.println(" Inserts are detected? "+ metaS.insertsAreDetected(typeS));

				pressAnyKey();

				/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
				 * 1.- Imprimo el resultado con ambos 
				 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
				System.out.println("Query SELECT col1, col2, col3 FROM PRUEBA where col1=11 or col2 = 22 or col3 = 33\n");
			

				showMessage("TYPE_SCROLL_SENSITIVE");
				printResult(resultS);
				
				showMessage("TYPE_SCROLL_INSENSITIVE");
				printResult(resultI);

				
				/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
				 * 2.- Imprimo el resultado hacia atrás  
				 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
				System.out.println("\nSHOWING RESULTS BACKWARD\n");
				
				showMessage("TYPE_SCROLL_SENSITIVE");
				printResultBackward(resultS);
				
				showMessage("TYPE_SCROLL_INSENSITIVE");
				printResultBackward(resultI);

				pressAnyKey();
				
				/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
				 * 3.- Cambio una col del result set 
				 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
				System.out.println("\n CHANGING A COLUMN IN A CERTAIN ROW");

				showMessage("TYPE_SCROLL_INSENSITIVE");
				updateCol(resultI, 2, 3, 55);
				printResult(resultI);
				
				showMessage("TYPE_SCROLL_SENSITIVE");
				updateCol(resultS, 1, 3, 55);				
				printResult(resultS);

				
				/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
				 * 4.- Cambio la tabla desde sqldeveloper. Pongo la col 2 de la 
				 *     fila 3 con el valor 00
				 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */		
		        System.out.println("\n USING SQLDEVELOPER, CHANGE  COL2, ROW 3 TO 0");

				
		        pressAnyKey();
				
				showMessage("TYPE_SCROLL_INSENSITIVE");
				printResult(resultI);
				showMessage("TYPE_SCROLL_SENSITIVE");
				printResult(resultS);

				/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
				 * 4.- Cambio la tabla desde sqldeveloper. Pongo la col 2 de la 
				 *     fila 2 con el valor 00 -> DEJA DE CUMPLIR EL WHERE
				 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */		
		        System.out.println("\n USING SQLDEVELOPER, CHANGE  COL2, ROW 2 TO 0");
		        pressAnyKey();
					

				showMessage("TYPE_SCROLL_INSENSITIVE");
				printResult(resultI);

				showMessage("TYPE_SCROLL_SENSITIVE");
				printResult(resultS);
					
					
			} catch (SQLException e) {
				e.printStackTrace();
			} // catch
			finally {
				try { stmntS.close();} catch (SQLException e) { e.printStackTrace();}
				try { connS.close();} catch (SQLException e) { e.printStackTrace();}
				try { resultS.close();} catch (SQLException e) { e.printStackTrace();}
				
				try { stmntI.close();} catch (SQLException e) { e.printStackTrace();}
				try { connI.close();} catch (SQLException e) { e.printStackTrace();}
				try { resultI.close();} catch (SQLException e) { e.printStackTrace();}
			}
	} // main


private static void showMessage(String string) {
	System.out.println("**********************************************");
	System.out.println("*          "+string+"             *");
	System.out.println("**********************************************");

	}

private static void pressAnyKey() {
	System.out.println("Press Enter key to continue...");
    try
    {
        System.in.read();
    }  
    catch(Exception e)
    {}  
}


private static void printResult(ResultSet r) {
	try {
		r.beforeFirst();

		while(r.next())
			System.out.println("col1 = " + r.getInt(1) + " col2 = " + r.getInt(2)  + " col3 = " + r.getInt(3));
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
}

private static void printResultBackward(ResultSet r) {
	try {
		r.afterLast();
		
		while(r.previous())
			System.out.println("col1 = " + r.getInt(1) + " col2 = " + r.getInt(2)+ " col3 = " + r.getInt(3) );
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}

private static void updateCol(ResultSet r, int row, int col, int value) throws SQLException {
	if (r.absolute(row) ) // si esa fila existe
	{	r.updateInt(col, value);
		r.updateRow();
	}

	}

}
