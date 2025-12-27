package com.mygdx.game.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.mygdx.game.entities.Objeto;
import lombok.Getter;

import java.util.ArrayList;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.screens.levels.Level.world;

import static com.mygdx.game.platform.JointKnot.RADIUS;

public class Bridge extends Objeto {

    @Getter
    private ArrayList<JointKnot> bridge = new ArrayList<>();

    private Body begin, end;
    private boolean collides;
    private boolean joint;

    public Bridge(Vector2 position, int length){
        begin = box(new Vector2(position.x - RADIUS/2f, position.y - RADIUS/2f), new Vector2(RADIUS/2f, RADIUS/2f), BodyDef.BodyType.StaticBody, true, "begin", 10f);
        end = box(new Vector2(position.x - RADIUS/2f + (RADIUS * length), position.y - RADIUS/2f), new Vector2(RADIUS/2f, RADIUS/2f), BodyDef.BodyType.StaticBody, true, "end", 10f);


        for (int i = 0; i < length; i += 1){
            JointKnot knotA = new JointKnot(new Vector2(position.x + (RADIUS * i), position.y));
            bridge.add(knotA);
        }

        for (int i = 0; i < (length - 1); i++) {
            Body bodyA = bridge.get(i).getBody();
            Body bodyB = null;
            if (bridge.lastIndexOf(bridge.get(i+1)) > 0){
                bodyB = bridge.get(i + 1).getBody();
            }
            RopeJointDef ropeJointDef = new RopeJointDef();
            ropeJointDef.bodyA = bodyA;
            ropeJointDef.bodyB = bodyB;
            ropeJointDef.collideConnected = true;
            ropeJointDef.maxLength = 1f;
            world.createJoint(ropeJointDef);
        }
    }

    @Override
    public void update(){
        if (collides && !joint) {
            RopeJointDef ropeJointDef = new RopeJointDef();
            ropeJointDef.bodyA = begin;
            ropeJointDef.bodyB = bridge.getFirst().getBody();
            ropeJointDef.collideConnected = true;
            ropeJointDef.maxLength = 1f;
            world.createJoint(ropeJointDef);

            RopeJointDef ropeJointDef2 = new RopeJointDef();
            ropeJointDef2.bodyA = bridge.getLast().getBody();
            ropeJointDef2.bodyB = end;
            ropeJointDef2.collideConnected = true;
            ropeJointDef2.maxLength = 1f;
            world.createJoint(ropeJointDef2);


            collides = false;
            joint = true;

        }
    }

    @Override
    public void render(SpriteBatch s){
        for (JointKnot knot : bridge){
            knot.render(s);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    public void beginContact(Contact contact) {

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if (body1 == null || body2 == null)
            return;

        if (body1.getUserData().toString().contains("Rects") && body2.getUserData().toString().contains("Knot")){
            collides = true;
        }
        if (body2.getUserData().toString().contains("Rects")  && body1.getUserData().toString().contains("Knot")){
            collides = true;
        }
    }
}
