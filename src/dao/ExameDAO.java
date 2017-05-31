package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import ConnectionFactory.ConnectionFactory;
import model.Exame;
import model.Opcao;
import model.Pergunta;
import model.Usuario;
import util.FXUtil;

public class ExameDAO {
	private Connection connection;
	
	public ExameDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public Exame save(ArrayList<Pergunta> perguntas, Usuario usuario, Exame exame) throws SQLException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int idExame = 0;
		double nota = 0.0;
		java.util.Date dataUtil = new java.util.Date();
		java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
		
		for(int i = 0; i < perguntas.size(); i++){
			Pergunta pergunta = perguntas.get(i);
			for(int x = 0; x < pergunta.getOpcoes().size(); x++){
				Opcao opcao = pergunta.getOpcoes().get(x);
				if(opcao.getId() == pergunta.getSelected()){
					if(opcao.isVerdadeiro())
						++nota;
				}
			}
		}
	    
		String sql = "INSERT INTO EXAME (ID_USUARIO, DATA_REALIZADA, TEMPO, NOTA) VALUES (?, ?, ?, ?) RETURNING ID_EXAME";
		String sqlRespostas = "INSERT INTO SELECAO (ID_OPCAO, ID_EXAME) VALUES (?, ?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setInt(1, usuario.getId());
			 ps.setDate(2, dataSql);
			 ps.setString(3, exame.getTempo());
			 ps.setDouble(4, nota);
			 ResultSet rs =  ps.executeQuery();
			 while(rs.next()){
				 idExame = rs.getInt("id_exame");
			 }
			 exame.setId(idExame);
			 for(int i = 0; i < perguntas.size(); i++){
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
		 return exame;
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
	
	public Exame getExame(Exame exameSelected) throws SQLException{
		Exame exame = new Exame();
		String sql = "SELECT * FROM EXAME WHERE ID_EXAME = ?";
		PreparedStatement ps = this.connection.prepareStatement(sql);
		ps.setInt(1, exameSelected.getId());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			exame.setId(rs.getInt("id_exame"));
			exame.setTempo(rs.getString("tempo"));
			exame.setNota(rs.getDouble("nota"));
		}
		
		return exame;
	}
	
	public ArrayList<Pergunta> getPerguntasExame(Exame exame) throws SQLException{
		ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PERGUNTA.TITULO, PERGUNTA.ID_PERGUNTA, SELECAO.ID_OPCAO FROM SELECAO ");
		sql.append("INNER JOIN OPCAO ON OPCAO.ID_OPCAO = SELECAO.ID_OPCAO ");
		sql.append("INNER JOIN PERGUNTA ON PERGUNTA.ID_PERGUNTA = OPCAO.ID_PERGUNTA ");
		sql.append("WHERE SELECAO.ID_EXAME = ?");
		
		PreparedStatement ps = this.connection.prepareStatement(sql.toString());
		ps.setInt(1, exame.getId());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			Pergunta pergunta = new Pergunta();
			pergunta.setId(rs.getInt("id_pergunta"));
			pergunta.setTitle(rs.getString("titulo"));
			pergunta.setSelected(rs.getInt("id_opcao"));
			
			
			String sql2 = "SELECT * FROM OPCAO WHERE ID_PERGUNTA = ? LIMIT 4";
			
			PreparedStatement ps2 = this.connection.prepareStatement(sql2);
			ps2.setInt(1, pergunta.getId());
			ResultSet rs2 = ps2.executeQuery();
			 
			while(rs2.next()){	
				Opcao opcao = new Opcao();
				opcao.setId(rs2.getInt(1));
				opcao.setTitle(rs2.getString("titulo"));
				opcao.setVeracidade(rs2.getBoolean("veracidade"));
				pergunta.setOpcoes(opcao);
			}
			perguntas.add(pergunta);
		}
		return perguntas;
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
