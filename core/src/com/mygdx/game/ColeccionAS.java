//falta implementar
package com.mygdx.game;

import java.util.*;

public class ColeccionAS implements Coleccionbuilder {

	
	private ArrayList<asteroid> lista;
	
	public ColeccionAS() {
		this.lista = new ArrayList<>();
	}
	
	public void add(asteroid newAsteroid) {
		this.lista.add(newAsteroid);
	}
	
	public asteroid get(int i) {
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
