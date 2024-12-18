package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.Images;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.screens.levels.Level.bullets;
import static com.mygdx.game.sfx.Sounds.*;

public class Boy extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 20f, JUMP_VELOCITY = 70f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip0, usingOnlyLastFrame, looping = true, init;
    private boolean flip;
    private float punchingAnimationTimer;
    public static float BOX_WIDTH = 65f, BOX_HEIGHT = 95f;
    private final Vector2 dimensions = new Vector2(BOX_WIDTH, BOX_HEIGHT);
    private Rectangle actionRect = new Rectangle();
    private float flickering_time;
    @Setter @Getter
    private boolean stricken;
    private boolean shooting;
    private float imgX, imgY, degrees, radians, dx, dy;
    private Vector2 test;
    private Vector2 position;
    private int secondJump;
    private float saberTime;
    private boolean saber_taken, hit;
    private int counterWeaponTaken;
    private float worldX;
    private float worldY;
    private Viewport viewport;
    private boolean jetPack;
    private Sprite jetPackSprite;
    private Vector2 jetPackPosition;
    public Boy(World world, Vector2 position, Viewport viewport){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2((dimensions.x/2f) - 5, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
        this.position = position;
        this.viewport = viewport;
        jetPackSprite = new Sprite(Animations.BOY_JETPACK.getAnimator().currentSpriteFrame(false, true, flip0));
        jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10f);

    }

    public void render(SpriteBatch s){
        update();
        if (jetPack){
            jetPackSprite = new Sprite(Animations.BOY_JETPACK.getAnimator().currentSpriteFrame(false, true, flip0));
            jetPackSprite.setPosition(Math.abs(degrees) > 90f ? body.getPosition().x + 10f : body.getPosition().x, body.getPosition().y + 10f);
            jetPackSprite.draw(s);
        }
        if (stricken) {
            Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
        }
        if (!shooting && !stricken) {
            Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
        }
        if (shooting && !stricken) {
            Sprite sprite2 = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.getFrame(0));
            if (isMoving() && !jetPack)
                sprite2 = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, flip));
            sprite2.setPosition(body.getPosition().x, body.getPosition().y);

            Sprite sprite = new Sprite(Images.shooting1);
            sprite.setPosition(body.getPosition().x , body.getPosition().y);
            sprite.setRotation(degrees);
            if (Math.abs(degrees) > 90f) {
                sprite.setRotation(-Math.abs(180f - degrees));
                sprite.flip(true, false);
                sprite2.flip(true, false);
                jetPackSprite.flip(true, false);
            }
            else{
                sprite.flip(false, false);
                sprite2.flip(false, false);
                jetPackSprite.flip(false, false);
            }

            sprite.draw(s);
            sprite2.draw(s);
            Sprite sprite3 = new Sprite(Images.shoot);
            sprite3.setPosition(worldX - 3, worldY - 9);
            sprite3.draw(s);

        }

    }

    public void update(){
        method();
        animations();
        if (body.getPosition().x < 32){
            body.setTransform(32, body.getPosition().y, 0);
        }
        if (body.getPosition().y <= 400 && !Sounds.LEVEL1.isPlaying()) {
            Sounds.LEVEL1.play();
        }
        actionRect = actionRect();
        if (body.getPosition().y > 1000){
            body.setTransform(body.getPosition().x, 1000, 0);
        }
        if (flickering_time >= 2.0f) {
            animations = Animations.BOY_IDLE;
            flickering_time = 0f;
            stricken = false;
//          Sounds.HURT.stop();
        }

        aim();
        if (body.getPosition().y < 200 && jetPack){
            body.setTransform(body.getPosition().x, 200, 0);
        }
        if (body.getPosition().y < -200){
            body.setTransform(position,0f);
            animations = Animations.BOY_STRICKEN;
            setStricken(true);
        }
        if (Math.abs(getBody().getLinearVelocity().y) < 0.05f && !animations.name().equals("BOY_JUMPING")){
            secondJump = 0;
        }
    }

    private void method() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && jetPack){
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 1);
        }
    }

    private void aim(){
        if (shooting) {
//            imgX = Gdx.graphics.getWidth() / 2f;
//            imgY = Gdx.graphics.getHeight() / 2f;
            float dx = worldX - Math.abs(body.getPosition().x + 64) - 3;
            float dy = worldY - Math.abs(body.getPosition().y + 64) - 9;
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
//          System.out.println(degrees);
            radians = (float) Math.atan2(dy, dx);
        }
    }

    public Rectangle actionRect(){
        if (animations.name().equals("BOY_PUNCHING")) {
            if (frameCounter() >= 2)
                return new Rectangle(!flip0 ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                        getBody().getPosition().y + HEIGHT / 2f - 25, 45, 45);
        } else{
            if (animations.name().equals("BOY_SABER")) {
                if (frameCounter() <= 2)
                    return new Rectangle(!flip0 ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                            getBody().getPosition().y + HEIGHT / 2f - 25, 70, 45);
            }
        }
        return new Rectangle();
    }

    private void animations() {
        String name = animations.name();
//        if (name.equals("BOY_JUMPING")) {
//            if (body.getLinearVelocity().y < -29f) {
//                animations = Animations.BOY_IDLE;
//            }
//        } else{
        if (name.equals("BOY_STRICKEN")){
            flickering_time += Gdx.graphics.getDeltaTime();
//            System.out.println(flickering_time);
            if (flickering_time >= 1.2f) {
                animations = Animations.BOY_IDLE;
                flickering_time = 0f;
                stricken = false;
                Sounds.HURT.stop();
            }
        } else {
            if (name.equals("BOY_PUNCHING")) {
                punchingAnimationTimer += Gdx.graphics.getDeltaTime();
                if (punchingAnimationTimer >= 2f) {
                    animations = Animations.BOY_IDLE;
                    punchingAnimationTimer = 0f;
                }
            }
            if (!stricken) {
                if (name.equals("BOY_SABER")) {
                    if (saber_taken && !hit) {
                        setFrameCounter(0);
                    }
                    if (frameCounter() >= 2f) {
                        setFrameCounter(2);
                        saberTime += Gdx.graphics.getDeltaTime();
                        if (saberTime >= 0.5f) {
                            hit = false;
                            saberTime = 0f;
                            animations = Animations.BOY_IDLE;
                            getBody().setLinearVelocity(0f, getBody().getLinearVelocity().y);
                        }
                    }
                } else {
                    if (name.equals("BOY_PUNCHING")) {
                        punchingAnimationTimer += Gdx.graphics.getDeltaTime();
                        if (punchingAnimationTimer >= 2f) {
                            animations = Animations.BOY_IDLE;
                            punchingAnimationTimer = 0f;
                        }
                    } else {
                        if (name.equals("BOY_WALKING") || name.equals("BOY_SHOOTING_AND_WALKING") || name.equals("BOY_JETPACK")) {

                        } else {
                            if (onGround() && !jetPack) {
                                if (isMoving())
                                    animations = Animations.BOY_WALKING;
                                else
                                    animations = Animations.BOY_IDLE;
                                //                   usingOnlyLastFrame = true;
                            } else {
                                if (isMoving() || jetPack)
                                    animations = Animations.BOY_JUMPING;
                                else
                                    animations = Animations.BOY_JUMPING_FRONT;
                                //                    usingOnlyLastFrame = false;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean onGround(){
        return Math.abs(body.getLinearVelocity().y) <= 0.01f;
    }

    private boolean isMoving(){
        return Math.abs(body.getLinearVelocity().x) > 0.2f;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void render(ShapeRenderer s) {
//        s.rect(body.getPosition().x, body.getPosition().y, width, height);
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
        s.rect(actionRect.x, actionRect.y, actionRect.width, actionRect.height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.SPACE && jetPack) {
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 20);
            body.setGravityScale(0f);
        }
        if (keycode == Input.Keys.T) {
            jetPack = !jetPack;
            if (jetPack) {
                jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10);
//                body.setGravityScale(0.5f);
                JETPACK.loop(0.3f);
            }
            else {
//                jetPackSprite.setPosition(body.getPosition().x, body.getPosition().y - 10);
                body.setGravityScale(1f);
                JETPACK.stop();
            }
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.A){
            body.setLinearVelocity(keycode == Input.Keys.D ? VELOCITY_X : -VELOCITY_X, body.getLinearVelocity().y);
            if (!shooting) {
                if (!flip) {
                    degrees = 0f;
                    radians = 0f;
                }
                flip0 = keycode == Input.Keys.A;
            }
            if (!stricken && !shooting && !jetPack) {
                animations = Animations.BOY_WALKING;
            }
            usingOnlyLastFrame = false;
            looping = true;
        }
        if (!stricken) {
            if (keycode == Input.Keys.SPACE && !jetPack) {
                if (Math.abs(getBody().getLinearVelocity().x) < 15f && !jetPack)
                    animations = Animations.BOY_JUMPING_FRONT;
                if (Math.abs(getBody().getLinearVelocity().x) >= 15f || jetPack)
                    animations = Animations.BOY_JUMPING;
                if (secondJump < 2) {
                    getBody().setLinearVelocity(getBody().getLinearVelocity().x, JUMP_VELOCITY);
                    if (!jetPack)
                        secondJump++;
                }
                JUMP.play();
            }
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.D || keycode == Input.Keys.A){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
            if (!stricken && !shooting)
                animations = Animations.BOY_IDLE;
        }
        if (keycode == Input.Keys.SPACE && jetPack) {
            body.setGravityScale(0.2f);

        }
    }

    public void mouseMoved(int screenX, int screenY){
        if (shooting) {
//            dx = screenX - body.getPosition().x;
//            dy = (Gdx.graphics.getHeight() - screenY) - body.getPosition().y; // Invert Y-axis
//            angle = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
//            System.out.println(angle);
//            angle2 = (float) Math.atan2(dy, dx);

            Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);

            viewport.unproject(worldCoordinates);

            worldX = worldCoordinates.x;
            worldY = worldCoordinates.y;
        }
    }
//!flip ? getBody().getPosition().x +
//    WIDTH : getBody().getPosition().x
    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (button == Input.Buttons.LEFT) { //shoots
            if (shooting){
//                System.out.println(true);
                Bullet bullet = new Bullet(world, new Vector2(getBody().getPosition().x + WIDTH/2f,
                    getBody().getPosition().y + HEIGHT/2f), flip, radians, true);
                bullets.add(bullet);
                GUNSHOT.play();
            }
            if (!shooting && !stricken && !saber_taken){ //punches
                punchingAnimationTimer = 0f;
                animations = Animations.BOY_PUNCHING;
                JUMP.play();
                HIYAH.play();
                usingOnlyLastFrame = false;
                looping = false;
                animations.animator.resetStateTime();
            }
            if (saber_taken && PowerBar.sp >= 50f) {  //hits
                hit = true;
                PowerBar.sp -= 50f;
                SABER.play();
                animations = Animations.BOY_SABER;
                setFrameCounter(0);
                getBody().setLinearVelocity(!flip0 ? 50f : -50f, getBody().getLinearVelocity().y);
            }
        }
        if (button == Input.Buttons.RIGHT) {
            counterWeaponTaken++;
            switch(counterWeaponTaken){
                default:{
                    shooting = false;
                    saber_taken = false;
                    animations = Animations.BOY_IDLE;
                    counterWeaponTaken = 0;
                    break;
                }
                case 1:{
                    shooting = true;
                    saber_taken = false;
                    animations = Animations.BOY_SHOOTING_AND_WALKING;
                    break;
                }
                case 2:{
                    shooting = false;
                    saber_taken = true;
                    animations = Animations.BOY_SABER;
                    break;
                }

            }
//            shooting = !shooting;
//            degrees = 0f;
//            radians = 0f;
        }
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public float frameCounter(){
        return animations.animator.getAnimation().getKeyFrame(animations.animator.stateTime).getU2() * animations.animator.getNumFrames();
    }

    public Rectangle getBodyBounds() {
        return new Rectangle(body.getPosition().x + width/2f - 30f, body.getPosition().y + height/2f - 50f, dimensions.x, dimensions.y);
    }
}
