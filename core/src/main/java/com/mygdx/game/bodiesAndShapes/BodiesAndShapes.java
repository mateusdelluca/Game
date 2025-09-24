package com.mygdx.game.bodiesAndShapes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.screens.levels.Level_Manager.world;

public class BodiesAndShapes {

    public static Body box(Vector2 position, Vector2 dimensions){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body box(Vector2 position, Vector2 dimensions, BodyDef.BodyType type){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);
        body.setFixedRotation(false);
        body.setAwake(true);
        return body;
    }

    public static Body box(Vector2 position, Vector2 dimensions, BodyDef.BodyType type, boolean isSensor, float angle){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = isSensor;
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);

        body.setFixedRotation(false);
        body.setTransform(position, (float) Math.toRadians(angle));
        body.setAwake(true);
        return body;
    }

    public static Body box(Vector2 position, Vector2 dimensions, BodyDef.BodyType type, boolean sensor){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = sensor;
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body box(Vector2 position, Vector2 dimensions, BodyDef.BodyType type, boolean sensor, String userData){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = sensor;
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setUserData(userData);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body box(Vector2 position, Vector2 dimensions, BodyDef.BodyType type, boolean sensor, String userData, float density){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = sensor;
        PolygonShape box = new PolygonShape();
        box.setAsBox(dimensions.x, dimensions.y, new Vector2(dimensions.x/2f, dimensions.y/2f), 0f);
        fixtureDef.shape = box;
        fixtureDef.density = density;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setUserData(userData);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body circle(Vector2 position, int radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fixtureDef.shape = circleShape;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body circle(Vector2 position, int radius, BodyDef.BodyType type){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fixtureDef.shape = circleShape;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

    public static Body circle(Vector2 position, Float radius, BodyDef.BodyType type, boolean sensor, String userData, float density){
        BodyDef bodyDef = new BodyDef();
        bodyDef.active = true;
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(0,0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = sensor;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        fixtureDef.restitution = 1.0f;
        fixtureDef.friction = 1f;
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(position, 0);
        body.setUserData(userData);
        body.setActive(true);
        body.setAwake(true);
        return body;
    }

}
