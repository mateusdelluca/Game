package com.mygdx.game.images;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Animations implements Serializable {



    MINI_BLOCK(new Animator(7,7,7f,500,500,"block/Block_tr.png")),

    //boy
    BOY_DEAD(new Animator(1,1,1,128,128,"boy/Dead.png")),
    BOY_RELOADING(new Animator(3,3,1,128,128, "boy/Reloading.png")),
    BOY_JETPACK(new Animator(4,4,14,128,128, "boy/JetPack.png")),
    BOY_WALKING(new Animator(6,6, 5, 128, 128, "boy/Walking.png")),
    BOY_IDLE(new Animator(1,1, 1, 128, 128, "boy/Idle.png")),
    BOY_SABER(new Animator(4,4, 15, 128, 128, "boy/saber_Sheet.png")),
    BOY_STRICKEN(new Animator(2,2, 3, 128, 128, "boy/Stricken.png")),
    BOY_JUMPING(new Animator(1,1, 1, 128, 128, "boy/Jumping3.png")),
    BOY_JUMPING_FRONT(new Animator(1,1, 1, 128, 128, "boy/Jumping.png")),
    BOY_PUNCHING(new Animator(3,3, 30, 128, 128, "boy/Punching2.png")),
    BOY_SHOOTING_AND_WALKING(new Animator(6,6, 5, 128, 128, "boy/Shooting3.png")),
    //monster
    MONSTER1_WALKING(new Animator(4,4,1,94,128, "monster/Walking.png")),
    MONSTER1_FLICKERING(new Animator(4,4,3,94,128, "monster/Flickering.png")),
    MONSTER1_SPLIT(new Animator(3,3,6,128,128, "monster/Split.png"));




    @Getter
    public Animator animator;

    Animations(Animator animator){
        this.animator = animator;
    }

    public static Animations getAnimation(String name){
        return valueOf(name);
    }
}
