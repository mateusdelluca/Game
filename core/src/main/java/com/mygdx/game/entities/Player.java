package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.screens.levels.Level;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.sfx.Sounds.*;

public class Player extends Stickman {

    private float stateTime; // A variable for tracking elapsed time for the animation
    public Animations animations;
    private boolean usingOnlyLastFrame;
//    private Image image = new Image(new Texture(Gdx.files.internal("Saber.png")));

    private boolean facingRight;
    private Rectangle action;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Level level;
    private float velocity;

    public Player(World world, Level level){
        super(world);
        this.level = level;
        animations = Animations.FIRST;
        box.getBody().setTransform(130, 1500, 0);
        action = new Rectangle(0, 0, 0, 0);
    }

    public void render(SpriteBatch spriteBatch){
        Sprite s = new Sprite(animations.getAnimator().currentSpriteFrame(usingOnlyLastFrame, loopTrueOrFalse(animations.name()), flip));
        s.setOrigin(0,0);
        s.setCenter(WIDTH/2f, HEIGHT/2f);
        float rotation = (float) Math.toDegrees(getBody().getTransform().getRotation());
        s.setRotation(rotation);
        s.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        s.draw(spriteBatch);
        facingRight = !s.isFlipX();
        for (Bullet b : bullets)
            b.render(spriteBatch);
    }

    private void resetPosition(){
        if (getBody().getPosition().y <= -300){
            getBody().setTransform(150,300, 0);
            animations = Animations.IDLE_FLASH;
            animations.animator.stateTime = 0f;
        }
    }

    public void update(float delta) {
        stateTime = animations.animator.stateTime;
        resetPosition();
        animation();
    }

