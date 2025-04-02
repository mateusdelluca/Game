package com.mygdx.game.Bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.screens.levels.Level_Manager.world;

public class Builder{

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
        return body;
    }

}
