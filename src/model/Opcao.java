package model;

public class Opcao {
	private int id;
	private String title;
	private boolean veracidade;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isVerdadeiro() {
		return veracidade;
	}
	public void setVeracidade(boolean veracidade) {
		this.veracidade = veracidade;
	}
}
