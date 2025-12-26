package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.screens.levels.Level.world;

import static com.mygdx.game.entities.Knot.RADIUS;

public class Bridge extends Objeto{

    @Getter
    private ArrayList<Knot> bridge = new ArrayList<>();

    public Bridge(Vector2 position, int size){
        Knot knotA = null;
        Knot knotB = null;

        for (int i = 0; i <= size; i += 2){
            knotA = new Knot(new Vector2(position.x + (RADIUS * i), position.y));
            knotB = new Knot(new Vector2(position.x + (RADIUS * (i + 1)), position.y));
            bridge.add(knotA);
            bridge.add(knotB);
        }

        for (int i = 0; i <= size; i++) {
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
    public void render(SpriteBatch s){
        for (Knot knot : bridge){
            knot.render(s);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }


}
