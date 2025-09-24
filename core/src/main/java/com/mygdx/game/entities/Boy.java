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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.*;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;


import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.screens.levels.Level.items;
import static com.mygdx.game.screens.levels.Level_Manager.spriteBatch;
import static com.mygdx.game.sfx.Sounds.*;

public class Boy extends Objeto {

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 1_000f, JUMP_VELOCITY = 70f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip0, usingOnlyLastFrame, looping = true, init;
    @Getter @Setter
    private boolean facingLeft;
    private float punchingAnimationTimer;
    public static float BOX_WIDTH = 60f, BOX_HEIGHT = 95f;
    public static final Vector2 DIMENSIONS_FOR_SHAPE = new Vector2(BOX_WIDTH, BOX_HEIGHT);
    private Rectangle actionRect = new Rectangle();
    private float flickering_time;
    @Setter @Getter
    private boolean beenHit;
    public static boolean shooting;
    public static float imgX, imgY, degrees, radians;
    private float dx, dy;
    private int secondJump;
    private float saberTime;
    private boolean hit;
    private int counterWeaponTaken;
    private float worldX;
    private float worldY;
    @Getter @Setter
    private transient Viewport viewport;

    public static boolean use_jetPack;

    private Vector2 jetPackPosition;
    private float chargingSPTimer;
    private float chargingSPTimer2;
    private float chargingSPTimer3;
    private boolean chargingSP;
    @Getter
    private Rifle rifle;
    private Vector2 bodyPosition;

    public static String nameOfAnimation;
    public static boolean throwing, saber_taken;
    private boolean thrown;
    private int indexNinja;
    private float throwTimer;
    public static boolean ropeShoot;
    private boolean onGround;
    private float delta;
    public static boolean laser;

    private Array<Laser> laser_rail = new Array<>();
    private Body punch_box;
    private boolean mortal;
    private boolean punching;

