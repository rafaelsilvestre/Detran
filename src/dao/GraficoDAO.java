package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;

public class GraficoDAO {
	private Connection connection;
	
	public GraficoDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public String getProvasRealizadas() throws SQLException{
		String str = null;
		
		String sql = "SELECT COUNT(*) AS QUANTIDADE FROM EXAME";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			str = rs.getString("quantidade");
		}
		return str;
	}
	
	
	public int getAprovados() throws SQLException{
		int i = 0;
		
		String sql = "SELECT COUNT(*) AS QUANTIDADE FROM EXAME WHERE NOTA > 7";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			i = rs.getInt("quantidade");
		}
		return i;
	}
	
	public int getReprovados() throws SQLException{
		int i = 0;
		
		String sql = "SELECT COUNT(*) AS QUANTIDADE FROM EXAME WHERE NOTA < 7";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			i = rs.getInt("quantidade");
		}
		return i;
	}
	
	
}
