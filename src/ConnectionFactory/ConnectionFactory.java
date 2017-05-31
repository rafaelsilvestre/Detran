package ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {
	 private final String DRIVER = "org.postgresql.Driver";
	    private final String URL = "jdbc:postgresql://127.0.0.1:5432/detran";
	    private final String USER = "postgres";
	    private final String PASSWORD = "b3002015";
	        
	    
	    public Connection getConnection(){
	        Connection con = null;
	        try {
	            Class.forName(DRIVER);
	            con = DriverManager.getConnection(URL, USER, PASSWORD);
	            //System.out.println("Conectado");
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            System.out.println("---------- ----------------");
	            e.printStackTrace();
	        }
	        return con;        
	    }
	    
	    public void closeConnection (Connection con, PreparedStatement ps, ResultSet rs){
	        try {
	            if(rs != null) rs.close();
	            if(ps != null) ps.close();
	            if(con != null) con.close();            
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            System.out.println("---------- ----------------");
	            e.printStackTrace();
	        }        
	    }
	    
	    public void closeConnection(Connection con, PreparedStatement ps){
	        closeConnection (con, ps, null);        
	    }
}
