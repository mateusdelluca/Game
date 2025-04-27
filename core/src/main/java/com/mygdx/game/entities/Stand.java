package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mygdx.game.system.BodyData;

import java.util.Random;

import static com.mygdx.game.Bodies.Builder.box;
import static com.mygdx.game.images.Animations.MINI_BLOCK;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public class Stand extends Objeto{

    private Body bodyA;
    private Body bodyB;
    private Joint jointBodiesAB;
    private boolean onlyFirstFrame, destroyed;
    private boolean visible0;
    private float stateTime;

    public static int index;

    public static final float DURATION_ANIMATION_IN_SECONDS = 0.5f;
    public Stand(float x, float y){
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        bodyA = box(new Vector2(x,y), new Vector2(70, 20), BodyDef.BodyType.DynamicBody, false, this.toString());
        bodyB = box(new Vector2(bodyA.getPosition().x + 100, bodyA.getWorldCenter().y + 100), new Vector2(50, 10), BodyDef.BodyType.StaticBody, true);
        bodyA.setUserData(this.toString());
        distanceJointDef.initialize(bodyA, bodyB, bodyA.getPosition(), bodyB.getWorldCenter());
        distanceJointDef.length = 250f;
        distanceJointDef.collideConnected = true;
        distanceJointDef.dampingRatio = new Random().nextFloat(0.9f);
        distanceJointDef.frequencyHz = new Random().nextFloat(4.9f);
        index++;
        jointBodiesAB = world.createJoint(distanceJointDef);
        jointBodiesAB.setUserData("joint");
        body = bodyA;
        body.setUserData(this.toString());
        bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        bodyData.userData = this.toString();
    }

    @Override
    public void beenHit(){
        onlyFirstFrame = false;
        destroyed = true;
    }

    public void update(){

    }

    @Override
    public void render(SpriteBatch s) {
        if (visible0) {
            Sprite sprite = new Sprite(MINI_BLOCK.animator.currentSpriteFrame(onlyFirstFrame, stateTime));
            sprite.setSize(600, 150);
            sprite.setOriginCenter();
            sprite.setPosition(bodyA.getPosition().x + 320f - 600f, bodyA.getPosition().y - 70);
            sprite.draw(s);
        }
        MINI_BLOCK.getAnimator().setStateTime(stateTime);
        this.visible0 = stateTime < DURATION_ANIMATION_IN_SECONDS;
        if (destroyed) {
            if (stateTime == 0f)
                world.destroyJoint(jointBodiesAB);
            stateTime += Gdx.graphics.getDeltaTime();
            if (!visible0)
                bodyA.setTransform(11_000, 11_000, 0);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + index;
    }
}
