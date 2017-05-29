package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ConnectionFactory.ConnectionFactory;
import model.Exame;
import model.Pergunta;
import model.Usuario;
import util.FXUtil;

public class ExameDAO {
	private Connection connection;
	
	public ExameDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void save(ArrayList<Pergunta> perguntas, Usuario usuario, Exame exame) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int idExame = 0;
		java.util.Date dataUtil = new java.util.Date();
		java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
	    
		String sql = "INSERT INTO EXAME (ID_USUARIO, DATA_REALIZADA, TEMPO) VALUES (?, ?, ?) RETURNING ID_EXAME";
		String sqlRespostas = "INSERT INTO SELECAO (ID_OPCAO, ID_EXAME) VALUES (?, ?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setInt(1, usuario.getId());
			 ps.setDate(2, dataSql);
			 ps.setString(3, exame.getTempo());
			 ResultSet rs =  ps.executeQuery();
			 while(rs.next()){
				 idExame = rs.getInt("id_exame");
			 }
			 
			 for(int i = 0; i < perguntas.size(); i++){
				 System.out.println(perguntas.get(i).getSelected());
				 ps1 = this.connection.prepareStatement(sqlRespostas);
				 ps1.setInt(1, perguntas.get(i).getSelected());
				 ps1.setInt(2, idExame);
				 ps1.execute();
			 }
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir Exame", e.getMessage());
		 } finally {
			 ps.close();
			 ps1.close();
	     }
	}
	
	public ArrayList<Exame> getExames() throws SQLException{
		ArrayList<Exame> exames = new ArrayList<Exame>();
		String sql = "SELECT * FROM EXAME";
		PreparedStatement ps = this.connection.prepareStatement(sql); 
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			Exame exame = new Exame();
			exame.setId(rs.getInt("id_exame"));
			exame.setDate(rs.getDate("data_realizada"));
			exame.setTempo(rs.getString("tempo"));
			exames.add(exame);
		}
		return exames;
	}
	
	public void deleteExame(Exame exame) throws SQLException{
		try{
			if(this.isSelecao(exame)){
				this.removeSelecao(exame);
			}			
			
			String sql = "DELETE FROM EXAME WHERE ID_EXAME = ?";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, exame.getId());
			ps.executeQuery();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public boolean isSelecao(Exame exame) throws SQLException{
		ArrayList<Integer> exames = new ArrayList<Integer>();
		String sql = "SELECT * FROM SELECAO WHERE ID_EXAME = ?";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, exame.getId());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
			exames.add(rs.getInt("id_selecao"));
		
		if(exames.size() > 0)
			return true;
		else
			return false;
	}
	
	public void removeSelecao(Exame exame) throws SQLException{
		String sqlSelecao = "DELETE FROM SELECAO WHERE ID_EXAME = ?";
		PreparedStatement ps1 = this.connection.prepareStatement(sqlSelecao);
		ps1.setInt(1, exame.getId());
		ps1.executeQuery();
	}
}
