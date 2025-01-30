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
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.*;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.sfx.Sounds.*;

public class Boy extends Objeto {

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 20f, JUMP_VELOCITY = 70f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip0, usingOnlyLastFrame, looping = true, init;
    private boolean isFacingLeft;
    private float punchingAnimationTimer;
    public static float BOX_WIDTH = 65f, BOX_HEIGHT = 95f;
    public static final Vector2 DIMENSIONS_FOR_SHAPE = new Vector2(BOX_WIDTH, BOX_HEIGHT);
    private Rectangle actionRect = new Rectangle();
    private float flickering_time;
    @Setter @Getter
    private boolean beenHit;
    private boolean shooting;
    private float imgX, imgY, degrees, radians;
    private float dx, dy;
    private int secondJump;
    private float saberTime;
    private boolean saber_taken, hit;
    private int counterWeaponTaken;
    private float worldX;
    private float worldY;
    @Getter @Setter
    private transient Viewport viewport;
    @Getter @Setter
    private boolean use_jetPack;

    private Vector2 jetPackPosition;
    private float chargingSPTimer;
    private float chargingSPTimer2;
    private float chargingSPTimer3;
    private boolean chargingSP;
    @Setter @Getter
    private ArrayList<Item> items = new ArrayList<Item>();
    private Rifle rifle;
//    private JetPack jetPack;
    private Vector2 bodyPosition;
    private boolean buttonReloadingPressed;

    public static String nameOfAnimation;



