package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;



public class Nave{
	
	private boolean destruida = false;
	private float velMax = 15;
	private float aceleracion = (float) 0.7;
	private float frenado = (float) 0.25;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private long tiempoInicial = 0, tiempoFinal;
    private long tiempoDisparoMax;
     
    
    
    
    public Nave(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 80, 80);
    	tiempoInicial = 0;
    	tiempoFinal = System.currentTimeMillis();

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x =  spr.getX();
        float y =  spr.getY();
        tiempoInicial += System.currentTimeMillis() - tiempoFinal;
        tiempoFinal = System.currentTimeMillis();
        if (!herido) {
	        // que se mueva con teclado
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	        	if (xVel > velMax*-1 && xVel<velMax) {
	        		xVel-=aceleracion;
	        	}
	        		
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	        	if (xVel > velMax*-1 && xVel<velMax) {
	        		xVel+=aceleracion;
	        	}
	        	
	        }
        	if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        		if (yVel > velMax*-1 && yVel<velMax) {
        			yVel-=aceleracion; 
	        	}
        		    
        	}
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	if (yVel > velMax*-1 && yVel<velMax) {
        			yVel+=aceleracion; 
	        	}
	        }
	        
	        //poner aqui si no funciona
	        if (xVel!=0 && xVel < 0) {
        		xVel = (float) (xVel + frenado);
        	}if (xVel!=0 && xVel > 0) {
        		xVel = (float) (xVel - frenado);
        	}if (yVel != 0 && yVel < 0){
    			yVel = (float) (yVel + frenado);
    		}if (yVel != 0 && yVel > 0) {
    			yVel = (float) (yVel - frenado);
    		}
	        
	        // que se mantenga dentro de los bordes de la ventana
	        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
	        	xVel*=-1;
	        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
	        	yVel*=-1;
	        
	        spr.setPosition(x+xVel, y+yVel);   
         
 		    spr.draw(batch);
        } else {
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		   spr.draw(batch); 
 		  spr.setX(x);
 		   tiempoHerido--;
 		   if (tiempoHerido<=0) herido = false;
 		 }
        // disparo
        
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && tiempoInicial > tiempoDisparoMax) {         
          Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala,true);
	      juego.agregarBala(bala);
	      soundBala.play();
	      tiempoInicial = 0;
        }
       
    }
      
    public boolean checkCollision(asteroid b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    
    public boolean checkCollisione(gatosEnemigos b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    public boolean checkCollisione(Bullet b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    public void setTiempoDisparoMax(long tiempoDisparoMax) {
    	this.tiempoDisparoMax = tiempoDisparoMax;
    }
    public long getTiempoDisparoMax() {
    	return tiempoDisparoMax;
    }
    
    public int getVidas() {return vidas;}
    public Rectangle getArea() {
		
		return spr.getBoundingRectangle();
	}
    
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
