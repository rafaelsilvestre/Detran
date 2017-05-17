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

public class PerguntaDAO {

	private Connection connection;
	private OpcaoDAO opcaoDAO;
	
	public PerguntaDAO(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void save(Pergunta pergunta) throws SQLException{
		PreparedStatement ps = null;
	    
		String sql = "INSERT INTO PERGUNTA (TITULO) VALUES (?)";
		 try {
			 ps = this.connection.prepareStatement(sql);
			 ps.setString(1, pergunta.getTitle());
			 ps.execute();
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		 } finally {
			 ps.close();
	     }
	}
	
	public ArrayList<Pergunta> getPerguntas() throws SQLException{
		ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
		
	    String sql = "SELECT * FROM PERGUNTA ORDER BY RANDOM() LIMIT 10";
		try {
			 ps = this.connection.prepareStatement(sql);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 Pergunta pergunta = new Pergunta();
				 ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
				 
				 pergunta.setId(rs.getInt("id_pergunta"));
				 pergunta.setTitle(rs.getString("titulo"));
				 
				 opcoes = new OpcaoDAO().getOpcoes(pergunta);
				 for(int i = 0; i < opcoes.size(); i++){
					 pergunta.setOpcoes(opcoes.get(i));
				 }
				 
				 perguntas.add(pergunta);
			 }
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 FXUtil.mensagem("Problema...", "Erro ao inserir pergunta", e.getMessage());
		} finally {
			 ps.close();
			 rs.close();
		}
		return perguntas;
	}
}
