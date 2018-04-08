package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class jgame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static final String title = "Game";
	public static final int width = 320,height = 240;
	public static final int scale = 2;
	public static final float STEP = 1/60f;
	private float accum;
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudcam;
	private GameStateManagers gsm;

	public static Content res;



	public SpriteBatch getBatch() {
		return sb;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public OrthographicCamera getHudcam() {
		return hudcam;
	}

	@Override
	public void create () {

		Gdx.input.setInputProcessor(new inputprocess());

		res = new Content();
		res.loadTexture("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\bally_nobag.png","run_right");
		res.loadTexture("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\crystal.png","coins");

		res.loadSound("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\coin_sound.wav","coin_sound");
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,width*2f,height*2f);
		gsm = new GameStateManagers(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.63f,0.9f,1f,1f);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while(accum>=STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			game_input.update();
		}


	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}
}
