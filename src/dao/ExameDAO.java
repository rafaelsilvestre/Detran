package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	
	public void save(Pergunta pergunta, Usuario usuario, Exame exame) throws SQLException{
		PreparedStatement ps = null;
	    
		String sql = "INSERT INTO EXAME (ID_USUARIO, DATA_REALIZADA, TEMPO) VALUES (?, ?, ?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setInt(1, usuario.getId());
			 ps.setDate(2, (Date) exame.getDate());
			 ps.setString(3, exame.getTempo());
			 ps.execute();
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		 } finally {
			 ps.close();
	     }
	}
}
