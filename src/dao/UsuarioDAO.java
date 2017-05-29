package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import model.Usuario;
import util.FXUtil;

public class UsuarioDAO {
	private Connection connection;
	private OpcaoDAO opcaoDAO;
	
	public UsuarioDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void save(Usuario usuario) throws SQLException{
		PreparedStatement ps = null;
	    
		String sql = "INSERT INTO USUARIO (NOME, EMAIL, TELEFONE) VALUES (?, ?, ?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setString(1, usuario.getNome());
			 ps.setString(2, usuario.getEmail());
			 ps.setString(3, usuario.getTelefone());
			 ps.execute();
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir Usuário", e.getMessage());
		 } finally {
			 ps.close();
	     }
	}
}
