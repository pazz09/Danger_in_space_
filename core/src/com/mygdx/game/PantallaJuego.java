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
	private int velXAsteroides1; 
	private int velYAsteroides1;
	private int velXEnemy; 
	private int velYEnemy; 
	private int velXAsteroides2;
	private int velYAsteroides2;

	private int cantAsteroides1;
	private int cantEnemy;
	private int cantAsteroides2;
	private Texture texture;
	
	private Nave4 nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private  ArrayList<enemy> enemy1 = new ArrayList<>();
	private  ArrayList<enemy> enemy2 = new ArrayList<>();
	private  ArrayList<Ball3> balls3 = new ArrayList<>();
	private  ArrayList<Ball3> balls4 = new ArrayList<>();


	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides1, int velYAsteroides1,int velXEnemy, int velYEnemy, int velXAsteroides2, int velYAsteroides2,
			int cantAsteroides1,int cantAsteroides2,int cantEnemy) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXEnemy=velXEnemy;
		this.velYEnemy=velYEnemy;
		this.velXAsteroides1 = velXAsteroides1;
		this.velYAsteroides1 = velYAsteroides1;
		this.velXAsteroides2 = velXAsteroides2;
		this.velYAsteroides2 = velYAsteroides2;
		this.cantAsteroides1 = cantAsteroides1;
		this.cantAsteroides2 = cantAsteroides2;
		this.cantEnemy=cantEnemy;
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		
		
		
		
		//inicializar assets; musica de fondo y efectos de sonido
		
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("star-wars-clone-army-theme.mp3")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("naveJugador.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("bala.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("guau.mp3"))); 
        nave.setVidas(vidas);
        //asteroide pequeño
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides1; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides1+r.nextInt(4), velYAsteroides1+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("roca3.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	    
	    //asteroide grande
	    Random a = new Random();
	    for (int j = 0; j < cantAsteroides2; j++) {
	        Ball3 aa = new Ball3(a.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+a.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+a.nextInt(10), velXAsteroides2+a.nextInt(4), velYAsteroides2+a.nextInt(4), 
	  	            new Texture(Gdx.files.internal("roca3.png")));	   
	  	    balls3.add(aa);
	  	    balls4.add(aa);
	  	}
	    
	    
	    //crea enemigo
	    Random e = new Random();
	    for (int i = 0; i < cantEnemy; i++) {
	        enemy bb = new enemy(e.nextInt((int)Gdx.graphics.getWidth()),
	  	            150+e.nextInt((int)Gdx.graphics.getHeight()-80),
	  	            50+e.nextInt(10), velXEnemy+r.nextInt(4), velYEnemy+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("enemy1.png")),new Texture(Gdx.files.internal("bala.png")));	   
	  	    enemy1.add(bb);
	  	    enemy2.add(bb);
	  	}
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" NIVEL: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "	PUNTAJE:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          batch.begin();
          
          
          
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
	    	  for(int i = 0; i )
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < enemy1.size(); j++) {    
		              if (b.checkCollision(enemy1.get(j))) {          
		            	 explosionSound.play();
		            	 enemy1.remove(j);
		            	 enemy2.remove(j);
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
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : balls1) {
		          ball.update();
		      }
		      for (enemy enmy3 : enemy1) {
			          enmy3.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      }
		      for (int i=0;i<enemy1.size();i++) {
			    	enemy enll1 = enemy1.get(i);   
			        for (int j=0;j<enemy2.size();j++) {
			          enemy enll2 = enemy2.get(j); 
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
	    	    Ball2 b=balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      for (int i = 0; i < enemy1.size(); i++) {
	    	    enemy b=enemy1.get(i);
	    	    b.draw(batch,this);
		          //perdió vida o game over
	              if (nave.checkCollisione(b)) {
		            //asteroide se destruye con el choque             
	            	 enemy1.remove(i);
	            	 enemy2.remove(i);
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
	      if (balls1.size() == 0 && enemy1.size() == 0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas()+1, score, 
					velXAsteroides+1, velYAsteroides+1,velXEnemy+2,velYEnemy+2, 
					cantAsteroides+3,cantEnemy+2);
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
