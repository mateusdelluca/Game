package com.mygdx.game.images;

public class Robot2_Sprites {

    public static final int WIDTH = 160, HEIGHT = 160;

    public Animator walking = new Animator(6,6,3, WIDTH, HEIGHT,"robots/robot3/walking.png");
    public Animator takingPunch = new Animator(6,6,8, WIDTH, HEIGHT,"robots/robot3/takingPunch.png");
    public Animator punching = new Animator(6,6,10, WIDTH, HEIGHT,"robots/robot3/punching.png");
    public Animator idle = new Animator(1,1,1, WIDTH, HEIGHT,"robots/robot3/idle.png");
    public Animator currentAnimation = idle;

    public String nameOfAnimation = "idle";


    public void changeAnimation(String name){
        nameOfAnimation = name;
        switch(nameOfAnimation) {
            case "walking":{
                currentAnimation = walking;
                break;
            }
            case "punching":{
                currentAnimation = punching;
                break;
            }
            case "takingPunch":{
                currentAnimation = takingPunch;
                break;
            }
            case "idle": {
                currentAnimation = idle;
                break;
            }
        }
    }

}