    public Boy(Vector2 bodyPosition, Viewport viewport){
        super(WIDTH, HEIGHT);
        this.bodyPosition = bodyPosition;
        body = createBody(new Vector2((DIMENSIONS_FOR_SHAPE.x/2f), DIMENSIONS_FOR_SHAPE.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(bodyPosition, 0);;
        body.setUserData(getClass().getSimpleName());
        bodyData.userData = "" + body.getUserData();
        this.viewport = viewport;
        jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10f);
        mass(1.0f, new Vector2((DIMENSIONS_FOR_SHAPE.x/2f) - 5, DIMENSIONS_FOR_SHAPE.y/2f), 1.0f);
        rifle = new Rifle(new Vector2(-10_000, -20_000));
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        if (mortal){
            mortalSprite.rotate(-30f);
            mortalSprite.setPosition(body.getPosition().x, body.getPosition().y);
            mortalSprite.draw(spriteBatch);
        } else{
        if (use_jetPack){   //when actives jetPack
            jetPackSprite = new Sprite(Animations.BOY_JETPACK.getAnimator().currentSpriteFrame(false, true, flip0));
            jetPackSprite.setPosition(Math.abs(degrees) > 90f ? body.getPosition().x + 10f : body.getPosition().x, body.getPosition().y + 10f);
            jetPackSprite.draw(spriteBatch);
        }
        if (body.getPosition().y < -100f) {
            beenHit();
            body.setTransform(new Vector2(100, 400), 0);
        }
        if (beenHit) { //when take a damage and stay flickering
            Sprite flickering = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
            flickering.setPosition(body.getPosition().x, body.getPosition().y);
            flickering.draw(spriteBatch);
        } else {
            if ((!shooting && !throwing && !ropeShoot && !laser) || saber_taken) {  //when he is not shooting and even has been hit. Uses animations method to recognize physics of this sprite
                Sprite anim = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping && !animations.name().equals("BOY_SABER"), flip0));
                anim.setPosition(body.getPosition().x, body.getPosition().y);
                anim.draw(spriteBatch);
            } else {
                if (!laser) {
                    legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.getFrame(0));
                    if (isMoving()) //when he is moving and didn't active the jetpack
                        legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingLeft));
                    legs.setPosition(body.getPosition().x, body.getPosition().y);
                }
                if (shooting && !laser) {    //when actives the gun and shooting and he is not moving and he has not been hit
                    //BOY SPRITE TOP
//            Sprite top = new Sprite(Images.shooting1); !Cartridge.reloading
                    top = new Sprite(Animations.BOY_RELOADING.animator.currentSpriteFrame(!rifle.isReloading(), rifle.isReloading(), facingLeft));
                    top.setOriginCenter();
                    top.setRotation(degrees);
                    top.setPosition(bodyPosition.x, bodyPosition.y);
                    if (Math.abs(degrees) > 90f) {
                        top.setRotation(-Math.abs(180f - degrees));
                        top.flip(true, false);
                        legs.flip(true, false);
                        jetPackSprite.flip(true, false);
                    } else {
                        top.flip(false, false);
                        legs.flip(false, false);
                        jetPackSprite.flip(false, false);
                    }

                    legs.draw(spriteBatch);
//                    s.draw(top, body.getPosition().x, body.getPosition().y);
//                    top.rotate(degrees);
                    top.draw(spriteBatch);
//                    aim.setPosition(worldX - 13, worldY - 13);    //these negatives numbers are there to aim the center of mouse
                    spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                }
            }
            if (throwing && !shooting && !beenHit && !saber_taken) {
                if (Math.abs(degrees) > 100f) {
                    throwNinjaStar1.setFlip(true, false);
                    throwNinjaStar2.setFlip(true, false);
                    legs.setFlip(true, false);
                    jetPackSprite.setFlip(true, false);
                    setFacingLeft(true);
                }
                if (Math.abs(degrees) < 100f) {
                    throwNinjaStar1.setFlip(false, false);
                    throwNinjaStar2.setFlip(false, false);
                    legs.setFlip(false, false);
                    jetPackSprite.setFlip(false, false);
                    setFacingLeft(false);
                }
                throwNinjaStar1.setPosition(body.getPosition().x, body.getPosition().y);
                legs.draw(spriteBatch);
                if (!thrown)
                    throwNinjaStar1.draw(spriteBatch);
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
            }
            if (thrown) {
                throwNinjaStar2.setPosition(body.getPosition().x, body.getPosition().y);
                throwNinjaStar2.draw(spriteBatch);
                delta += Gdx.graphics.getDeltaTime();
                if (delta > 100)
                    thrown = false;
            }
            if (ropeShoot) {
                if (isMoving()) //when he is moving and didn't active the jetpack
                    legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingLeft));
                legs.setPosition(body.getPosition().x, body.getPosition().y);
                ninjaRope_shoot.setOriginCenter();
                ninjaRope_shoot.setRotation(degrees);
                ninjaRope_shoot.setPosition(bodyPosition.x, bodyPosition.y);
                if (Math.abs(degrees) > 100f) {
                    ninjaRope_shoot.setRotation(-Math.abs(180f - degrees));
                    ninjaRope_shoot.setFlip(true, false);
                    legs.setFlip(true, false);
                    jetPackSprite.setFlip(true, false);
                    setFacingLeft(true);
                }
                if (Math.abs(degrees) < 100f) {
                    ninjaRope_shoot.setFlip(false, false);
                    legs.setFlip(false, false);
                    jetPackSprite.setFlip(false, false);
                    setFacingLeft(false);
                }
                legs.draw(spriteBatch);
                ninjaRope_shoot.draw(spriteBatch);
