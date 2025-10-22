package com.mygdx.game.images;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Animations implements Serializable {

    BOY_DEAD(new Animator(1,1,1,128,128,"boy/Dead.png")),
    BOY_ATTACKING_SWORD(new Animator(5,5,15,128,128,"boy/Attacking_Sword2.png")),
    BOY_WALKING_SWORD(new Animator(6,6,5,128,128,"boy/Walking_Sword.png")),
    BOY_SWORD(new Animator(1,1,1,128,128,"boy/Sword.png")),
    BOY_RELOADING(new Animator(3,3,1,128,128, "boy/Reloading.png")),
    BOY_JETPACK(new Animator(4,4,14,128,128, "boy/JetPack.png")),
    BOY_WALKING(new Animator(6,6, 5, 128, 128, "boy/Walking.png")),
    BOY_IDLE(new Animator(1,1, 1, 128, 128, "boy/Idle.png")),
    BOY_SABER(new Animator(4,4, 15, 128, 128, "boy/saber_Sheet.png")),
    BOY_STRICKEN(new Animator(2,2, 1, 128, 128, "boy/Stricken.png")),
    BOY_JUMPING(new Animator(1,1, 1, 128, 128, "boy/Jumping3.png")),
    BOY_JUMPING_FRONT(new Animator(1,1, 1, 128, 128, "boy/Jumping.png")),
    BOY_PUNCHING(new Animator(3,3, 3, 128, 128, "boy/NewPunching.png")),
    BOY_PUNCHING_FIRE(new Animator(6,6, 12, 128, 128, "boy/Punching_Fire.png")),
    BOY_SHOOTING_AND_WALKING(new Animator(6,6, 5, 128, 128, "boy/Shooting3.png")),
    BOY_LVL_UP(new Animator(3,3,3,1, 128, 128, "boy/LevelUP.png")),
    BOY_HEADSET(new Animator(3,3,5,42, 50,"boy/Laser_HeadSet-Sheet3.png")),
    BOY_JUMPING_FRONT_LASER((new Animator(3,3,5,128, 128,"boy/Jumping_Headset_Sheet.png")));

    @Getter
    public Animator animator;

    Animations(Animator animator){
        this.animator = animator;
    }

    public static Animations getAnimation(String name){
        return valueOf(name);
    }
}
