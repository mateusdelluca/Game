package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Monster1_Sprites;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.mygdx.game.screens.levels.Level.player;

public class Monster1 extends Objeto implements Serializable {

    public static final float WIDTH = 94, HEIGHT = 128;
    public static float BOX_WIDTH = 78f, BOX_HEIGHT = 118f;
//    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, looping, facingRight;
    private Vector2 dimensions = new Vector2(78f, 118f);
    private float flickering_deltaTime;

    @Setter
    @Getter
    private boolean split;
    private int id;
    private boolean soundRunning;
    private String nameAnimation;
    private boolean left;
    private long lastTime, deltaTime, initialTime;

    public Monster1_Sprites animations = new Monster1_Sprites();
    private float attacking_time;
    private boolean attackOnce;
    private Body attack_box;
    private int index;
    private float attackOnceTimer;

    public Monster1(Vector2 position, String userData){
        super(WIDTH, HEIGHT);
        id = Integer.parseInt(String.valueOf(userData.charAt(8)));
        body = createBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        mass(1.0f, new Vector2(WIDTH/2f, HEIGHT/2f), 1.0f);
    }


    public void render(SpriteBatch spriteBatch){
        if (visible) {
            if (!beenHit) {
                if (isAttacking()) {
                    attacking_time += Gdx.graphics.getDeltaTime();
                    if (attacking_time >= 3/12f) {
                        attacking_time = 0f;
//                        animations.attacking.resetStateTime(); TODO: arrumar stateTime para fim da animação
                        animations.changeAnimation("MONSTER1_WALKING");
                        attackOnce = true;
                    }
                }
            }
            Sprite sprite = new Sprite(animations.currentAnimation.currentSpriteFrameUpdateStateTime(usingOnlyLastFrame, looping, facingRight));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            if (animations.nameOfAnimation.equals("MONSTER1_ATTACKING")) {
                float x = body.getPosition().x - 256 / 2f - BOX_WIDTH / 2f;
                sprite.setPosition(x, sprite.getY());
            }
            sprite.draw(spriteBatch);
        }
    }

    @Override
    public void update(){
        super.update();
        nameAnimation = animations.nameOfAnimation;
        animations.currentAnimation.update();
        if (body == null)
            loadBody(BodyDef.BodyType.DynamicBody, false);
        if (player != null && player.getBody() != null && body != null) {
            if (!isBeenHit()) {
                if (Math.abs(player.getBody().getPosition().y - body.getPosition().y) < 100) {
                    if (Math.abs(player.getBody().getPosition().x - body.getPosition().x) < 150) {
                       if (isntAttacking() && !attackOnce) {
                           attack();
                       }
                       if (player.getBody().getPosition().x < body.getPosition().x) {
                           facingRight = false;
                           getBody().applyForce(new Vector2(-5_000, 0), player.getBody().getWorldCenter(), true);
                       } else {
                           getBody().applyForce(new Vector2(5_000, 0), player.getBody().getWorldCenter(), true);
                           facingRight = true;
                       }
                    }
                }
            }
            attackOnceTimer += Gdx.graphics.getDeltaTime();
            if (attackOnceTimer > 3f) {
                attackOnce = false;
                attackOnceTimer = 0f;
                animations.attacking.resetAnimation();
            }

            if (nameAnimation.equals("MONSTER1_SPLIT")) {
                looping = false;
                for (Fixture f : getBody().getFixtureList()) {
                    f.setSensor(true);
                }


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

                    }
                    timer += Gdx.graphics.getDeltaTime();
                    if (timer >= 0.5f) {
                        timer = 0f;
                        animations.changeAnimation("MONSTER1_WALKING");
                        soundRunning = false;
                        beenHit = false;
                    }
                }
            }
        }
        if (HP <= 0 && !split) {
            animations.changeAnimation("MONSTER1_SPLIT");
            split = true;
            dropItems();
        }
        if (isntAttacking()) {
//            for (Body attack_box : attack_box)
                if (attack_box != null)
                    attack_box.setTransform(25_000, 25_000, 0);

        }
    }

    private boolean isntAttacking(){
        return !animations.nameOfAnimation.equals("MONSTER1_ATTACKING");
    }

    private boolean isAttacking(){
        return animations.nameOfAnimation.equals("MONSTER1_ATTACKING");
    }

    private void attack(){
        body.setLinearVelocity(0f, body.getLinearVelocity().y);
        animations.changeAnimation("MONSTER1_ATTACKING");
        attack_box = BodiesAndShapes.box(new Vector2(!facingRight ? body.getPosition().x - 110 :
            body.getPosition().x + 110, body.getPosition().y + 50f), new Vector2(80f,40f),
            BodyDef.BodyType.StaticBody, false, " Enemy", 50f);
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
        setStateTime(animations.currentAnimation.timeToFramePosition(frame));
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
