package com.mygdx.game.images;

public class Monster1_Sprites {


    public Animator walking = new Animator(4,4,1,94,128, "monster/Walking.png");
    public Animator flickering = new Animator(4,4,3,94,128, "monster/Flickering.png");
    public Animator split = new Animator(3,3,6,128,128, "monster/Split.png");

    public Animator currentAnimation = walking;

    public String nameOfAnimation = "MONSTER1_WALKING";


    public void changeAnimation(String name){
        nameOfAnimation = name;
            switch(nameOfAnimation) {
                case "MONSTER1_WALKING":{
                     currentAnimation = walking;
                     break;
                }
                case "MONSTER1_FLICKERING":{
                    currentAnimation = flickering;
                    break;
                }
                case "MONSTER1_SPLIT":{
                    currentAnimation = split;
                    break;
                }
        }
    }


}
