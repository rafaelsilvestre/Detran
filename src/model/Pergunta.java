package model;

import java.util.ArrayList;

public class Pergunta {
	private int id;
	private String title;
	private int selected;
	private String image;
	private ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
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
	public ArrayList<Opcao> getOpcoes() {
		return opcoes;
	}
	public void setOpcoes(Opcao opcoes) {
		this.opcoes.add(opcoes);
	}
}
