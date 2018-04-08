package com.mygdx.game;
import static com.mygdx.game.Box2dVars.BIT_COIN;
import static  com.mygdx.game.Box2dVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.io.*;
//import com.badlogic.gdx.assets.*;


public class Play extends gamestate {
    SpriteBatch batch;
    BitmapFont font;

    private World world;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera b2dCam;

    //  private Body playerBody;
    private MyContactListener contactListener;

    private Player player;
    private Array<Coins> coins;
    private Array<Spikes> spikes;

    private TiledMap tileMap;
    private float tileSize;
    private OrthogonalTiledMapRenderer tmr;
    private int high_score=0;
    private int counter=0;
    private String text;

    public Play(GameStateManagers gsm) {

        super(gsm);
        batch=new SpriteBatch();
        font=new BitmapFont(Gdx.files.internal("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\text.fnt"));
        BufferedReader bw= null;
        try {
            bw = new BufferedReader(new FileReader("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\highscore.txt"));
            String s;
            while((s=bw.readLine())!=null){
                String[] str=s.split(" ");
                high_score=Integer.parseInt(str[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        world = new World(new Vector2(0, -120f), true);
        contactListener = new MyContactListener();
        world.setContactListener(contactListener);

        b2dr = new Box2DDebugRenderer();
        createPlayer();


//cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, game.width / PPM, game.height / PPM);

        ///////////////////////////////////////////
        tileMap = new TmxMapLoader().load("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\mapy_yo.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);


        //createTiles();

        createCoins();
        createSpikes();
        buildCollision(tileMap, world);// makes the tiles in the map staticobjects

    }

    public void handleInput() {

        if (game_input.ispressed(game_input.button1)) {
            if (contactListener.isPlayeronground()) {

                // System.out.println(Box2d_sprites.control_switch);
                player.getBody().applyForceToCenter(0, 5000, true); // applies n newtons of force to one kg body
            }
        }
        if (game_input.isdown(game_input.button2)) {
            player.control_switch = 1;
            player.getBody().applyForceToCenter(100, 0, true);
        }
        if (game_input.isdown(game_input.button3)) {
            player.control_switch = 2;
            player.getBody().applyForceToCenter(-100, 0, true);
        }
    }

    public void update(float dt) {
        handleInput();
        world.step(dt, 6, 2);
        Array<Body> bodies = contactListener.getBodies();
        for (int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            coins.removeValue((Coins) b.getUserData(), true);
            world.destroyBody(bodies.get(i));
            player.collectCoin();
             jgame.res.getSound("coin_sound").play();
             counter++;
        }
        bodies.clear();
        player.update(dt);
        if(contactListener.isPlayerDead()) {
          //  jgame.res.getSound("hit").play();
            //gsm.setState(GameStateManager.MENU);

            System.out.println("dead");
            try {
                String s="";
                BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\highscore.txt"));
            while((s=br.readLine())!=null)
            {
                String[] str=s.split(" ");
                int a=Integer.parseInt(str[0]);
                if(counter>a)
                {
                    BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\highscore.txt"));
                    String write=counter+" ";
                    bw.write(write);
                    bw.close();
                    high_score=counter;
                }
                else high_score=a;
            }

            br.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            counter=0;

        }

        for (int i = 0; i < coins.size; i++) {
            coins.get(i).update(dt);
        }


    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.set(player.getBody().getPosition().x * PPM + jgame.width / 4, player.getBody().getPosition().y * PPM + jgame.height / 4, 0);
        cam.update();
        tmr.setView(cam);
        tmr.render();
        sb.setProjectionMatrix(cam.combined);
        player.render(sb);
        for (int i = 0; i < coins.size; i++) {
            coins.get(i).render(sb);
        }
        // b2dr.render(world,b2dCam.combined);
        cam.position.set(player.getBody().getPosition().x * PPM + jgame.width / 4, player.getBody().getPosition().y * PPM, 0);
        cam.update();
        batch.begin();
        text="High Score : "+high_score+"\nDiamonds Collected : "+counter;
        font.draw(batch,text,cam.viewportHeight/1.5f+cam.viewportWidth/5,cam.viewportWidth/1.5f+cam.viewportWidth/13.5f);
        batch.end();
    }

    public void dispose() {

    }

    public void createPlayer() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

// box dynamic(player)
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(160 / PPM, 200 / PPM);
        Body body = world.createBody(bdef);

        shape.setAsBox(13 / PPM, 13 / PPM);
        fdef.shape = shape;
        fdef.friction = 0.5f;

        fdef.filter.categoryBits = Box2dVars.BIT_PLAYER;
        //fdef.filter.maskBits = Box2dVars.BIT_GROUND | Box2dVars.BIT_COIN | Box2dVars.BIT_SPIKE;

        body.createFixture(fdef).setUserData("player");


//player sensor
        shape.setAsBox(10 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = Box2dVars.BIT_PLAYER;
        fdef.isSensor = true;// will make this a sensor
        //fdef.filter.maskBits = Box2dVars.BIT_GROUND;
        body.createFixture(fdef).setUserData("foot");
        // create player
        player = new Player(body);
    }


    private static void buildCollision(TiledMap map, World world) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("dirt");
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        fDef.density = 2.5f;
        fDef.isSensor = false;
        fDef.friction = 0.02f;
        fDef.restitution = 0;
        fDef.filter.categoryBits = Box2dVars.BIT_GROUND;
        fDef.filter.maskBits = Box2dVars.BIT_PLAYER;

        bDef.type = BodyType.StaticBody;
        Body tileBody = world.createBody(bDef);
        tileBody.setUserData("ground");
        int cellcounter = 0;
        TiledMapTileLayer.Cell cell, nextCell, prevCell;
        int firstCellIndexX = 0, firstCellIndexY = 0;

        for (int j = 0; j < layer.getHeight(); j++) {
            for (int i = 0; i < layer.getWidth(); i++) {
                cell = layer.getCell(i, j);
                prevCell = layer.getCell(i - 1, j);
                nextCell = layer.getCell(i + 1, j);

                if (cell != null) {
                    cellcounter++;


                    if (prevCell == null) {
                        firstCellIndexX = i;
                        firstCellIndexY = j;
                    }

                    if (nextCell == null) {
                        float width = layer.getTileWidth() * cellcounter / PPM;
                        float height = layer.getTileHeight() / PPM;

                        shape.setAsBox(width / 2, height / 2, new Vector2((firstCellIndexX * layer.getTileWidth() / PPM) + (width / 2), (firstCellIndexY * layer.getTileHeight() / PPM) + (height / 2)), 0);
                        fDef.shape = shape;
                        tileBody.createFixture(fDef);
                        cellcounter = 0;
                    }
                }
            }
        }
        shape.dispose();

    }

    private void createTiles() {

        // load tile map
        tileMap = new TmxMapLoader().load("C:\\Users\\Shubham Mittal\\Desktop\\java_game\\core\\assets\\mapy_yo.tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        tileSize = (Integer) tileMap.getProperties().get("tilewidth");

        TiledMapTileLayer layer;

        layer = (TiledMapTileLayer) tileMap.getLayers().get("dirt");
        createLayer(layer, Box2dVars.BIT_GROUND);


    }

    private void createLayer(TiledMapTileLayer layer, short bits) {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        // go through all the cells in the layer
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {

                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);

                // check if cell exists
                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                // create a body + fixture from cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set(
                        (col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM
                );

                PolygonShape cs = new PolygonShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(
                        -tileSize / 2 / PPM, -tileSize / 2 / PPM);
                v[1] = new Vector2(
                        -tileSize / 2 / PPM, tileSize / 2 / PPM);
                v[2] = new Vector2(
                        tileSize / 2 / PPM, tileSize / 2 / PPM);
                cs.set(v);
                cs.setAsBox(tileSize / (2 * PPM), tileSize / (2 * PPM));
                fdef.friction = 0;
                fdef.shape = cs;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = Box2dVars.BIT_PLAYER;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef);


            }
        }
    }

    public void createCoins() {
        // create list of crystals
        coins = new Array<Coins>();

        // get all crystals in "crystals" layer,
        // create bodies for each, and add them
        // to the crystals list
        MapLayer ml = tileMap.getLayers().get("coins");
        if (ml == null) return;

        for (MapObject mo : ml.getObjects()) {
            BodyDef cdef = new BodyDef();
            cdef.type = BodyType.StaticBody;
            float x = (Float) mo.getProperties().get("x") / PPM;
            float y = (Float) mo.getProperties().get("y") / PPM;
            cdef.position.set(x, y);
            Body body = world.createBody(cdef);
            FixtureDef cfdef = new FixtureDef();
            CircleShape cshape = new CircleShape();
            cshape.setRadius(8 / PPM);
            cfdef.shape = cshape;
            cfdef.isSensor = true;
            cfdef.filter.categoryBits = Box2dVars.BIT_COIN;
            cfdef.filter.maskBits = Box2dVars.BIT_PLAYER;
            body.createFixture(cfdef).setUserData("coins");
            Coins c = new Coins(body);
            body.setUserData(c);
            coins.add(c);
            cshape.dispose();
        }
    }

    public void createSpikes() {

        spikes = new Array<Spikes>();

        MapLayer ml = tileMap.getLayers().get("spikes");
       // if (ml == null) return;

        for (MapObject mo : ml.getObjects()) {
            BodyDef cdef = new BodyDef();
            cdef.type = BodyType.StaticBody;
            float x = (Float) mo.getProperties().get("x") / PPM;
            float y = (Float) mo.getProperties().get("y") / PPM;
            cdef.position.set(x, y);
            Body body = world.createBody(cdef);
            FixtureDef cfdef = new FixtureDef();
            CircleShape cshape = new CircleShape();
            cshape.setRadius(5 / PPM);
            cfdef.shape = cshape;
            cfdef.isSensor = true;
            cfdef.filter.categoryBits = Box2dVars.BIT_SPIKE;
            cfdef.filter.maskBits = Box2dVars.BIT_PLAYER;
            body.createFixture(cfdef).setUserData("spike");
            Spikes s = new Spikes(body);
            body.setUserData(s);
            spikes.add(s);
            cshape.dispose();
        }


    }
}
