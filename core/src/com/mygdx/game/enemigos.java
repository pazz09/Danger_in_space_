package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class enemigos {
	public abstract void update();
    
    public abstract Rectangle getArea();

	public abstract int getXSpeed();
	public abstract void setXSpeed(int xSpeed);
	public abstract int getySpeed();
	public abstract void setySpeed(int ySpeed);
}
