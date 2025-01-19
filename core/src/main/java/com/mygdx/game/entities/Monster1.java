package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.Animations;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.sfx.Sounds;
import ktx.box2d.RayCast;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Monster1 extends Objeto{

    public static final float WIDTH = 94, HEIGHT = 128;
    public static float BOX_WIDTH = 78f, BOX_HEIGHT = 118f;
    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, looping = true, facingRight;
    private Vector2 dimensions = new Vector2(78f, 118f);
    private float flickering_time;
    @Setter @Getter
    private float HP = 5;
    @Setter
    @Getter
    private boolean split;
    private int id;
    private boolean soundRunning;

    public Monster1(World world, Vector2 position, String userData){
        super(world, WIDTH, HEIGHT);
        id = Integer.parseInt(String.valueOf(userData.charAt(8)));
        body = createBoxBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);

    }
    @Override
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
        fixtureDef.density = 10f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
        body.setUserData(getClass().getSimpleName() + id);
        body.setActive(true);
        body.createFixture(fixtureDef);
//        System.out.println(body.getUserData());
        return body;
    }

    public void render(SpriteBatch spriteBatch){
        if (visible){
            Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(false, looping, facingRight));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(spriteBatch);
        }
    }

    public void update(Boy boy){
        if (Math.abs(boy.getBody().getPosition().y - body.getPosition().y) < 100){
            if (Math.abs(boy.getBody().getPosition().x - body.getPosition().x) < 300) {
                if (boy.getBody().getPosition().x < body.getPosition().x) {
                    facingRight = false;
                    body.setLinearVelocity(-5,body.getLinearVelocity().y);
                }
                else{
                    body.setLinearVelocity(5,body.getLinearVelocity().y);
                    facingRight = true;
                }
            }
        }
        String name = animations.name();
        if (!visible)
            body.setTransform(0,0,0);
        if (HP <= 0){
            animations = Animations.MONSTER1_SPLIT;
            split = true;
        }
        if (name.equals("MONSTER1_SPLIT")){
            looping = false;
            for (Fixture f : getBody().getFixtureList()){
                f.setSensor(true);
            }
            body.setUserData("null");
//            getBody().setGravityScale(0f);
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task(){
                @Override
                public void run() {
                    setVisible(false);
                }
            }, 1);
        } else {
            if (name.equals("MONSTER1_FLICKERING")) {
                if (!soundRunning) {
                    Sounds.MONSTER_HURT.play();
                    soundRunning = true;
                }
                if (flickering_time >= 1.0f) {
                    flickering_time = 0f;
                    body.setLinearVelocity(0,0);
                    setBeenHit(false);
                    animations = Animations.MONSTER1_WALKING;
                    soundRunning = false;
                    Sounds.MONSTER_HURT.stop();
                }
                if (HP <= 0){
                    animations = Animations.MONSTER1_SPLIT;
                    split = true;
                }
                flickering_time += Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + id;
    }

    public Rectangle getBodyBounds() {
        if (split)
            return new Rectangle();
        return new Rectangle(body.getPosition().x - 2.5f, body.getPosition().y + 5f, dimensions.x + 20f, dimensions.y + 5f);
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

}
