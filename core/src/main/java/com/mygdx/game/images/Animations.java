package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

@Getter
public enum Animations {


    //boy
    BOY_RECHARGING(new Animator(3,3,1,128,128, "boy/Recharging.png")),
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
    MONSTER1_FLICKERING(new Animator(4,4,6,94,128, "monster/Flickering.png")),
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
