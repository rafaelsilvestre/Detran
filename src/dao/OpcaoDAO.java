package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectionFactory.ConnectionFactory;
import model.Opcao;
import model.Pergunta;
import util.FXUtil;

public class OpcaoDAO {
	private Connection connection;
	
	public OpcaoDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void save(Opcao opcao, Pergunta pergunta) throws SQLException{
		PreparedStatement ps = null;
	    
		String sql = "INSERT INTO OPCAO (TITULO, ID_PERGUNTA, VERACIDADE) VALUES (?, ?, ?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setString(1, opcao.getTitle());
			 ps.setInt(2, pergunta.getId());
			 ps.setBoolean(3, opcao.isVerdadeiro());
			 ps.execute();
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		 } finally {
			 ps.close();
	     }
	}
	
	public ArrayList<Opcao> getOpcoes(Pergunta pergunta) throws SQLException{
		ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT * FROM OPCAO WHERE ID_PERGUNTA = ? ORDER BY RANDOM()";
		try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setInt(1, pergunta.getId());
			 rs = ps.executeQuery();
			 while(rs.next()){
				 Opcao opcao = new Opcao();
				 opcao.setId(rs.getInt("id_opcao"));
				 opcao.setTitle(rs.getString("titulo"));
				 opcao.setVeracidade(rs.getBoolean("veracidade"));
				 opcoes.add(opcao);
			 }
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		} finally {
			 ps.close();
			 rs.close();
		}
		return opcoes;
	}
	
	public ArrayList<Opcao> getOpcoesGabarito(Pergunta pergunta) throws SQLException{
		ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT * FROM OPCAO WHERE ID_PERGUNTA = ? LIMIT 4";
		try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setInt(1, pergunta.getId());
			 rs = ps.executeQuery();
			 int count = 0;
			 while(rs.next()){
				 count++;
				 
				 if(count > 4)
					 break;
				 
				 Opcao opcao = new Opcao();
				 opcao.setId(rs.getInt(1));
				 opcao.setTitle(rs.getString("titulo"));
				 opcao.setVeracidade(rs.getBoolean("veracidade"));
				 opcoes.add(opcao);
			 }
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		} finally {
			 ps.close();
			 rs.close();
		}
		return opcoes;
	}
}
