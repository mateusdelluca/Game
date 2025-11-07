package com.mygdx.game.images;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Player_Animations implements Serializable {

    DEAD(new Animator(1,1,1,128,128,"boy/Dead.png")),
    ATTACKING_SWORD(new Animator(5,5,15,128,128,"boy/Attacking_Sword2.png")),
    ATTACKING_SWORD_FIRE(new Animator(7,7,14,128,128,"boy/Attacking_Sword_Fire.png")),
    ATTACKING_SWORD_FIRE_2(new Animator(12,12,12,192,128,"boy/Sword_Fire_2Hits_3.png")),
    WALKING_SWORD(new Animator(6,6,5,128,128,"boy/Walking_Sword.png")),
    SWORD(new Animator(1,1,1,128,128,"boy/Sword.png")),
    RELOADING(new Animator(3,3,1,128,128, "boy/Reloading.png")),
    JETPACK(new Animator(4,4,14,128,128, "boy/JetPack.png")),
    WALKING(new Animator(6,6, 5, 128, 128, "boy/Walking.png")),
    IDLE(new Animator(1,1, 1, 128, 128, "boy/Idle.png")),
    SABER(new Animator(4,4, 15, 128, 128, "boy/saber_Sheet.png")),
    STRICKEN(new Animator(2,2, 2, 128, 128, "boy/Stricken.png")),
    JUMPING(new Animator(1,1, 1, 128, 128, "boy/Jumping3.png")),
    JUMPING_FRONT(new Animator(1,1, 1, 128, 128, "boy/Jumping.png")),
    PUNCHING(new Animator(3,3, 3, 128, 128, "boy/NewPunching.png")),
    JUMPING_FRONT_LASER((new Animator(3,3,5,128, 128,"boy/Jumping_Headset_Sheet.png"))),
    PUNCHING_FIRE(new Animator(6,6, 12, 128, 128, "boy/Punching_Fire.png")),
    LEGS_ONLY(new Animator(6,6, 5, 128, 128, "boy/Shooting3.png")),
    LVL_UP(new Animator(3,3,5,1, 128, 128, "boy/LevelUP.png")),
    HEADSET(new Animator(3,3,5,42, 50,"boy/Laser_HeadSet-Sheet3.png")),
    NONE(new Animator(1,1,1,1, 1,"boy/Laser_HeadSet-Sheet3.png"));


    @Getter
    public Animator animator;
    public static Player_Animations currentAnimation = Player_Animations.IDLE;
    Player_Animations(Animator animator){
        this.animator = animator;
//        animator.stateTime2 = 0f;
    }

    public static Player_Animations getAnimation(String name){
        return valueOf(name);
    }

    public static void changeAnimation(String name){
        Animator.changedAnimation = true;
        currentAnimation = getAnimation(name);
    }

}
