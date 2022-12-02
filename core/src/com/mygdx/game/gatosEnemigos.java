package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class gatosEnemigos extends enemigos implements Enemybuilder {
	private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;
    private Texture txBala;

    public gatosEnemigos(int x, int y, int size, int xSpeed, int ySpeed, Texture tx, Texture txBala) {
    	spr = new Sprite(tx);
    	this.x = x; 
    	this.txBala = txBala;
 	
        //validar que borde de esfera no quede fuera
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        //validar que borde de esfera no quede fuera
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
        spr.setPosition(x, y);
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
    }
    public void draw(SpriteBatch batch,PantallaJuego juego) {
    	spr.draw(batch);
    	if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {         
            Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-50,0,-3,txBala);
            juego.agregarBala(bala);
  	      	
         }
    }
    
    public void checkCollision(gatosEnemigos b2) {
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
        	// rebote
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
        	setXSpeed(- getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            
            if (getySpeed() ==0) setySpeed(getySpeed() + b2.getySpeed()/2);
            if (b2.getySpeed() ==0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
            setySpeed(- getySpeed());
            b2.setySpeed(- b2.getySpeed()); 
        }
    }
	@Override
	public void update() {
		// TODO Auto-generated method stub
		x += getXSpeed();
        y += getySpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (y+getySpeed() < 0 || y+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
        	setySpeed(getySpeed() * -1);
        spr.setPosition(x, y);
	}

	@Override
	public Rectangle getArea() {
		// TODO Auto-generated method stub
		return spr.getBoundingRectangle();
	}

	
	

	@Override
	public int getXSpeed() {
		// TODO Auto-generated method stub
		return xSpeed;
	}

	@Override
	public void setXSpeed(int xSpeed) {
		// TODO Auto-generated method stub
		this.xSpeed = xSpeed;
	}

	@Override
	public int getySpeed() {
		// TODO Auto-generated method stub
		return ySpeed;
	}

	@Override
	public void setySpeed(int ySpeed) {
		// TODO Auto-generated method stub
		this.ySpeed = ySpeed;
	}

}
