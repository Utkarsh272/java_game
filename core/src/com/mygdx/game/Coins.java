package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Coins extends Box2d_sprites {
    public Coins(Body body) {

        super(body);

        Texture tex = jgame.res.getTexture("coins");
        TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];
        animation.setFrames(sprites, 1 / 30f);

        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();

    }
}
