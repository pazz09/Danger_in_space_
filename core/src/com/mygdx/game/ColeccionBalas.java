package com.mygdx.game;

import java.util.ArrayList;

public class ColeccionBalas {

private ArrayList<Bullet> lista;

	
	public ColeccionBalas() {
		this.lista = new ArrayList<>();
	}
	
	public boolean add(Bullet a) {
		this.lista.add(a);
		return true;
	}
	
	public Bullet get(int i) {
		return this.lista.get(i);
	}

	
	public int size() {
		
		return this.lista.size();
	}

	
	public void remove(Bullet i) {
		
		this.lista.remove(i);
	}

}
