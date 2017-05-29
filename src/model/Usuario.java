package model;

import java.util.ArrayList;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
	private ArrayList<Exame> exames = new ArrayList<Exame>();
	
	public Usuario(int id){
		this.id = id;
	}
	
	public Usuario(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public ArrayList<Exame> getExames() {
		return exames;
	}
	public void setExames(Exame exames) {
		this.exames.add(exames);
	}
}
