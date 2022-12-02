package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Bullet extends enemigos implements Enemybuilder{

	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	private boolean Nave;
	    
	    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx, boolean nave) {
	    	spr = new Sprite(tx);
	    	spr.setPosition(x, y);
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        this.Nave=nave;
	    }
	    public void update() {
	        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
	        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
	            destroyed = true;
	        }
	        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
	        	destroyed = true;
	        }
	        
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	   
	    public boolean checkCollision(asteroid asteroid) {
	        if(spr.getBoundingRectangle().overlaps(asteroid.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean checkCollision(gatosEnemigos b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    public boolean checkCollision(Nave4 b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.getArea())&& Nave==false){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    public boolean isDestroyed() {return destroyed;}
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
