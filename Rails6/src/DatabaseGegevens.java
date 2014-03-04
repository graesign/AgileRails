
import java.sql.*;
	
public class DatabaseGegevens{
	
	public static void getData(){
			
		String 		driver		=	"com.mysql.jdbc.Driver";
		
		/* url: JDBC URL; parts one and two are supplied by your driver, 
		 * and the third part specifies your data source. */
//		String		url		=	"jdbc:mysql://localhost/railscab";
                String		url		=	"jdbc:mysql:144.76.76.182/c3school";
		
		// userid: your login name or user name
		String 		userid		=	"c3school";
		
		// password: your password for the DBMS
		String 		password	=	"lGqdxVW4dUJKl";
		
		Connection	conn		=	null;
		Statement	stmt		=	null;
		ResultSet	rs              =	null;
			
		try{
			// Load driver
			Class.forName( driver );
				
			// Class.forName( "the class name supplied with your driver" )
			System.out.println( "Driver has been loaded." );
			
		} catch( java.lang.ClassNotFoundException e ) {
			System.err.print( "ClassNotFoundException: " );
			System.err.println( e.getMessage() );
		}
		
		try{
			// Make connection with the DB
			conn		=	DriverManager.getConnection( url ,userid ,password );
			// Create statement
			stmt		=	conn.createStatement();
			// Create resultset
			rs			=	stmt.executeQuery( "SELECT * from Rails" );
				
			if ( rs != null ){
				while ( rs.next() ) {
					
					// Get all Integer values from resultset
					int id			=	rs.getInt( "id");
					int vertrek		=	rs.getInt( "vertrek" );
					int bestemming	=	rs.getInt( "bestemming" );
					int passagiers	=	rs.getInt( "passagiers" );
					int uren		=	rs.getInt( "uren" );
					int minuten		=	rs.getInt( "minuten" );
					
					// Print all values
					System.out.println("-----------------------------------------------------------------------");
					System.out.println( "id = " + id + ", " + "vertrek = " + vertrek +  ", " + "bestemming = " 
							+ bestemming +  ", " + "passagiers = " + passagiers + ", " + "tijdstip = " + uren + ":" + minuten + "u" );
				}
			}
			
			// Close SQL statement
			stmt.close();
			stmt			=	null;
			
			// Close connection with the DB
			conn.close();
			conn			=	null;
			
		} catch( SQLException ex1 ) {
			System.err.println( "SQLException: " + "1: " + ex1.getMessage() );
			//ex1.printStackTrace();
		}
			
		/* close any JDBC instances that weren't explicitly closed
		 * during normal code path, so we don't 'leak' resources. */
		if ( stmt != null ) {
			 try{
				stmt.close();
					
			 } catch( SQLException ex2 ) {
				 System.err.println( "SQLException: " + "2:" + ex2.getMessage() );
			 }
		}
		
		if ( conn != null ){
			try{
				conn.close();
				
			} catch( SQLException ex3 ){
				System.err.println( "SQLException: " + "3: " + ex3.getMessage() );
			}
		}
	}
}
