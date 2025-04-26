package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

import static com.mygdx.game.Bodies.Builder.box;
import static com.mygdx.game.images.Animations.MINI_BLOCK;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public class Stand extends Objeto{

    private Body bodyA;
    private Body bodyB;
    private Joint jointBodiesAB;

    public Stand(Body playerBody){
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        bodyA = box(new Vector2(300f, 6000 - 200f), new Vector2(70, 20), BodyDef.BodyType.DynamicBody, false, "bodyA");
        bodyB = box(new Vector2(bodyA.getPosition().x + 100, bodyA.getWorldCenter().y + 100), new Vector2(50, 10), BodyDef.BodyType.StaticBody);
        bodyA.setUserData(bodyA);
        distanceJointDef.initialize(bodyA, bodyB, bodyA.getPosition(), bodyB.getWorldCenter());
        distanceJointDef.length = 250f;
        distanceJointDef.collideConnected = true;
        distanceJointDef.dampingRatio = 0.05f;
        distanceJointDef.frequencyHz = 4.5f;
        jointBodiesAB = world.createJoint(distanceJointDef);
        jointBodiesAB.setUserData("joint");
    }

    public void update(){

    }

    @Override
    public void render(SpriteBatch s) {
        Sprite sprite = new Sprite(MINI_BLOCK.animator.currentFrame(false));
        sprite.setSize(600, 150);
        sprite.setOriginCenter();
        sprite.setPosition(bodyA.getPosition().x + 320f - 600f, bodyA.getPosition().y - 70);
        sprite.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }
}
