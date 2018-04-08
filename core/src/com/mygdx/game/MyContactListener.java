package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
    private boolean playeronground;
    private Array<Body> bodiesToRemove;
    private boolean playerDead;
    private int numFootContacts;

    MyContactListener(){
        super();
        bodiesToRemove = new Array<Body>();
    }
    public void beginContact(Contact contact){
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
       if(fa.getUserData()!=null&&fa.getUserData().equals("foot")){
           numFootContacts++;//sets true when foot fixture is in contact with the ground

        }
        if(fb.getUserData()!=null&&fb.getUserData().equals("foot")){
            numFootContacts++;// sets true when foot fixture is in contact with the ground(setting both because box2d picks randomly)

        }
        if(fa.getUserData() != null && fa.getUserData().equals("coins")) {
            System.out.println("touch");
            bodiesToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("coins")) {
            bodiesToRemove.add(fb.getBody());
        }
        if(fa.getUserData() != null && fa.getUserData().equals("spike")) {
            playerDead = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("spike")) {
            playerDead = true;
        }


    }


    public void endContact(Contact contact){
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa.getUserData()!=null&&fa.getUserData().equals("foot")){
            numFootContacts--;//sets false when player is in air

        }
        if(fb.getUserData()!=null&&fb.getUserData().equals("foot")){
            numFootContacts--;

        }
    }
    public boolean isPlayeronground(){
        return numFootContacts>0;
    }
    public Array<Body> getBodies() { return bodiesToRemove; }
    public boolean isPlayerDead(){return playerDead;}
    public void preSolve(Contact c,Manifold manifold){}//not used, declared because implemented contactlistner
    public void postSolve(Contact c, ContactImpulse v){}//not used, declared because implemented contactlistner
}
