package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.entities.Mouse.clicked;
import static com.mygdx.game.entities.Mouse.mouseBody;
import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.*;
import static com.mygdx.game.screens.levels.Level.world;

public class Rope extends Item {


    @Getter @Setter
    private Body bodyA;

    private Vector2 dimensions;

    private Sprite spriteA = new Sprite(Images.rope);
    @Getter @Setter
    private DistanceJoint jointAB;

    private Vector2 positionA;

    public static final float MULTIPLY = 1f;
    public static final int NUM_ROPES = 0;

    public static final float WIDTH = Images.rope.getWidth() * MULTIPLY, HEIGHT = Images.rope.getHeight() * MULTIPLY;
    private boolean collide;

    public Rope(Vector2 positionA, boolean firstRope){
        super(WIDTH, HEIGHT, new Vector2());
        this.dimensions = new Vector2(WIDTH/2f, HEIGHT/2f);
        this.positionA = positionA;
        bodyA = box(positionA, dimensions, firstRope ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody, false, this.toString());
//        positionB = new Vector2(this.positionA);
//        positionB.add(new Vector2(0f,50f));
//        bodyB = Builder.box(positionB, dimensions, BodyDef.BodyType.DynamicBody);
//        DistanceJointDef distanceJointDef = new DistanceJointDef();
//        distanceJointDef.initialize(bodyA, bodyB, bodyA.getWorldCenter(), bodyB.getWorldCenter());
//        jointAB = (DistanceJoint) world.createJoint(distanceJointDef);
    }

    @Override
    public void update(){
        if (collide && clicked) {
            bodyA.setTransform(mouseBody.getPosition(), bodyA.getAngle());
        }
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void setIndex(int index) {

    }

    @Override
    public void render(SpriteBatch s) {
        spriteA.setSize(width, height);
        spriteA.setOriginCenter();
        spriteA.setPosition(bodyA.getWorldCenter().x - WIDTH/4f, bodyA.getWorldCenter().y - HEIGHT/4f);
//        spriteB.setSize(width, height);
//        spriteB.setOriginCenter();
//        spriteB.setPosition(bodyB.getWorldCenter().x - WIDTH/4f, bodyB.getWorldCenter().y - HEIGHT/4f);
        if (visible){
            spriteA.draw(s);
//            spriteB.draw(s);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    public void joint(Body bodyC){
//        bodyA.setGravityScale(0f);
//        bodyC.setGravityScale(0f);
//        bodyA.setFixedRotation(false);
//        bodyC.setFixedRotation(false);
        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = bodyA;
        ropeJointDef.bodyB = bodyC;
        ropeJointDef.collideConnected = true;
        ropeJointDef.maxLength = 50f;
//        ropeJointDef.localAnchorA.set(0, -20);
//        ropeJointDef.localAnchorB.set(0, 20);
//        bodyA.getWorldCenter(), bodyC.getWorldCenter());
//        ropeJointDef.length = 10f;
        world.createJoint(ropeJointDef);
    }


    public void beginContact(Body body1, Body body2){
        if (body1.getUserData().toString().equals(bodyA.getUserData()) && body2.getUserData().toString().contains("Mouse")){
            collide = true;
        } else {
            if (body2.getUserData().toString().equals(bodyA.getUserData()) && body1.getUserData().toString().contains("Mouse")) {
                collide = true;
            }
        }
    }

}