//                s.draw(shoot, worldX - 13, worldY - 9);
            } else {
                if (laser) {
                    animations = Animations.BOY_WALKING;
                    if (!isMoving())
                        animations.animator.setStateTime(0);
                    if (isMoving() || onGround) {
                        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(false,
                            true, facingLeft));
                        sprite.setPosition(bodyPosition.x, bodyPosition.y);
                        sprite.draw(spriteBatch);
                        flipAndRender(new Sprite(Animations.BOY_HEADSET.animator.currentSpriteFrame(false,
                                true, facingLeft)),
                            new Vector2(bodyPosition.x + 45f, bodyPosition.y + 61f));
                    }
                    if (!isMoving() && !onGround) {
                        animations = Animations.BOY_JUMPING_FRONT_LASER;
                        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(false, true));
                        sprite.setPosition(body.getPosition().x, body.getPosition().y);
                        sprite.draw(spriteBatch);
                    }
                }
            }
        }
        if (!laser_rail.isEmpty()) {
            for (Laser laser : laser_rail)
                laser.render(spriteBatch);
        }
        }
    }

    public void flipAndRender(Sprite sprite, Vector2 itemPosition){
        spriteBatch.draw(shoot, worldX - 13, worldY - 9);
//        legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.getFrame(0));
//        if (isMoving()) //when he is moving and didn't active the jetpack
//            legs = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingLeft));
//        legs.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setOriginCenter();
//
        sprite.setPosition(itemPosition.x, itemPosition.y);
        if (degrees > 91f && degrees < 181f) {
            sprite.setRotation(-Math.abs(180 - Math.abs(degrees)));
//            legs.setFlip(true, false);
            jetPackSprite.setFlip(true, false);
            sprite.setFlip(true, false);
            facingLeft = true;
//            radians = (float) Math.toRadians(degrees);
        } else{
            if (degrees < 90f && degrees >= 0f){
                sprite.setRotation(degrees);
                sprite.setFlip(false, false);
//                legs.setFlip(false, false);
                jetPackSprite.setFlip(false, false);
                facingLeft = false;
//                radians = (float) Math.toRadians(degrees);
            }
        }
//        legs.draw(spriteBatch);
        sprite.draw(spriteBatch);
    }

    public void update(){
        super.update();
        if (punching)
            punchingAnimationTimer += Gdx.graphics.getDeltaTime();
        if (punchingAnimationTimer >= 0.3f){
            animations = Animations.BOY_IDLE;
            punchingAnimationTimer = 0f;
            punch_box.setTransform(new Vector2(-2000, -2000), 0);
            punch_box = null;
            punching = false;
        }
        if (onGround)
            mortal = false;
        if (body.getLinearVelocity().y != 0)
            onGround = false;
        if (Math.abs(getBody().getLinearVelocity().x) > VELOCITY_X)
            getBody().setLinearVelocity((Math.abs(getBody().getLinearVelocity().x) / (getBody().getLinearVelocity().x))
                *  (VELOCITY_X), getBody().getLinearVelocity().y);
        this.bodyPosition = body.getPosition();
        if (thrown)
            throwTimer += Gdx.graphics.getDeltaTime();
        if (throwTimer > 1f) {
            thrown = false;
            throwing = true;
            throwTimer = 0;
        }

        shooting = Rifle.showingNumbBullets;
        fly();      //check if he is using jetPack and fly away and when its pressed space and sp > 0
        animations();   //gives orders of physics of body on animations
        actionRect = actionRect();
        if (flickering_time >= 1.0f && beenHit) {  //the timer of 1second to normalize after has been hit
            animations = Animations.BOY_IDLE;
            flickering_time = 0f;
            beenHit = false;
        }

       aim();  //the commands and precision of pointing and shoot
        if (Math.abs(getBody().getLinearVelocity().y) < 0.5f){
            secondJump = 0;
        }
    }

    private void fly() {
        if (PowerBar.sp > 10 && input.isKeyPressed(Input.Keys.SPACE) && use_jetPack){
            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 1);
        }
        if (use_jetPack && PowerBar.sp > 0) {
            chargingSPTimer3 += Gdx.graphics.getDeltaTime();
            if (chargingSPTimer3 > 0.2f) {
                PowerBar.sp--;
                chargingSPTimer3 = 0;
            }
        }
        if (PowerBar.sp <= 30f){
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
        if (shooting) {
//            imgX = Gdx.graphics.getWidth() / 2f;
//            imgY = Gdx.graphics.getHeight() / 2f;
            float dx = worldX - Math.abs(bodyPosition.x + 64);
            float dy = worldY - Math.abs(bodyPosition.y + 64);
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
            radians = (float) Math.atan2(dy, dx);
        } else {
            if (throwing || ropeShoot) {
                float dx = worldX - Math.abs(bodyPosition.x + 64 + (!facingLeft ? 50 : -50));
                float dy = worldY - Math.abs(bodyPosition.y + 64);
                degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
                radians = (float) Math.atan2(dy, dx);
            } else{
                if (laser){ //TODO float radians
                    float dx = worldX - Math.abs(bodyPosition.x + 64);
                    float dy = worldY - Math.abs(bodyPosition.y + 80);
                    degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
                    radians = (float) Math.atan2(dy, dx);
                }
            }
        }
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
            if (!beenHit) {
                if (name.equals("BOY_SABER") && !shooting) {
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
                        if (punch_box == null) {
                            punch_box = BodiesAndShapes.box(new Vector2(!flip0 ? body.getPosition().x + 100 : body.getPosition().x + 30, body.getPosition().y + 50f), new Vector2(20f, 50f), BodyDef.BodyType.StaticBody, false);
                            punch_box.setUserData("Punch");
                            punching = true;
                        }

                    } else {
                        if (name.equals("BOY_WALKING") || name.equals("BOY_SHOOTING_AND_WALKING") || name.equals("BOY_RELOADING") || name.equals("BOY_JETPACK")
                        || animations == Animations.BOY_RELOADING || throwing) {
                            if (laser){
                                if (!isMoving()){
                                    animations = Animations.BOY_WALKING;
                                    animations.animator.setFrameCounter(0);
                                }
                            }
                        } else {
                            if (onGround() && !use_jetPack) {
                                if (isMoving())
                                    animations = Animations.BOY_WALKING;
                                else
                                    animations = Animations.BOY_IDLE;
                                //                   usingOnlyLastFrame = true;
                            } else {
                                if (isMoving() || use_jetPack) {
                                    animations = Animations.BOY_JUMPING;
                                }
                                else {
                                    if (!laser)
                                        animations = Animations.BOY_JUMPING_FRONT;
                                    else
                                        animations = Animations.BOY_JUMPING_FRONT_LASER;
                                }
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
//            body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y + 20);
            getBody().applyForceToCenter(new Vector2(0f, 1_000f), true);
            body.setGravityScale(0f);
        }
        if (keycode == Input.Keys.J){
            throwing = !throwing;
        }
        if (keycode == Input.Keys.R){
            if (rifle != null) {
                if (!rifle.isReloading() && !rifle.isButtonReloadingPressed()) {
                    rifle.setButtonReloadingPressed(true);
                    rifle.setReloading(true);
                    rifle.reloading();
                }
            }
        }

        if (keycode == Input.Keys.I){
            StateManager.setStates(StateManager.States.INVENTORY);
        }

//        if (keycode == Input.Keys.T) {
//            use_jetPack = !use_jetPack;
//            if (use_jetPack) {
//                jetPackPosition = new Vector2(body.getPosition().x, body.getPosition().y + 10);
////                body.setGravityScale(0.5f);
//                JETPACK.loop(0.4f);
//            }
//            else {
////                jetPackSprite.setPosition(body.getPosition().x, body.getPosition().y - 10);
//                body.setGravityScale(1f);
//                JETPACK.stop();
//            }
//        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.A){
            if (Math.abs(getBody().getLinearVelocity().x) < VELOCITY_X) {
                body.applyForce(new Vector2(keycode == Input.Keys.D ? VELOCITY_X : -VELOCITY_X, 0f), getBody().getWorldCenter(), true);
                facingLeft = keycode == Input.Keys.A;
            }if (!shooting) {
                if (!facingLeft) {
//                    degrees = 0f;
//                    radians = 0f;
                }
                flip0 = keycode == Input.Keys.A;
                if (throwing){
                    throwNinjaStar1.setFlip(flip0, false);
                }
            }
            if (!beenHit && !shooting && !use_jetPack && !mortal) {
                animations = Animations.BOY_WALKING;
            }
            usingOnlyLastFrame = false;
            looping = true;
        }
        if (!beenHit && !mortal) {
            if (keycode == Input.Keys.SPACE) {
                if (Math.abs(getBody().getLinearVelocity().x) < 15f && !use_jetPack)
                    animations = Animations.BOY_JUMPING_FRONT;
                if (Math.abs(getBody().getLinearVelocity().x) >= 15f || use_jetPack)
                    animations = Animations.BOY_JUMPING;

//                body.setLinearVelocity(body.getLinearVelocity().x, JUMP_VELOCITY);

//                }
                JUMP.play();
            }
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.D || keycode == Input.Keys.A){
            body.setLinearVelocity(0f,
                body.getLinearVelocity().y);
            if (!beenHit && !shooting && !use_jetPack && !mortal)
                animations = Animations.BOY_IDLE;

        }
        if (keycode == Input.Keys.SPACE && use_jetPack) {
            onGround = false;
            body.setGravityScale(0.4f);
        }
        if (keycode == Input.Keys.Y){
            if (Math.abs(body.getLinearVelocity().y) <= 0 || !onGround) {
                if (!mortal)
                    body.applyForceToCenter(new Vector2(0f, 4000f), true);
                body.setLinearVelocity(new Vector2(isFacingLeft() ? -10f : 10f, body.getLinearVelocity().y));
                mortal = true;
            }
            onGround = false;
        }
        if (keycode == Input.Keys.SPACE) {
            if (!beenHit && secondJump < 1 && onGround) {
                onGround = false;
                if (!use_jetPack) {
                    secondJump++;
                    body.setGravityScale(1f);
//                    body.setLinearVelocity(getBody().getLinearVelocity().x, JUMP_VELOCITY);
                    body.applyForce(new Vector2(0f, 5_000f), getBody().getWorldCenter(), true);
                }
            }
        }
//        System.out.println(body.getLinearVelocity().y);
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
                if (!rifle.isReloading()) {
                    if (!rifle.getLeftSideBullets().getBulletsLeft().isEmpty()) {
                        Bullet bullet = new Bullet(
                            new Vector2(!facingLeft ? (getBody().getPosition().x +
                            WIDTH / 2f) : (getBody().getPosition().x),
                            (getBody().getPosition().y + HEIGHT / 2f)),
                            facingLeft, radians, true, this.toString());
                        rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                    }
                }
            }
            else {
                if (throwing && !shooting && !beenHit && !saber_taken && !laser) {
                    thrown = true;
                    items.put(NinjaStar.class.getSimpleName() + indexNinja++, new NinjaStar(new Vector2(!facingLeft ? ((getBody().getPosition().x +
                        WIDTH / 2f) + 50) : (getBody().getPosition().x - 50),
                        getBody().getPosition().y + HEIGHT / 2f),
                        radians, false));
                } else {
                    if (!shooting && !beenHit && !saber_taken && !laser) { //punches
                        punchingAnimationTimer = 0f;
                        animations = Animations.BOY_PUNCHING;
                        JUMP.play();
                        HIYAH.play();
                        usingOnlyLastFrame = false;
                        looping = false;
                        animations.animator.resetStateTime();
                    } else{
                        if (saber_taken && PowerBar.sp >= 20f) {  //hits
                            hit = true;
                            PowerBar.sp -= 20f;
                            SABER.play();
                            animations = Animations.BOY_SABER;
                            setFrameCounter(0);
                            getBody().setLinearVelocity(!flip0 ? VELOCITY_X * 5 : -VELOCITY_X * 5, getBody().getLinearVelocity().y);
                        }
                            if (laser) {
                                        laser_rail.add(new Laser(
                                            new Vector2(!facingLeft ? (getBody().getPosition().x +
                                                WIDTH / 2f) : (getBody().getPosition().x - WIDTH/4f),
                                                !facingLeft ? (getBody().getPosition().y + (HEIGHT/2f)) : (getBody().getPosition().y + (HEIGHT))),
                                            radians > Math.PI/2f, radians, this.toString()));
                                LASER_HEADSET.play();
                        }
                    }
                }
            }
        }
        if (button == Input.Buttons.RIGHT) {
//            counterWeaponTaken++;
//            switch(counterWeaponTaken){
//                case 1:{
//                    Rifle.showingNumbBullets = false;
//                    shooting = false;
//                    throwing = false;
//                    saber_taken = true;
//                    animations = Animations.BOY_SABER;
//                    break;
//                }
//                case 2:{
//                    shooting = false;
//                    beenHit = false;
//                    saber_taken = false;
//                    animations = Animations.BOY_IDLE;
//                    Rifle.showingNumbBullets = false;
//                    break;
//                }
//                case 3:{
//                    shooting = false;
//                    beenHit = false;
//                    saber_taken = false;
//                    throwing = true;
//                    Rifle.showingNumbBullets = false;
//                    break;
//                }
//                default:{
//                    saber_taken = false;
//                    throwing = false;
//                    if (rifle != null) {
//                        Rifle.showingNumbBullets = true;
//                        shooting = true;
//                        counterWeaponTaken = 0;
//                        animations = Animations.BOY_RELOADING;
//                        degrees = 0;
//                        radians = 0;
//                        beenHit = false;
//                        break;
//                    }
//                    Rifle.showingNumbBullets = false;
//                    shooting = false;
//                    animations = Animations.BOY_IDLE;
//                    counterWeaponTaken = 0;
//                    break;
//                }
//            }
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

    @Contract
    public void takeItem(Item item){
        if (item instanceof Portal)
            return;
        if (item instanceof Rifle) {
            rifle = (Rifle) items.get(item.toString());
            ((Rifle) item).setVisible(false);
//            body.applyForce(new Vector2(1_000_000_000f, 1_000_000_000f), body.getPosition(), true);
        }
        if (item instanceof Crystal) {
            if (((Crystal) item).getRand() > 0)
                PowerBar.sp += 10;
            else
                PowerBar.hp += 10;
            clink2.play();
//            System.out.println(item);
            item.setVisible(false);
            return;
        }
        if (item instanceof JetPack) {
//            use_jetPack = true;
            ((JetPack) item).setVisible(false);
        }
        if (item instanceof NinjaStar){
            item.setVisible(false);
        }
        if (item instanceof NinjaRope){
            item.setVisible(false);
        }
        if (item instanceof Laser_Headset) {
            item.setVisible(false);
        }
        ItemToBeDrawn itemToBeDrawn = new ItemToBeDrawn(item.toString());
        if (item.toString().contains("NinjaStar")) {
            if (item instanceof NinjaStar) {
                ((NinjaStar) item).setItemToBeDrawn(itemToBeDrawn);
            }
        }
        item.setVisible(false);
        TRIGGER.play();
    }

    @Override
    public void beenHit(){
        if (animations != Animations.BOY_STRICKEN) {
//            getBody().setLinearVelocity(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y + 40f);
            animations = Animations.BOY_STRICKEN;
            PowerBar.hp -= 10;
            setBeenHit(true);
            Sounds.HURT.play();
        }
    }

    @Override
    public void beginContact(Body body1, Body body2) {
//        super.beginContact(body1, body2);
//        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
//            return;
//        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
//            return;
//        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
//            return;

//        Body body1 = contact.getFixtureA().getBody();
//        Body body2 = contact.getFixtureB().getBody();

        if (body1 == null || body2 == null)
            return;

        if ((body1.getUserData().toString().equals("Boy") && body2.getUserData().toString().contains("Rects")
            || body2.getUserData().toString().equals("Boy") && body1.getUserData().toString().contains("Rects")) ||
        (body1.getUserData().toString().equals("Boy") && body2.getUserData().toString().contains("Block")
            || body2.getUserData().toString().equals("Boy") && body1.getUserData().toString().contains("Block"))
//            || (body1.getUserData().toString().equals("Boy") && body2.getUserData().toString().contains("Thorns")
//                || body2.getUserData().toString().equals("Boy") && body1.getUserData().toString().contains("Thorns"))
//            || (body1.getUserData().toString().equals("Boy") && body2.getUserData().toString().contains("Enemy")
//            || body2.getUserData().toString().equals("Boy") && body1.getUserData().toString().contains("Enemy"))){
        ){
            onGround = true;
        }

        if ((body1.equals(body) && body2.getUserData().toString().contains("Enemy"))
            || (body2.equals(body) && body1.getUserData().toString().contains("Enemy"))){
            applyForceToBody(body1, body2);
            onGround = true;
            beenHit();
        }
        if (body1.equals(body) && body2.getUserData().toString().contains("Colliders")
            || body2.equals(body) && body1.getUserData().toString().contains("Colliders")) {
            onGround = true;
            beenHit();
        }
    }

    private void applyForceToBody(Body body1, Body body2){
        if (body1.getUserData().toString().contains("Enemy")){
            Vector2 force = new Vector2(left_or_right(getBody(), body1), 1_000.0f); // força para direita
            Vector2 point = getBody().getWorldCenter(); // aplica no centro de massa
            getBody().setLinearVelocity(0,0);
            getBody().applyForce(force, point, true);

            Vector2 force2 = new Vector2(left_or_right(body1, getBody()), 1_000.0f); // força para direita
            Vector2 point2 = getBody().getWorldCenter(); // aplica no centro de massa
            body1.setLinearVelocity(0,0);
            body1.applyForce(force2, point2, true);

        } else {
            if (body2.getUserData().toString().contains("Enemy")) {
                Vector2 force = new Vector2(left_or_right(getBody(), body2), 1_000.0f); // força para direita
                Vector2 point = getBody().getWorldCenter(); // aplica no centro de massa
//                getBody().setLinearVelocity(0,0);
                getBody().applyForce(force, point, true);

                Vector2 force2 = new Vector2(left_or_right(body2, getBody()), 1_000.0f); // força para direita
                Vector2 point2 = getBody().getWorldCenter(); // aplica no centro de massa
//                body2.setLinearVelocity(0,0);
                body2.applyForce(force2, point2, true);
            }
        }
    }

    private float left_or_right(Body bodyA, Body bodyB){
       return bodyA.getPosition().x > bodyB.getPosition().x ? 1_000.0f : -1_000f;
    }
}
