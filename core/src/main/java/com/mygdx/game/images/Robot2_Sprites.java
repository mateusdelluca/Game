package com.mygdx.game.images;

public class Robot2_Sprites {

    public Animator walking = new Animator(4,4,2,128, 128,"robots/robot2/Robot_Walking.png");

    public Animator currentAnimation = walking;

    public String nameOfAnimation = "ROBOT2_WALKING";


    public void changeAnimation(String name){
        nameOfAnimation = name;
        switch(nameOfAnimation) {
            case "ROBOT2_WALKING":{
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
            default: {
                currentAnimation = walking;
                break;
            }
        }
    }

}