    private void animation(){
        String name = animations.name();
        if (name.equals("FIRST")){
            if (getBodyBounds().y > 400)
                setStateTime(0);
            else {
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        stand = true;
                    }
                }, 3f);
                if (!stand)
                    setFrameCounter(1);
                if (frameCounter() >= 10) {
                    animations = Animations.IDLE;
//                    level.songLevel1.play();
                }
            }
        } else{
            if (name.equals("PUNCHED")){
                if (frameCounter() == 1) {
                    PUNCHED.play();
                    setFrameCounter(2);
                }
                if (animations.animator.ani_finished()){
                    animations.animator.resetStateTime();
                    animations = Animations.IDLE;
                    hited = false;
                }
            } else {
                if (name.equals("WALKING")) {
                    usingOnlyLastFrame = false;
                    getBody().setFixedRotation(true);
                    getBody().setTransform(getBody().getPosition().x, getBody().getPosition().y, 0);
                    if (Math.abs(getBody().getLinearVelocity().x) <= 0.2f)
                        animations = Animations.IDLE;
                } else {
                    if (name.equals("PUNCH")) {
                        if (animations.animator.ani_finished()) {
                            animations.animator.resetStateTime();
                            animations = Animations.IDLE;
                        }
                    } else {
                        if (name.equals("JUMPING")) {
                            if (animations.animator.ani_finished() && Math.abs(getBody().getLinearVelocity().y) < 5) {
                                getBody().setLinearVelocity(getBody().getLinearVelocity().x, 80);
                                JUMP.play();
                                animations.animator.resetStateTime();
                                if (getBody().getLinearVelocity().x == 0)
                                    animations = Animations.IDLE;
                                else
                                    animations = Animations.WALKING;
                            }
                        } else {
                            if (name.equals("HIYAH")) {
                                getBody().setFixedRotation(true);
                                usingOnlyLastFrame = true;
                                if (getBody().getLinearVelocity().x == 0) {
                                    getBody().setLinearVelocity(new Vector2((!flip ? 5 : -5), getBody().getLinearVelocity().y));
                                }
                                if (getBody().getLinearVelocity().x != 0) {
                                    getBody().setLinearVelocity(new Vector2((!flip ? 60 : -60), getBody().getLinearVelocity().y));
                                }

                                if (getBody().getLinearVelocity().y == 0 || getBody().getPosition().y < 0f) {
                                    getBody().getFixtureList().get(0).setFriction(0f);
                                    if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
                                        animations = Animations.IDLE;
                                    else
                                        animations = Animations.WALKING;
                                }
                            } else {
                                if (name.equals("SHOT")) {
                                    if (frameCounter() == 1) {
                                        bullets.add(new Bullet(world, new Vector2(facingRight ? getBody().getPosition().x +
                                                WIDTH / 2f + 60 : getBody().getPosition().x + WIDTH / 2f - 80,
                                                getBody().getPosition().y + HEIGHT / 2f + 40), flip));
                                        GUNSHOT.play();
                                        setFrameCounter(2);
                                    }
                                    if (animations.animator.ani_finished()) {
                                        animations.animator.resetStateTime();
                                        animations = Animations.IDLE;
                                    }
                                } else {
                                    if (name.equals("IDLE_FLASH")) {
                                        usingOnlyLastFrame = false;
                                        getBody().setFixedRotation(true);
//                                box.getBody().setTransform(150f, 300f, 0f);
                                        if (animations.animator.ani_finished()) {
                                            animations.getAnimation("IDLE_FLASH").animator.resetStateTime();
                                            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                                                animations = Animations.JUMPING;
                                            else
                                                animations = Animations.IDLE;
                                        }
                                    } else {
                                        if (name.equals("SABER")) {
                                            if (frameCounter() <= 1) {
                                                velocity = getBody().getLinearVelocity().x;
                                                getBody().setLinearVelocity(!flip ? 200f : -200f, getBody().getLinearVelocity().y + 2);
                                            } else {
                                                velocity += (-0.05f * Math.abs(velocity) / velocity);
                                                getBody().setLinearVelocity(getBody().getLinearVelocity().x + velocity, getBody().getLinearVelocity().y);
                                            }
                                            if (animations.animator.ani_finished()) {
                                                usingOnlyLastFrame = true;
                                                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                                                    animations = Animations.WALKING;
                                                    usingOnlyLastFrame = false;
                                                } else {
                                                    animations = Animations.IDLE;
                                                    usingOnlyLastFrame = false;
                                                    }
                                            }
                                        } else {
                                            getBody().setFixedRotation(false);
                                            usingOnlyLastFrame = false;
                                            hited = false;
                                            animations = Animations.IDLE;
                                            getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void dispose(){
        super.dispose();

        animations.getAnimator().dispose();
        world.dispose();
    }



    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A && PowerBar.sp >= 30){
            animations = Animations.SABER;
            SABER.play();
            Animations.SABER.animator.stateTime = 0f;
            getBody().setFixedRotation(true);
            usingOnlyLastFrame = false;
        }
        if (keycode == Input.Keys.S){
            animations = Animations.SHOT;

        }
        if (keycode == Input.Keys.D){
           animations = Animations.PUNCH;
           JUMP.play();
//           PolygonShape ps = new PolygonShape();
//           ps.setAsBox(30,10, new Vector2(facingRight ? WIDTH/2f + 50 : WIDTH/2f - 50, HEIGHT/2f + 50), 0);
//           box.fixtureDef3.shape = ps;
//           getBody().createFixture(box.fixtureDef3);d
        }
        if (keycode == Input.Keys.RIGHT){
            animations = Animations.WALKING;
            flip = false;
            if (getBody().getLinearVelocity().x < 20)
                getBody().setLinearVelocity(getBody().getLinearVelocity().x + 20, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.LEFT){
            animations = Animations.WALKING;
            flip = true;
            if (getBody().getLinearVelocity().x > -20)
                getBody().setLinearVelocity(-20, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.SPACE){
            usingOnlyLastFrame = false;
            if (!animations.name().equals("IDLE_FLASH") && Math.abs(getBody().getLinearVelocity().y) > 3f){
                animations = Animations.HIYAH;
                HIYAH.play();
                usingOnlyLastFrame = true;
//                getBody().getFixtureList().get(0).setFriction(10f);
            } else{
                animations = Animations.JUMPING;
                setStateTime(0);
            }
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D){
//            getBody().destroyFixture(box.getBody().getFixtureList().get(3));
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT)
            getBody().setLinearVelocity(new Vector2(0,getBody().getLinearVelocity().y));
        if (keycode == Input.Keys.A){
            if (PowerBar.sp >= 30)
                PowerBar.sp -= 30;
        }
        return false;
    }


    public boolean keyTyped(char character) {
        return false;
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       BodyDef bodyDef = new BodyDef();
       bodyDef.type = BodyDef.BodyType.DynamicBody;
       bodyDef.active = true;
       bodyDef.position.set(getBody().getPosition().x + 300, new Random().nextFloat(768f)); // Posição inicial
       CircleShape shape = new CircleShape();
       shape.setRadius(new Random().nextFloat(200f));
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = shape;
       fixtureDef.density = 1.0f;
       fixtureDef.friction = 0.1f;
       MassData m = new MassData();

       Body body = world.createBody(bodyDef);
       body.createFixture(fixtureDef);
       body.setActive(true);
       m.mass = 1_000_000f;
       body.setMassData(m);
       return false;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    public boolean scrolled(float amountX, float amountY) {
        return false;
    }




    public void render(ShapeRenderer s) {

        for (Bullet b : bullets)
            b.render(s);
        if (animations.name().equals("PUNCH")) {
            if (frameCounter() > 2)
                action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f : getBody().getPosition().x + WIDTH/2f -80,
                    getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
//          s.rect(getBody().getPosition().x + WIDTH/2f, getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
//          s.rect(getBody().getPosition().x + WIDTH / 2f - 80, getBody().getPosition().y + HEIGHT / 2f + 40, 80, 20);
        } else{
            if (animations.name().equals("HIYAH")){
                action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f + 50 : getBody().getPosition().x + WIDTH/2f - 100, getBody().getPosition().y + HEIGHT/2f - 40, 50, 50);
            } else{
                if (animations.name().equals("SABER") && PowerBar.sp >= 30){
                    Timer timer = new Timer();
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            action = new Rectangle(0, 0, 0, 0);
                            animations = Animations.IDLE;
                            usingOnlyLastFrame = false;
                        }
                    }, 2.2f);
                    action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f + 30 : getBody().getPosition().x + WIDTH/2f - 110,
                            getBody().getPosition().y + HEIGHT/2f - 50, 80, 130);
                } else {
                    action = new Rectangle(0, 0, 0, 0);
                }
            }
        }
//        action = getBodyBounds();
        s.rect(action.x, action.y, action.width, action.height);
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);

//        System.out.println(getBodyBounds().x + " " + getBodyBounds().y);
    }

    public Rectangle getAction() {
        return action;
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public float frameCounter(){
        return animations.animator.getAnimation().getKeyFrame(stateTime).getU2() * animations.animator.getNumFrames();
    }


    public boolean isStand() {
        return stand;
    }


}
