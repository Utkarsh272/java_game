package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class inputprocess extends InputAdapter {
    public boolean keyDown(int k){
        if(k== Input.Keys.W){
            game_input.setKey(game_input.button1,true);
        }
        if(k== Input.Keys.D){
            game_input.setKey(game_input.button2,true);
        }
        if(k== Input.Keys.A){
            game_input.setKey(game_input.button3,true);
        }
        return true;
    }
    public boolean keyUp(int k){
        if(k== Input.Keys.W){
            game_input.setKey(game_input.button1,false);
        }
        if(k== Input.Keys.D){
            game_input.setKey(game_input.button2,false);
        }
        if(k== Input.Keys.A){
            game_input.setKey(game_input.button3,false);
        }
        return true;
    }
}
