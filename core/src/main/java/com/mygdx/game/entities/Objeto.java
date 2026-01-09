package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import java.util.Random;

import static com.mygdx.game.screens.levels.Level.world;

public abstract class Objeto implements Serializable{

    @Setter
    @Getter
    protected transient Body body;

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
    protected boolean touchingGround;
    protected boolean onGround;
    @Getter @Setter
    private int index;
    protected BitmapFont font;
    protected float scale = 5f;
    protected boolean isScale;
    public Character_Features character_features = new Character_Features();
    protected boolean playerBodyXPositionHigherThanAnotherBody;
    @Setter @Getter
    protected boolean isFacingRight;
    public Objeto(float width, float height){
        this.width = width;
        this.height = height;
        this.visible = true;
        Texture t = new Texture(Gdx.files.internal("Font.png"));
        font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        font.getData().setScale(scale, scale);
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
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

    public void render(SpriteBatch s){
        if (beenHit) {
            if (scale >= 0.1) {
                scale -= 0.1f;
                font.getData().setScale(scale, scale);
            }
            else{
                scale = 5.0f;
            }
        }
    }

    public void renderShape(ShapeRenderer sr) {
        renderHP(sr);
    }

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
        character_features.update(this);
        if (beenHit && !isScale) {
            if (scale > 0f)
                scale -= 0.2f;
            font.getData().setScale(scale, scale);
            if (scale <= 0f) {
                scale = 5f;
                isScale = true;
                font.getData().setScale(scale, scale);
            }
        }
        if (body != null && rect == null) {
            rect = new Rectangle(body.getPosition().x - (width / 2f), body.getPosition().y - (height / 2f), width, height);
        }
//        hit(body);
        if (!isVisible() && body != null) {
            body.setTransform(10_000 + new Random().nextFloat(10000), 10_000 + new Random().nextFloat(10_000), 0);
//            world.destroyBody(body);
        }
        if (character_features.getHp() <= 0 && !(this instanceof Player) && !dead){
            dead = true;
            dropItems();
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
    }


    public boolean playerBodyXPositionHigherThanAnotherBody(Body bodyA, Body bodyB){
        return (bodyA.equals(body) && bodyA.getPosition().x > bodyB.getPosition().x);
    }

    public void dropItems(){
        int rand = new Random().nextInt(4);
        for (int i = 0; i < rand; i++)
            new Minis(new Vector2(isFacingRight ? body.getPosition().x - 25 : body.getPosition().x + 50, body.getPosition().y));
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
                    (body1.getUserData().toString().contains("Player") || body2.getUserData().toString().contains("Player"))
                    ||
                    (body1.getUserData().toString().contains("Boy") || body2.getUserData().toString().contains("Boy"))
//                    ||
//                    (body1.getUserData().toString().contains("Punch") || body2.getUserData().toString().contains("Punch"))
                    ) {
                    if (this instanceof Monster1 || this instanceof Jack || this instanceof Girl || this instanceof Robot) {
                        beenHit();
                    }
                }
                if (
                    (body1.getUserData().toString().contains(" Enemy") || body2.getUserData().toString().contains(" Enemy"))
                    ||
                    (body1.getUserData().toString().contains(" Colliders") || body2.getUserData().toString().contains(" Colliders"))
                    ||
                    (body1.getUserData().toString().contains("NinjaStar") || body2.getUserData().toString().contains("NinjaStar"))) {
                    if (this instanceof Player)
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
        if ((body1.getUserData().toString().contains("Player") && body2.getUserData().toString().contains("Enemy")))
            playerBodyXPositionHigherThanAnotherBody = playerBodyXPositionHigherThanAnotherBody(body1, body2);
        if (body2.getUserData().toString().contains("Enemy") && body1.getUserData().toString().contains("Player"))
            playerBodyXPositionHigherThanAnotherBody = playerBodyXPositionHigherThanAnotherBody(body2, body1);
    }

    public void beginContact(Contact contact){
        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if (body1 == null || body2 == null || body == null)
            return;


        if (
            (
                ((body1.getUserData().toString().contains("Thorns") && body2.equals(body))
                    || (body2.getUserData().toString().contains("Thorns") && body1.equals(body)))
                    ||

                    ((body1.getUserData().toString().contains("Colliders") && body2.equals(body))
                        || (body2.getUserData().toString().contains("Colliders") && body1.equals(body)))

                    ||
                    ((body1.getUserData().toString().contains("Rect") && body2.equals(body))
                        || body2.getUserData().toString().contains("Rect") && body1.equals(body))

                    ||
                    ((body1.getUserData().toString().contains("Knot") && body2.equals(body))
                        || body2.getUserData().toString().contains("Knot") && body1.equals(body))

            )                ||
                (Math.abs(body.getLinearVelocity().y) <= 0.1)){

            onGround = true;
        } else
            onGround = false;
    }

    public boolean onGround(){
        return onGround;
    }

    public void renderHP(ShapeRenderer sr){
        sr.setColor(new Color(1,0,0,235f/255f));
        if (visible)
            sr.rect(body.getPosition().x + 40f, body.getPosition().y + 128f, character_features.getHp(), 5f);
    }
}
