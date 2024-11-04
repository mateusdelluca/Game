package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Color;

public enum Animations {


    //boy
    BOY_WALKING(new Animator(6,6, 5, 128, 128, "boy/Walking.png")),
    BOY_IDLE(new Animator(1,1, 1, 128, 128, "boy/Idle.png")),
    BOY_SABER(new Animator(3,3, 15, 128, 128, "boy/Saber.png")),
    BOY_STRICKEN(new Animator(2,2, 3, 128, 128, "boy/Stricken.png")),
    BOY_JUMPING(new Animator(1,1, 1, 128, 128, "boy/Jumping3.png")),
    BOY_JUMPING_FRONT(new Animator(1,1, 1, 128, 128, "boy/Jumping.png")),
    BOY_PUNCHING(new Animator(3,3, 30, 128, 128, "boy/Punching2.png")),
    BOY_SHOOTING_AND_WALKING(new Animator(6,6, 5, 128, 128, "boy/Shooting3.png")),
    //monster
    MONSTER1_WALKING(new Animator(4,4,2,94,128, "monster/Walking.png")),
    MONSTER1_FLICKERING(new Animator(4,4,6,94,128, "monster/Flickering.png")),
    MONSTER1_SPLIT(new Animator(3,3,3,128,128, "monster/Split.png")),
    //player
    FIRST(new Animator(10,10, 20, 400, 300,"stickman/First.png")),
    WALKING(new Animator(30, 10, 20, 400, 300, "stickman/Stickman_walking.png")),
    IDLE(new Animator( 26, 10, 10, 400, 300, "stickman/Idle.png")),
    PUNCH(new Animator(6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    JUMPING(new Animator(10, 10, 20, 400, 300, "stickman/Stickman_Jumping.png")),
    HIYAH(new Animator(14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    PUNCHED(new Animator(5, 5, 10, 400, 300, "stickman/Stickman_punched.png")),
    SHOT(new Animator(6, 6, 5, 400, 300, "stickman/Shot.png")),
    HITED(new Animator(1, 1, 1, 400, 300, "stickman/Stickman_baseball_hited.png")),
    IDLE_FLASH(new Animator(0.1f, 26, 10, 5, 400, 300, "stickman/Idle3.png")),
    SABER(new Animator(6,6,12,400,300, "stickman/SabreHit.png")),

    //enemy
    E_WALKING(new Animator(Color.RED,30, 10, 12, 400, 300, "stickman/Stickman_walking.png")),
    E_IDLE(new Animator(Color.RED, 26, 10, 2, 400, 300, "stickman/Idle.png")),
    E_SPLIT(new Animator(Color.RED, 4, 4, 3, 400, 300, "stickman/Split.png")),
    E_PUNCH(new Animator(Color.RED,6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    E_JUMPING(new Animator(Color.RED,10, 10, 5, 400, 300, "stickman/Stickman_Jumping.png")),
    E_HIYAH(new Animator(Color.RED,14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    E_PUNCHED(new Animator(Color.RED, 5, 5, 25, 400, 300, "stickman/Stickman_punched.png")),
    E_HITED(new Animator(Color.RED,1, 1, 1, 400, 300, "stickman/Stickman_baseball_hited.png"));

    public Animator animator;

    Animations(Animator animator){
        this.animator = animator;
    }

    public Animator getAnimator() {
        return animator;
    }

    public Animations getAnimation(String name){
        return valueOf(name);
    }
}
