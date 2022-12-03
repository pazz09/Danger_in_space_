package com.mygdx.game;

import java.util.ArrayList;

public class ColeccionGatos implements Coleccionbuilder{
	
	private ArrayList<gatosEnemigos> lista;

	
	public ColeccionGatos() {
		this.lista = new ArrayList<>();
	}
	
	public void add(gatosEnemigos a) {
		this.lista.add(a);
	}
	
	public gatosEnemigos get(int i) {
		return this.lista.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.lista.size();
	}

	@Override
	public void remove(int i) {
		// TODO Auto-generated method stub
		this.lista.remove(i);
	}
	
}
