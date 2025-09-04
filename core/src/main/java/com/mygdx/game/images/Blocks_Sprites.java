package com.mygdx.game.images;

public class Blocks_Sprites {

    private final Animator mini_block = new Animator(7,7,7f,500,500,"block/Block_tr.png");

    public Animator currentAnimation = mini_block;

    public String nameOfAnimation = "MINI_BLOCK";


    public void changeAnimation(String name){
        nameOfAnimation = name;
        switch(nameOfAnimation) {
            case "MINI_BLOCK":{
                currentAnimation = mini_block;
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
                currentAnimation = mini_block;
                break;
            }
        }
    }

}


