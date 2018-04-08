package com.mygdx.game;

public class game_input {
    public static  boolean[] keys;
    public static boolean[] previous_keys;

    public static final int tot_keys = 3;
    public static final int button1 = 0;
    public static final int button2 = 1;
    public static final int button3 = 2;

    static {
        keys = new boolean[tot_keys];
        previous_keys = new boolean[tot_keys];

    }
    public static void update(){
        for(int i=0;i<tot_keys;i++){
            previous_keys[i]=keys[i];
        }
    }
    public static void setKey(int i,boolean b){
        keys[i] = b;
    }
    public static boolean isdown(int i){ // keeps firing key presses as long as key is pressed
        return keys[i];
    }
    public static boolean ispressed(int i){  // fires key pressed only once
        return keys[i]&&!previous_keys[i];

    }

}
