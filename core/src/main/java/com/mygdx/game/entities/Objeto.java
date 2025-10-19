package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.items.Bullet;
import com.mygdx.game.items.minis.Minis;
import com.mygdx.game.screens.Stats;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Random;

import static com.mygdx.game.images.PowerBar.hit;
import static com.mygdx.game.screens.Stats.VIT;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public abstract class Objeto implements ObjetoFields, Serializable{

    @Getter
    @Setter
    protected transient Body body;
//    protected PolygonShape polygonShape;
//    protected FixtureDef fixtureDef;
    @Getter @Setter
    protected float width, height;
    protected Vector2 dimensions;
    @Getter
    @Setter
    protected boolean visible, dead;
    protected Rectangle rect;
    private float alpha = 1.0f;
    @Getter @Setter
    protected boolean beenHit;
    @Getter @Setter
    private float  deltaTime;
    @Getter @Setter
    protected BodyData bodyData;
    protected float timer;
    @Setter @Getter
    protected float HP = 10f;

    public Objeto(float width, float height){
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    public Objeto(){
    }

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
        fixtureDef.density = 1f;
//        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        body.setUserData(this.toString());
        if (this instanceof Boy)
            bodyData = new BodyData(body, dimensions, width, height);
        else
            bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        polygonShape.dispose();
        body.setAwake(true);
        return body;
    }

    protected Body createBody(Vector2 dimensions, BodyDef.BodyType bodyType, Float density, boolean isSensor){
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
        fixtureDef.density = density;
//        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        body.setUserData(this.toString());
        if (this instanceof Boy)
            bodyData = new BodyData(body, dimensions, width, height);
        else
            bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        polygonShape.dispose();
        body.setAwake(true);
        return body;
    }

    protected Body createBody(Vector2 dimensions, BodyDef.BodyType bodyType, boolean isSensor, float friction){
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
        fixtureDef.friction = friction;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.setActive(true);
        body.setFixedRotation(true);
        body.createFixture(fixtureDef);
        body.setUserData(this.toString());
        if (this instanceof Boy)
            bodyData = new BodyData(body, dimensions, width, height);
        else
            bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
        polygonShape.dispose();
        body.setAwake(true);
        return body;
    }

    public void mass(float mass, Vector2 center, float inertia){
        MassData massData = new MassData();
        massData.mass = mass;
        massData.center.set(center);
        massData.I = inertia;
        body.setMassData(massData);
    }

    public abstract void render(SpriteBatch s);

    public abstract void renderShape(ShapeRenderer s);

    public boolean intersectsRectangle(Rectangle another){
        return getRect().overlaps(another);
    }

    public Rectangle getRect(){
       return new Rectangle(body.getPosition().x - width/2f, body.getPosition().y - height/2f, width, height);
    }

    public String toString(){
        return getClass().getSimpleName();
    }

    public void loadBody(BodyDef.BodyType type, boolean isSensor){
        body = bodyData.convertDataToBody(type, isSensor);
        body.setUserData(this.toString());
//        System.out.println(this);
    }

    public Objeto objeto(){
        return this;
    }

    public void update(){
        if (body != null && rect == null) {
            rect = new Rectangle(body.getPosition().x - (width / 2f), body.getPosition().y - (height / 2f), width, height);
        }
//        hit(body);
        if (!isVisible() && body != null) {
            body.setTransform(10_000 + new Random().nextFloat(10000), 10_000 + new Random().nextFloat(10000), 0);
//            world.destroyBody(body);
        }
        if (HP <= 0 && !(this instanceof Boy) && !dead){
            dead = true;
            dropPotion();
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    body.setUserData("null");
                    setVisible(false);
                }
            }, 1);
        }
    }

    public void fixBullet(Bullet bullet){
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 0.1f){
            if (!bullet.getBody().getFixtureList().isEmpty())
                bullet.getBody().getFixtureList().get(0).setSensor(false);
            timer = 0f;
        }
    }

    public void beenHit(){
        beenHit = true;
        if (this instanceof Boy)
            hit = true;
    }

    public void dropPotion(){
        int rand = new Random().nextInt(4);
        for (int i = 0; i < rand; i++)
            new Minis(body.getPosition());
        Stats.exp_Points += 5;
    }

    public void beenHit(Body body1, Body body2) {
        if (body1 != null && body2 != null && getBody() != null) {
            if (getBody().equals(body1) || getBody().equals(body2)) {
                if (
//                    (body1.getUserData().toString().contains("Bullet") || body2.getUserData().toString().contains("Bullet"))
//                    ||
//                    (body1.getUserData().toString().contains("Colliders") || body2.getUserData().toString().contains("Colliders"))
//                    ||
//                    (body1.getUserData().toString().contains("NinjaStar") || body2.getUserData().toString().contains("NinjaStar"))
//                    ||
//                    (body1.getUserData().toString().contains("Laser") || body2.getUserData().toString().contains("Laser"))
//                    ||
                    (body1.getUserData().toString().contains("Boy") || body2.getUserData().toString().contains("Boy"))
//                    ||
//                    (body1.getUserData().toString().contains("Punch") || body2.getUserData().toString().contains("Punch"))
                    ) {
                    if (this instanceof Monster1 || this instanceof Jack || this instanceof Girl || this instanceof Robot) {
                        beenHit();
                        HP -= Boy.attack;
                    }
                }
                if (
                    (body1.getUserData().toString().contains(" Enemy") || body2.getUserData().toString().contains(" Enemy"))
                    ||
                    (body1.getUserData().toString().contains(" Colliders") || body2.getUserData().toString().contains(" Colliders"))
                    ||
                    (body1.getUserData().toString().contains("NinjaStar") || body2.getUserData().toString().contains("NinjaStar"))) {
                    if (this instanceof Boy)
                        beenHit();
                }
            }
        }
    }


    public void setUserData(Body body){
        bodyData.userData = "" + body.getUserData();
    }

    public void setUserData(String name){
        bodyData.userData = name;
        body.setUserData(name);
    }

    public void beginContact(Body body1, Body body2){

    }

    public void beginContact(Contact contact){

    }

    public boolean onGround(){
        return Math.abs(body.getLinearVelocity().y) <= 0.1f;
    }

}
