package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.handler.MyContactListener;
import lombok.Getter;
import lombok.Setter;

public abstract class Objeto{

    @Getter
    @Setter
    protected Body body;
    protected PolygonShape polygonShape;
    protected FixtureDef fixtureDef;
    @Getter
    protected World world;
    protected float width, height;
    protected Vector2 dimensions;
    @Getter
    @Setter
    protected boolean visible;
    protected Rectangle rect;
    public Objeto(World world, float width, float height){
        this.width = width;
        this.height = height;
        this.world = world;
        visible = true;
        if (body != null) {
            rect = new Rectangle(body.getPosition().x - width / 2f, body.getPosition().y - height / 2f, width, height);
        }
    }

    public Objeto(){

    }

    protected Body createBoxBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(width/2f, height/2f, new Vector2(width/2f, height/2f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100f;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.createFixture(fixtureDef);
        return body;
    }

    protected Body createBoxBody(Vector2 dimensions, BodyDef.BodyType bodyType, boolean isSensor){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(dimensions.x, dimensions.y, new Vector2(width/2f, height/2f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.createFixture(fixtureDef);
        return body;
    }

    public abstract void render(ShapeRenderer s);


    public boolean intersectsRectangle(Rectangle another){
        return getRect().overlaps(another);
    }

    public Rectangle getRect(){
       return new Rectangle(body.getPosition().x - width/2f,body.getPosition().y - height/2f, width, height);
    }


    public abstract String toString();
}
