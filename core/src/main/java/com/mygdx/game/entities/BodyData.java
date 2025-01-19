package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class BodyData implements Serializable {
    private static final long serialVersionUID = 1L;
    public float angle;
    public Vector2 position, linearVelocity;
    public String userData;

    public BodyData(Vector2 position, float angle, Vector2 linearVelocity, String userData) {
        this.position = position;
        this.angle = angle;
        this.linearVelocity = linearVelocity;
        this.userData = userData;
    }

    public BodyData convertBodyToData(Body body) {
        return new BodyData(
            body.getPosition(),
            body.getAngle(),
            body.getLinearVelocity(),
            body.getUserData().toString());
    }

    public Body convertDataToBody(World world, BodyData data) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.angle = data.angle;
        Body body = world.createBody(bodyDef);
        body.setLinearVelocity(linearVelocity);
        body.setUserData(userData);
        return body;
    }

}

