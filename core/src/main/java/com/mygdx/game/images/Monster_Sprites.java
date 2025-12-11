package com.mygdx.game.images;

public class Monster_Sprites {

    public Animator walking = new Animator(4,4,1,94,128, "monster/Walking.png");
    public Animator flickering = new Animator(4,4,1,94,128, "monster/Flickering.png");
    public Animator attacking = new Animator(3,3,9,256,128, "monster/Attacking.png");
    public Animator split = new Animator(3,3,6,128,128, "monster/Split.png");

    //MONSTER2
    public Animator monster2 = new Animator(7,7,7,320,375, "monster/Monster200.png");


    //MONSTER3
    public Animator monster3 = new Animator(15,1,15,250,223, "monster/Monster3-Sheet.png");


    //MONSTER4
    public Animator monster4 = new Animator(8,8,8,212,260, "monster/Monster200.png");




    public Animator currentAnimation = walking;

    public String nameOfAnimation = "MONSTER1_WALKING";
    public boolean changed;

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
                case "MONSTER1_ATTACKING":{
                    currentAnimation = attacking;
                    break;
                }
                case "MONSTER1_SPLIT":{
                    currentAnimation = split;
                    break;
                }
                default:{
                    currentAnimation = monster3;
                    break;
                }
        }
    }


}
