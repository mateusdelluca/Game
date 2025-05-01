package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mygdx.game.Bodies.Builder;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.screens.levels.Level_Manager.world;

public class Rope extends Objeto {


    @Getter @Setter
    private Body bodyA;
    @Getter @Setter
    private Body bodyB;

    private Vector2 dimensions;

    private Sprite spriteA = new Sprite(Images.rope);
    private Sprite spriteB = new Sprite(Images.rope);
    @Getter @Setter
    private DistanceJoint jointAB;

    private Vector2 positionA, positionB;

    public static final float MULTIPLY = 2f;

    public static final float WIDTH = Images.rope.getWidth() * MULTIPLY, HEIGHT = Images.rope.getHeight() * MULTIPLY;

    public Rope(Vector2 positionA, boolean firstRope){
        super(WIDTH, HEIGHT);
        this.dimensions = new Vector2(WIDTH/2f, HEIGHT/2f);
        this.positionA = positionA;
        bodyA = Builder.box(positionA, dimensions, firstRope ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody);
//        positionB = new Vector2(this.positionA);
//        positionB.add(new Vector2(0f,50f));
//        bodyB = Builder.box(positionB, dimensions, BodyDef.BodyType.DynamicBody);
//        DistanceJointDef distanceJointDef = new DistanceJointDef();
//        distanceJointDef.initialize(bodyA, bodyB, bodyA.getWorldCenter(), bodyB.getWorldCenter());
//        jointAB = (DistanceJoint) world.createJoint(distanceJointDef);
    }

    @Override
    public void update(){

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
        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.initialize(bodyA, bodyC, bodyA.getWorldCenter(), bodyC.getWorldCenter());
        distanceJointDef.length = 30f;
        world.createJoint(distanceJointDef);
    }
}
