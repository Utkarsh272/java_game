package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends Box2d_sprites {
    int control_switch=2;
    private int numCoins;
    private int totalCoins;


    public Player(Body body) {

        super(body);
        //texture_1();


            setAnimation(texture_1(), 1 / 12f);


    }
    public TextureRegion[] texture_1(){
        Texture tex = jgame.res.getTexture("run_right");
        TextureRegion[] sprites = TextureRegion.split(tex, 40, 40)[0];
        return sprites;
    }



    public void collectCoin() { numCoins++; }
    public int getNumCrystals() { return numCoins; }
    public void setTotalCrystals(int i) { totalCoins = i; }
    public int getTotalCyrstals() { return totalCoins; }
}