    public Boy(Vector2 bodyPosition, Viewport viewport){
        super(WIDTH, HEIGHT);
        this.bodyPosition = bodyPosition;
        body = createBody(new Vector2((DIMENSIONS_FOR_SHAPE.x/2f) - 5, DIMENSIONS_FOR_SHAPE.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(bodyPosition, 0);;
        body.setUserData(getClass().getSimpleName());
        bodyData.userData = "" + body.getUserData();
        this.viewport = viewport;
        jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10f);
    }

    @Override
    public void render(SpriteBatch s){
        if (use_jetPack){   //when actives jetPack
            jetPackSprite = new Sprite(Animations.BOY_JETPACK.getAnimator().currentSpriteFrame(false, true, flip0));
            jetPackSprite.setPosition(Math.abs(degrees) > 90f ? body.getPosition().x + 10f : body.getPosition().x, body.getPosition().y + 10f);
            jetPackSprite.draw(s);
        }
        if (beenHit) { //when take a damage and stay flickering
            Sprite flickering = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
            flickering.setPosition(body.getPosition().x, body.getPosition().y);
            flickering.draw(s);
        } else{
            if ((!shooting) || saber_taken) {  //when he is not shooting and even has been hit. Uses animations method to recognize physics of this sprite
                Sprite anim = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
                anim.setPosition(body.getPosition().x, body.getPosition().y);
                anim.draw(s);
         } else{
                if (shooting) {    //when actives the gun and shooting and he is not moving and he has not been hit
                    legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.getFrame(0));
                    if (isMoving()) //when he is moving and didn't active the jetpack
                        legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, isFacingLeft));
                    legs.setPosition(body.getPosition().x, body.getPosition().y);

                    //BOY SPRITE TOP
//            Sprite top = new Sprite(Images.shooting1); !Cartridge.reloading
                    top = new Sprite(Animations.BOY_RELOADING.animator.currentSpriteFrame(!Cartridge.reloading, Cartridge.reloading, isFacingLeft));
                    top.setOriginCenter();
                    top.setRotation(degrees);
                    top.setPosition(body.getPosition().x, body.getPosition().y);
                    if (Math.abs(degrees) > 90f) {
                        top.setRotation(-Math.abs(180f - degrees));
                        top.flip(true, false);
                        legs.flip(true, false);
                        jetPackSprite.flip(true, false);
                    }
                    else{
                        top.flip(false, false);
                        legs.flip(false, false);
                        jetPackSprite.flip(false, false);
                    }

                    legs.draw(s);
                    s.draw(top, body.getPosition().x, body.getPosition().y);
//                    top.rotate(degrees);
                    top.draw(s);
//                    aim.setPosition(worldX - 13, worldY - 13);    //these negatives numbers are there to aim the center of mouse
                    s.draw(shoot, worldX - 13, worldY - 13);
                }
            }
        }
        for (Item item : items)
            item.render(s);
    }

    public void update(){
        this.bodyPosition = body.getPosition();
        shooting = Rifle.showingNumbBullets;
        fly();      //check if he is using jetPack and fly away and when its pressed space and sp > 0
        animations();   //gives orders of physics of body in animations
        actionRect = actionRect();
        if (flickering_time >= 1.0f && beenHit) {  //the timer of 1second to normalize after has been hit
            animations = Animations.BOY_IDLE;
            flickering_time = 0f;
            beenHit = false;
        }

       aim();  //the commands and precision of pointing and shoot
        if (Math.abs(getBody().getLinearVelocity().y) < 0.05f && !animations.name().equals("BOY_JUMPING")){
            secondJump = 0;
        }
    }

    private void fly() {
        if (PowerBar.sp > 10 && Gdx.input.isKeyPressed(Input.Keys.SPACE) && use_jetPack){
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 1);
        }
        if (use_jetPack && PowerBar.sp > 0) {
            chargingSPTimer3 += Gdx.graphics.getDeltaTime();
            if (chargingSPTimer3 > 0.2f) {
                PowerBar.sp--;
                chargingSPTimer3 = 0;
            }
        }
        if (PowerBar.sp <= 0f){
            chargingSP = true;
        }
        if (chargingSP) {
            chargingSPTimer += Gdx.graphics.getDeltaTime();
            if (chargingSPTimer > 3f) {
                chargingSPTimer2 += Gdx.graphics.getDeltaTime();
                if (chargingSPTimer2 > 0.3f) {
                    PowerBar.sp++;
                    chargingSPTimer2 = 0f;
                }
            }
            if (PowerBar.sp > 200f) {
                chargingSPTimer = 0f;
                chargingSPTimer2 = 0f;
                chargingSP = false;
            }
        }
    }

    private void aim(){
//        if (shooting) {
//            imgX = Gdx.graphics.getWidth() / 2f;
//            imgY = Gdx.graphics.getHeight() / 2f;
            float dx = worldX - Math.abs(body.getPosition().x + 64);
            float dy = worldY - Math.abs(body.getPosition().y + 64);
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
//          System.out.println(degrees);
            radians = (float) Math.atan2(dy, dx);
//        }
    }

    public Rectangle actionRect(){
        if (animations.name().equals("BOY_PUNCHING")) {
            if (frameCounter() >= 2)
                return new Rectangle(!flip0 ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                        getBody().getPosition().y + HEIGHT / 2f - 25, 45, 45);
        } else{
            if (animations.name().equals("BOY_SABER")) {
                if (frameCounter() > 0)
                    return new Rectangle(!flip0 ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                            getBody().getPosition().y + HEIGHT / 2f - 25, 70, 45);
            }
        }
        return new Rectangle();
    }

    private void animations() {
        String name = animations.name();
        nameOfAnimation = name;
        shooting = rifle.showingNumbBullets;
        if (name.equals("BOY_STRICKEN") || beenHit){
            flickering_time += Gdx.graphics.getDeltaTime();
//            System.out.println(flickering_time);
            if (flickering_time >= 1f) {
                animations = Animations.BOY_IDLE;
                flickering_time = 0f;
                beenHit = false;
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
            if (!beenHit) {
                if (name.equals("BOY_SABER")) {
                    if (saber_taken && !hit) {
                        setFrameCounter(0);
                    }
                    if (frameCounter() >= 2f) {
                        saberTime += Gdx.graphics.getDeltaTime();
                        if (saberTime < 0.4f)
                            setFrameCounter(2);
                       if (saberTime >= 0.4f)
                           setFrameCounter(3);
                        if (saberTime >= 0.5f) {
                            hit = false;
                            saberTime = 0f;
                            animations = Animations.BOY_IDLE;
                            getBody().setLinearVelocity(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y);
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
                        if (name.equals("BOY_WALKING") || name.equals("BOY_SHOOTING_AND_WALKING") || name.equals("BOY_RELOADING") || name.equals("BOY_JETPACK")
                        || animations == Animations.BOY_RELOADING) {
                        } else {
                            if (onGround() && !use_jetPack) {
                                if (isMoving())
                                    animations = Animations.BOY_WALKING;
                                else
                                    animations = Animations.BOY_IDLE;
                                //                   usingOnlyLastFrame = true;
                            } else {
                                if (isMoving() || use_jetPack)
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
        return Math.abs(body.getLinearVelocity().y) <= 0.1f;
    }

    private boolean isMoving(){
        return body.getLinearVelocity().x != 0f;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
//        s.rect(body.getPosition().x, body.getPosition().y, width, height);
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
        s.rect(actionRect.x, actionRect.y, actionRect.width, actionRect.height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.SPACE && use_jetPack && PowerBar.sp > 10) {
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 20);
            body.setGravityScale(0f);
        }

        if (keycode == Input.Keys.R){
            if (rifle != null) {
                if (!Cartridge.reloading && !rifle.isButtonReloadingPressed()) {
                    rifle.setButtonReloadingPressed(true);
                    Cartridge.reloading = true;
                    rifle.updateItem();
                }
            }
        }
        if (keycode == Input.Keys.T) {
            use_jetPack = !use_jetPack;
            if (use_jetPack) {
                jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10);
//                body.setGravityScale(0.5f);
                JETPACK.loop(0.4f);
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
                if (!isFacingLeft) {
//                    degrees = 0f;
//                    radians = 0f;
                }
                flip0 = keycode == Input.Keys.A;
            }
            if (!beenHit && !shooting && !use_jetPack) {
                animations = Animations.BOY_WALKING;
            }
            usingOnlyLastFrame = false;
            looping = true;
        }
        if (!beenHit) {
            if (keycode == Input.Keys.SPACE) {
                if (Math.abs(getBody().getLinearVelocity().x) < 15f && !use_jetPack)
                    animations = Animations.BOY_JUMPING_FRONT;
                if (Math.abs(getBody().getLinearVelocity().x) >= 15f || use_jetPack)
                    animations = Animations.BOY_JUMPING;
//                if (secondJump < 2) {
//                body.setLinearVelocity(getBody().getLinearVelocity().x, JUMP_VELOCITY);
                body.setLinearVelocity(body.getLinearVelocity().x, JUMP_VELOCITY);
                if (!use_jetPack)
                    secondJump++;
//                }
                JUMP.play();
            }
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.D || keycode == Input.Keys.A){
            body.setLinearVelocity((Math.abs(body.getLinearVelocity().x) - VELOCITY_X) * (Math.abs(body.getLinearVelocity().x) / (0.1f + body.getLinearVelocity().x)),
                body.getLinearVelocity().y);
            if (!beenHit && !shooting && !use_jetPack)
                animations = Animations.BOY_IDLE;
        }
        if (keycode == Input.Keys.SPACE && use_jetPack) {
            body.setGravityScale(0.2f);
        }
        System.out.println(body.getLinearVelocity().y);
    }

    public void mouseMoved(int screenX, int screenY){
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (button == Input.Buttons.LEFT) { //shoots
            if (shooting && Rifle.showingNumbBullets) {
                if (!Cartridge.reloading) {
                    if (!rifle.getLeftSideBullets().getBulletsLeft().isEmpty()) {
                        Bullet bullet = new Bullet(
                            new Vector2(!isFacingLeft ? (getBody().getPosition().x +
                            WIDTH / 2f) : (getBody().getPosition().x),
                            (getBody().getPosition().y + HEIGHT / 2f)),
                            isFacingLeft, radians, true);
                        rifle.getLeftSideBullets().addAndRemove(bullet);
                    }
                }
            }
            if (!shooting && !beenHit && !saber_taken) { //punches
                punchingAnimationTimer = 0f;
                animations = Animations.BOY_PUNCHING;
                JUMP.play();
                HIYAH.play();
                usingOnlyLastFrame = false;
                looping = false;
                animations.animator.resetStateTime();
            }
            if (saber_taken && PowerBar.sp >= 20f) {  //hits
                hit = true;
                PowerBar.sp -= 20f;
                SABER.play();
                animations = Animations.BOY_SABER;
                setFrameCounter(0);
                getBody().setLinearVelocity(!flip0 ? VELOCITY_X * 5 : -VELOCITY_X * 5, getBody().getLinearVelocity().y);
            }
        }
        if (button == Input.Buttons.RIGHT) {
            counterWeaponTaken++;
            switch(counterWeaponTaken){
                case 1:{
                    Rifle.showingNumbBullets = false;
                    shooting = false;
                    saber_taken = true;
                    animations = Animations.BOY_SABER;
                    break;
                }
                default:{
                    saber_taken = false;
                    if (rifle != null) {
                        Rifle.showingNumbBullets = true;
                        shooting = true;
                        counterWeaponTaken = 0;
                        animations = Animations.BOY_RELOADING;
                        degrees = 0;
                        radians = 0;
                        beenHit = false;
                        break;
                    }
                    Rifle.showingNumbBullets = false;
                    shooting = false;
                    animations = Animations.BOY_IDLE;
                    counterWeaponTaken = 0;
                    break;
                }
            }
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
        return new Rectangle(body.getPosition().x + width/2f - 30f, body.getPosition().y + height/2f - 50f, DIMENSIONS_FOR_SHAPE.x, DIMENSIONS_FOR_SHAPE.y);
    }

    public void equip_Item(Item item){
        if (item instanceof Portal)
            return;
        if (item instanceof Rifle) {
            rifle = (Rifle) item;
            Rifle.showingNumbBullets = true;
        }
        if (item instanceof Crystal) {
//            item = (Crystal) item;
            PowerBar.sp += 20;
            clink2.play();
        }
        if (item instanceof JetPack)
            use_jetPack = true;
        items.add(item);
        TRIGGER.play();
    }
}
