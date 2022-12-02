
// colecciones
//balas destruye jugador
//modificaciones varias de movimiento UwU
//Singleton
//Template method
//Strategy
//Builder


package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides;
	private int velXEnemys; 
	private int velYEnemys; 
	private int cantAsteroides;
	private int cantEnemys;
	private long tiempoDisparo;
	private Texture texture;
	private Nave4 nave;
	private  ArrayList<asteroid> balls1 = new ArrayList<>();
	private  ArrayList<asteroid> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private  ArrayList<gatosEnemigos> enemys1 = new ArrayList<>();
	private  ArrayList<gatosEnemigos> enemys2 = new ArrayList<>();


	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides,int velXEnemys, int velYEnemys,
			int cantAsteroides,int cantEnemys, long tiempoDisparo) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXEnemys=velXEnemys;
		this.velYEnemys=velYEnemys;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		this.cantEnemys=cantEnemys;
		this.tiempoDisparo = tiempoDisparo;
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		texture = new Texture(Gdx.files.internal("fondoG.jpg"));
		
		
		
		//inicializar assets; musica de fondo y efectos de sonido
		
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("cantina-band.mp3")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("naveJugador.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("bala.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("guau.mp3"))); 
        nave.setVidas(vidas);
        nave.setTiempoDisparoMax(tiempoDisparo);
        //asteroide pequeño
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        asteroid bb = new asteroid(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("roca3.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	    
	   
	    
	    
	    //crea enemigo
	    Random e = new Random();
	    for (int i = 0; i < cantEnemys; i++) {
	        gatosEnemigos bb = new gatosEnemigos(e.nextInt((int)Gdx.graphics.getWidth()),
	  	            150+e.nextInt((int)Gdx.graphics.getHeight()-80),
	  	            50+e.nextInt(10), velXEnemys+r.nextInt(4), velYEnemys+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("enemy1.png")),new Texture(Gdx.files.internal("balaE.png")));	   
	  	    enemys1.add(bb);
	  	    enemys2.add(bb);
	  	}
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" NIVEL: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "PUNTAJE:"+this.score, Gdx.graphics.getWidth()-210, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          batch.begin();
          game.getBatch().draw(texture, 0, 0, texture.getWidth(), texture.getHeight());
          
          
		  dibujaEncabezado();
	      if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < enemys1.size(); j++) {    
		              if (b.checkCollision(enemys1.get(j))) {          
		            	 explosionSound.play();
		            	 enemys1.remove(j);
		            	 enemys2.remove(j);
		            	 j--;
		            	 score +=20;
		              }   	  
		  	        }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (asteroid ball : balls1) {
		          ball.update();
		      }
		      for (gatosEnemigos enmy3 : enemys1) {
			          enmy3.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	asteroid ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          asteroid asteroid = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(asteroid);
		     
		          }
		        }
		      }
		      for (int i=0;i<enemys1.size();i++) {
			    	gatosEnemigos enll1 = enemys1.get(i);   
			        for (int j=0;j<enemys2.size();j++) {
			          gatosEnemigos enll2 = enemys2.get(j); 
			          if (i<j) {
			        	  enll1.checkCollision(enll2);
			     
			          }
			        }
			      }
	      }
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < balls1.size(); i++) {
	    	    asteroid b=balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      for (int i = 0; i < enemys1.size(); i++) {
	    	    gatosEnemigos b=enemys1.get(i);
	    	    b.draw(batch,this);
		          //perdió vida o game over
	              if (nave.checkCollisione(b)) {
		            //asteroide se destruye con el choque             
	            	 enemys1.remove(i);
	            	 enemys2.remove(i);
	            	 i--;
            }   	  
	        }
	      if (nave.estaDestruido()) {
  			if (score > game.getHighScore())
  				game.setHighScore(score);
	    	Screen ss = new PantallaGameOver(game);
  			ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      batch.end();
	      //nivel completado
	      if (balls1.size() == 0 && enemys1.size() == 0) {
	    	
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas()+1, score, 
					velXAsteroides+1, velYAsteroides+1,velXEnemys+2,velYEnemys+2, 
					cantAsteroides+3,cantEnemys+2,tiempoDisparo-40);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
   
}
