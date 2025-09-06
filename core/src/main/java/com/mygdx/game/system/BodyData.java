package com.mygdx.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serial;
import java.io.Serializable;

import static com.mygdx.game.screens.levels.Level_Manager.world;

public class BodyData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public float angle, rotation;
    public Vector2 position, linearVelocity;
    public String userData;
    public Vector2 dimensions;
    public float width, height;
    public float density;

    public BodyData(Body body, Vector2 dimensions, float width, float height, float density) {
        this.position = body.getPosition();
        this.angle = body.getAngle();
        this.linearVelocity = body.getLinearVelocity();
        this.userData = body.getUserData().toString();
        this.dimensions = dimensions;
        this.width = width;
        this.height = height;
        this.rotation = body.getTransform().getRotation();
        this.density = density;
    }

    public BodyData(Body body, Vector2 dimensions, float width, float height) {
        this.position = body.getPosition();
        this.angle = body.getAngle();
        this.linearVelocity = body.getLinearVelocity();
        this.userData = body.getUserData().toString();
        this.dimensions = dimensions;
        this.width = width;
        this.height = height;
        this.rotation = body.getTransform().getRotation();
        this.density = 10f;
    }

    public Body convertDataToBody(BodyDef.BodyType bodyType, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(0,0);
        bodyDef.angle = angle;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = density;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(dimensions.x, dimensions.y, new Vector2(width/2f, height/2f), 0);
        fixtureDef.shape = polygonShape;
        Body body = world.createBody(bodyDef);
        body.setTransform(position, angle);
        if (!userData.equals("Bullet"))
            body.setFixedRotation(true);
        body.setLinearVelocity(linearVelocity);
        body.getTransform().setRotation(rotation);
        body.setUserData(userData);
        body.setActive(true);
        body.createFixture(fixtureDef);
        body.setTransform(position, angle);
        return body;
    }

}

