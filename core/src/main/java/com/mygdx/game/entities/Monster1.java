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
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

public class Monster1 extends Objeto implements Serializable {

    public static final float WIDTH = 94, HEIGHT = 128;
    public static float BOX_WIDTH = 78f, BOX_HEIGHT = 118f;
    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, noLooping, facingRight;
    private Vector2 dimensions = new Vector2(78f, 118f);
    private float flickering_deltaTime;
    @Setter @Getter
    private float HP = 3;
    @Setter
    @Getter
    private boolean split;
    private int id;
    private boolean soundRunning;
    private String nameAnimation;
    private boolean left;
    private long lastTime, deltaTime, initialTime;

    public Monster1(Vector2 position, String userData){
        super(WIDTH, HEIGHT);
        id = Integer.parseInt(String.valueOf(userData.charAt(8)));
        body = createBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }


    public void render(SpriteBatch spriteBatch){
        if (visible){
            Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, !noLooping, facingRight));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(spriteBatch);
        }
    }

    public void update(Boy boy){
        update();
        if (boy != null && boy.getBody() != null && body != null) {
            if (Math.abs(boy.getBody().getPosition().y - body.getPosition().y) < 100) {
                if (Math.abs(boy.getBody().getPosition().x - body.getPosition().x) < 300) {
                    if (boy.getBody().getPosition().x < body.getPosition().x) {
                        facingRight = false;
                        body.setLinearVelocity(-5, body.getLinearVelocity().y);
                    } else {
                        body.setLinearVelocity(5, body.getLinearVelocity().y);
                        facingRight = true;
                    }
                }
            }
            nameAnimation = animations.name();
            if (nameAnimation.equals("MONSTER1_SPLIT")) {
                noLooping = true;
                for (Fixture f : getBody().getFixtureList()) {
                    f.setSensor(true);
                }
                body.setUserData("null");
//            getBody().setGravityScale(0f);
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        setVisible(false);
                    }
                }, 1);
            } else {
                if (nameAnimation.equals("MONSTER1_FLICKERING")) {
                    if (!soundRunning) {
                        Sounds.MONSTER_HURT.play();
                        soundRunning = true;
                        initialTime = System.nanoTime();
                        HP--;
                    }
//                    lastTime = System.nanoTime();
//                    deltaTime = (lastTime - initialTime)/1_000_000_000;
                    timer += Gdx.graphics.getDeltaTime();
                      if (timer >= 0.5f) {
//                          initialTime = System.nanoTime();
//                        flickering_deltaTime = 0f;
                          timer = 0f;
                        body.setLinearVelocity(0, 0);
                        animations = Animations.MONSTER1_WALKING;
                        soundRunning = false;

                        beenHit = false;
                    }
                }
            }
        }
        if (HP <= 0) {
            animations = Animations.MONSTER1_SPLIT;
            split = true;
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
        return new Rectangle(bodyData.position.x - 2.5f, bodyData.position.y + 5f, bodyData.dimensions.x + 20f, bodyData.dimensions.y + 5f);
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

    @Override
    public void update(){
        super.update();
        if (body == null)
            loadBody(BodyDef.BodyType.DynamicBody, false);
    }

    @Override
    public void loadBody(BodyDef.BodyType type, boolean isSensor){
        body = bodyData.convertDataToBody(type, isSensor);
        body.setUserData(this.toString());
        System.out.println(this);
    }

    @Override
    public void beenHit() {

            if (body == null)
                loadBody(BodyDef.BodyType.DynamicBody, false);
            if (body != null) {
                body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 4);
                animations = Animations.MONSTER1_FLICKERING;
                Sounds.MONSTER_HURT.play();
                setBeenHit(true);
        }
    }
}
