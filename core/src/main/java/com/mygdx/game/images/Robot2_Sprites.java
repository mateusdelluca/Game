package com.mygdx.game.images;

public class Robot2_Sprites {

    public static final int WIDTH = 128, HEIGHT = 128;

    public Animator walking = new Animator(4,4,4, WIDTH, HEIGHT,"robots/Robot_Walking.png");
    public Animator idle = new Animator(1,1,1, WIDTH, HEIGHT,"robots/Robot_Idle.png");
    public Animator currentAnimation = idle;

    public String nameOfAnimation = "idle";


    public void changeAnimation(String name){
        nameOfAnimation = name;
        switch(nameOfAnimation) {
            case "walking":{
                currentAnimation = walking;
                break;
            }
//            case "ROBOT2_FLICKERING":{
//                currentAnimation = flickering;
//                break;
//            }
//            case "ROBOT2_SPLIT":{
//                currentAnimation = split;
//                break;
//            }
            case "idle": {
                currentAnimation = idle;
                break;
            }
        }
    }

}
