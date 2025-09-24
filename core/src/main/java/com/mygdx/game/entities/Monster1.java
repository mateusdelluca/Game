package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.Monster1_Sprites;
import com.mygdx.game.items.minis.Minis;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.entities.Boy.minis;
import static com.mygdx.game.screens.levels.Level_Manager.currentLevel;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public class Monster1 extends Objeto implements Serializable {

    public static final float WIDTH = 94, HEIGHT = 128;
    public static float BOX_WIDTH = 78f, BOX_HEIGHT = 118f;
//    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, noLooping, facingRight;
    private Vector2 dimensions = new Vector2(78f, 118f);
    private float flickering_deltaTime;
    @Setter @Getter
    private float HP = 7;
    @Setter
    @Getter
    private boolean split;
    private int id;
    private boolean soundRunning;
    private String nameAnimation;
    private boolean left;
    private long lastTime, deltaTime, initialTime;



    public Monster1_Sprites animations = new Monster1_Sprites();
    private Boy boy;
    public Monster1(Vector2 position, String userData, Boy boy){
        super(WIDTH, HEIGHT);
        id = Integer.parseInt(String.valueOf(userData.charAt(8)));
        body = createBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        this.boy = boy;
    }


    public void render(SpriteBatch spriteBatch){
        if (visible){
            Sprite sprite = new Sprite(animations.currentAnimation.currentSpriteFrame(usingOnlyLastFrame, !noLooping, facingRight));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(spriteBatch);
        }
    }

    @Override
    public void update(){
        super.update();
        if (body == null)
            loadBody(BodyDef.BodyType.DynamicBody, false);
        if (boy != null && boy.getBody() != null && body != null) {
            if (!isBeenHit()) {
                if (Math.abs(boy.getBody().getPosition().y - body.getPosition().y) < 100) {
                    if (Math.abs(boy.getBody().getPosition().x - body.getPosition().x) < 300) {
                        if (boy.getBody().getPosition().x < body.getPosition().x) {
                            facingRight = false;
                            getBody().applyForce(new Vector2(-5_000, 0), getBody().getWorldCenter(), true);
                        } else {
                            getBody().applyForce(new Vector2(5_000, 0), getBody().getWorldCenter(), true);
                            facingRight = true;
                        }
                    }
                }
            }
            nameAnimation = animations.nameOfAnimation;
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
                if (animations.nameOfAnimation.equals("MONSTER1_FLICKERING")) {
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
//                        body.setLinearVelocity(0, 0);
                        animations.changeAnimation("MONSTER1_WALKING");
                        soundRunning = false;

                        beenHit = false;
                    }
                }
            }
        }
        if (HP <= 0 && HP > -30) {
            animations.changeAnimation("MONSTER1_SPLIT");
            split = true;
            int rand = new Random().nextInt(3);
            for (int i = 0; i < rand; i++)
                new Minis(body.getPosition());
            HP = -100;
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + id + " Enemy";
    }

    public Rectangle getBodyBounds() {
        if (split)
            return new Rectangle();
        return new Rectangle(bodyData.position.x - 2.5f, bodyData.position.y + 5f, bodyData.dimensions.x + 20f, bodyData.dimensions.y + 5f);
    }

    public void setStateTime(float time){
        animations.currentAnimation.stateTime = time;
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.currentAnimation.timeToFrame(frame));
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
        if (body != null && !animations.nameOfAnimation.equals("MONSTER1_FLICKERING")) {
            animations.changeAnimation("MONSTER1_FLICKERING");
            Sounds.MONSTER_HURT.play();
            setBeenHit(true);
        }
    }


    public void beginContact(Body bodyA, Body bodyB){
        if ((bodyA.equals(body) && bodyB.getUserData().toString().equals("Laser Boy"))
        || (bodyB.equals(body) && bodyA.getUserData().toString().equals("Laser Boy"))){
            beenHit();
        }
        if ((bodyA.equals(body) && bodyB.getUserData().toString().equals("Bullet Boy"))
            || (bodyB.equals(body) && bodyA.getUserData().toString().equals("Bullet Boy"))){
            beenHit();
        }
        if ((bodyA.equals(body) && bodyB.getUserData().toString().contains("Thorns")
            || bodyB.equals(body) && bodyA.getUserData().toString().contains("Thorns"))){
            beenHit();

        }
        if (bodyA.equals(body) && bodyB.getUserData().toString().equals("Punch") ||
            bodyA.equals(body) && bodyB.getUserData().toString().equals("Boy")){
            beenHit();
//            body.setLinearVelocity(body.getPosition().x < bodyB.getPosition().x ? 20 : -20,20);
//            world.clearForces();
        }
        if (bodyB.equals(body) && bodyA.getUserData().toString().equals("Punch") ||
            bodyB.equals(body) && bodyA.getUserData().toString().equals("Boy")){
            beenHit();
//            body.setLinearVelocity(body.getPosition().x < bodyA.getPosition().x ? 20 : -20,20);
//            world.clearForces();
        }
    }
}
