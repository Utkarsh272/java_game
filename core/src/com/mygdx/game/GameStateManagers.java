package com.mygdx.game;

import java.util.Stack;

public class GameStateManagers {
    private jgame game;
    private Stack<gamestate> gameStates;
    public static final  int PLAY = 12121;
    public static final int MENU =13231;
    public static final int END =  122143;


    public GameStateManagers(jgame game){
        this.game=game;
        gameStates = new Stack<gamestate>();
        pushState(PLAY);

    }


    public jgame game(){
        return game;
    }


    public void update(float dt){
        gameStates.peek().update(dt);
    }


        public void render(){
            gameStates.peek().render();
        }


        private gamestate getState(int state){
            if(state == MENU)
                return new Menu(this);

            if(state == PLAY)
                return new Play(this);

            return null;
        }


        public void setState(int state){
            popState();
            pushState(state);
        }


        public void pushState(int state){
            gameStates.push(getState(state));
        }


        public void popState(){
            gamestate g = gameStates.pop();
            g.dispose();
        }
        public void dispose(){

        }
        public void handleInput(){

        }

}
