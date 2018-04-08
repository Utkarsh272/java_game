package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import javafx.scene.layout.Background;

public class Menu extends gamestate{
   // private GameButton playButton;
    private Background bg;
    TextureRegion myTextureRegion;
    TextureRegionDrawable myTexRegionDrawable;
    ImageButton button;
    Stage stage;
    Sprite sprite;
    Texture tex;

    private World world;
    private Box2DDebugRenderer b2dRenderer;


    public Menu(GameStateManagers gsm)
    {
        super(gsm);

//        tex = new Texture("rollyboi.png");

        class MyActor extends Actor{
            Texture texture = new Texture("C:\\Users\\Rithvik Sallaram\\Documents\\java_game\\core\\assets\\rollyboi.png");

            @Override
            public void draw(Batch batch, float parentAlpha) {

                batch.draw(texture, cam.viewportWidth/2-30, cam.viewportHeight*(0.75f)-30);
            }
        }



//        sprite = new Sprite(tex);
//        sprite.setPosition(
//                Gdx.graphics.getWidth()/2 - sprite.getWidth()/2,
//                Gdx.graphics.getHeight()/2 - sprite.getHeight()/2
//        );


        tex = new Texture("C:\\Users\\Rithvik Sallaram\\Documents\\java_game\\core\\assets\\bg.jpg");
        bg = new Background(new TextureRegion(tex), cam, 1f);
//        bg = new Background();
        bg.setVector(-20, -20);





        tex = new Texture("C:\\Users\\Rithvik Sallaram\\Documents\\java_game\\core\\assets\\play.png");

        myTextureRegion = new TextureRegion(tex);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable); //Set the button up

//        button.setOrigin(cam.viewportWidth/2 -30, 20);
        button.setX(Gdx.graphics.getWidth()/2-20);
        button.setY(20);

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button);
        stage.addActor(new MyActor());
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui


//        playButton = new GameButton(new TextureRegion(tex, 0, 34, 58, 27), 160, 100, cam);

        cam.setToOrtho(false, jgame.width, jgame.height);

        world = new World(new Vector2(0, -9.8f * 5), true);

        b2dRenderer = new Box2DDebugRenderer();


    }


    @Override
    public void handleInput() {

        button.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(button.isPressed())
                {
                    gsm.setState(GameStateManagers.PLAY);
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void update(float dt) {

        handleInput();

        world.step(dt / 5, 8, 3);

        bg.update(dt);
//        animation.update(dt);


    }

    @Override
    public void render() {

        sb.setProjectionMatrix(cam.combined);

        // draw background
        bg.render(sb);

        // draw button
//        button.render(sb);

        // draw bunny
        sb.begin();
//        sb.draw(sprite, sprite.getX(), sprite.getY());
//        sb.draw(new Sprite(button), 146, 31);
        stage.draw();
        sb.end();


//        // debug draw box2d
//        if(debug) {
//            cam.setToOrtho(false, Box2DGame.V_WIDTH / ppm, Box2DGame.V_HEIGHT / ppm);
//            b2dRenderer.render(world, cam.combined);
//            cam.setToOrtho(false, Box2DGame.V_WIDTH, Box2DGame.V_HEIGHT);
//        }



    }

    @Override
    public void dispose() {

    }

}
