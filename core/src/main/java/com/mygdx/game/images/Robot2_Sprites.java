package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Robot2_Sprites {

    public static final int WIDTH = 160, HEIGHT = 160;

    public Animator walking = new Animator(6,6,3, WIDTH, HEIGHT,"robots/robot3/walking.png");
    public Animator beenHit = new Animator(6,6,8, WIDTH, HEIGHT,"robots/robot3/takingPunch.png");
    public Animator punching = new Animator(6,6,10, WIDTH, HEIGHT,"robots/robot3/punching.png");
    public Animator fire = new Animator(5,5,5, WIDTH, HEIGHT,"robots/robot3/fire.png");

    public Animator falling = new Animator(5,5,1, WIDTH, HEIGHT,"robots/robot3/falling.png");
    public Animator idle = new Animator(1,1,1, WIDTH, HEIGHT,"robots/robot3/idle.png");

    public Animator currentAnimation = idle;

    public String currentAnim = "idle", oldAnim = "idle";

    float stateTime;
    public void changeAnimation(String name){
        if (!(oldAnim.equals(name))){
            oldAnim = name;
            currentAnim = name;
        }
        switch(currentAnim) {
            case "walking":{
                currentAnimation = walking;
                break;
            }
            case "punching":{
                currentAnimation = punching;
                break;
            }
            case "beenHit":{
                currentAnimation = beenHit;
                break;
            }
            case "fire":{
                currentAnimation = fire;
                break;
            }
            case "falling":{
                currentAnimation = falling;
                break;
            }
//            case "idle": {
//                currentAnimation = idle;
//                break;
//            }
            default:{
                currentAnimation = idle;
                break;
            }
        }

    }

    public void update(){
        if (isFinished()) {
            resetStateTime();
            //TODO: currentAnim = "idle"
            currentAnim = "punching";
            changeAnimation(currentAnim);
        }
    }

    private boolean isFinished(){
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation.setStateTime(stateTime);
        return currentAnimation.getAnimation().isAnimationFinished(stateTime);
    }

    private void resetStateTime(){
        stateTime = 0f;
        currentAnimation.setStateTime(stateTime);
    }

    public Sprite currentFrame(boolean useOnlyLastFrame, boolean looping, boolean facingRight){
       Sprite s = new Sprite(currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
       s.setScale(1.5f);
       return s;
    }

}
