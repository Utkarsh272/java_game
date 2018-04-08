package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class gamestate {
    protected GameStateManagers gsm;
    protected jgame game;
    protected SpriteBatch sb;
    protected OrthographicCamera cam;//follows player main
    protected OrthographicCamera camhud;//stays ate the origin


    protected gamestate(GameStateManagers gsm){
        this.gsm=gsm;
        game = gsm.game();
        sb= game.getBatch();
        cam=game.getCam();
        camhud = game.getHudcam();

    }
   public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
