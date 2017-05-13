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
	
	public ArrayList<Opcao> getOpcoes(Pergunta pergunta) throws SQLException{
		ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT * FROM OPCAO WHERE ID_PERGUNTA = ?";
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
}
