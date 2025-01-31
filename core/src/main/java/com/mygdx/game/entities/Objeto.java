package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.mygdx.game.screens.levels.Level_Manager.world;

public abstract class Objeto implements ObjetoFields, Serializable{

    @Getter
    @Setter
    protected transient Body body;
//    protected PolygonShape polygonShape;
//    protected FixtureDef fixtureDef;
    protected float width, height;
    protected Vector2 dimensions;
    @Getter
    @Setter
    protected boolean visible;
    protected Rectangle rect;
    private float alpha = 1.0f;
    @Getter @Setter
    private boolean beenHit;
    @Getter @Setter
    private float  deltaTime;
    @Getter @Setter
    protected BodyData bodyData;

    public Objeto(float width, float height){
        this.width = width;
        this.height = height;
        visible = true;
        if (body != null) {
            rect = new Rectangle(body.getPosition().x - width / 2f, body.getPosition().y - height / 2f, width, height);
        }

    }

//    public Objeto(World world, Vector2 position){
//        this.width = width;
//        this.height = height;
//        this.world = world;
//        visible = true;
//        if (body != null) {
//            rect = new Rectangle(body.getPosition().x - width / 2f, body.getPosition().y - height / 2f, width, height);
//        }
//    }

    public Objeto(){
    }

    protected Body createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
//        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        PolygonShape polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(width/2f, height/2f, new Vector2(width/2f, height/2f), 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100f;
        fixtureDef.restitution = 0.1f;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.createFixture(fixtureDef);
        bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        return body;
    }

//    protected Body createBody(Vector2 position, boolean isSensor){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.active = true;
////        bodyDef.position.set(0,0);
//        bodyDef.fixedRotation = true;
//        PolygonShape polygonShape = new PolygonShape();
////         Adicione formas (fixtures) ao corpo para representar sua geometria
//        polygonShape.setAsBox(width/2f, height/2f, new Vector2(width/2f, height/2f), 0);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = polygonShape;
//        fixtureDef.density = 100f;
//        fixtureDef.restitution = 0.1f;
//        fixtureDef.isSensor = isSensor;
//        Body body = world.createBody(bodyDef);
////        body.createFixture(fixtureDef).setUserData(this);
//        body.setActive(true);
//        body.createFixture(fixtureDef);
//        bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
//        return body;
//    }

    protected Body createBody(Vector2 dimensions, BodyDef.BodyType bodyType, boolean isSensor){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        PolygonShape polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(dimensions.x, dimensions.y, new Vector2(width/2f, height/2f), 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 10f;
//        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.createFixture(fixtureDef);
        body.setUserData(this.toString());
        if (this instanceof Boy)
            bodyData = new BodyData(body, dimensions, width, height);
        else
            bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        return body;
    }

    public abstract void render(SpriteBatch s);

    public abstract void renderShape(ShapeRenderer s);

    public boolean intersectsRectangle(Rectangle another){
        return getRect().overlaps(another);
    }

    public Rectangle getRect(){
       return new Rectangle(body.getPosition().x - width/2f, body.getPosition().y - height/2f, width, height);
    }

    public abstract String toString();

    public void beenHit(Sprite sprite){
        if (beenHit) {
            deltaTime += Gdx.graphics.getDeltaTime();
            if (deltaTime > 0.3f) {
//                alpha = new Random().nextFloat(1f);
                deltaTime = 0f;
                Sounds.HURT.play();
//                sprite.setAlpha(alpha);
            }
        }
    }

    public void loadWorldAndBody(BodyDef.BodyType type, boolean isSensor){
        body = bodyData.convertDataToBody(type, isSensor);
        body.setUserData(this.toString());
        System.out.println(this);
    }

    public Objeto objeto(){
        return this;
    }

    public abstract void update();

}
