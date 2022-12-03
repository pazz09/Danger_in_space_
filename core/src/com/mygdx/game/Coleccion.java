//falta implementar
package com.mygdx.game;

import java.util.*;

public class Coleccion {

	
	private ArrayList<asteroid> lista;
	
	public Coleccion() {
		this.lista = new ArrayList<>();
	}
	
	public void add(asteroid newAsteroid) {
		this.lista.add(newAsteroid);
	}
	
	public asteroid get(int i) {
		return this.lista.get(i);
	}
	
	public int size() {
		return this.lista.size();
	}
	
	public void remove(int i) {
		this.lista.remove(i);
	}
	
	
}
