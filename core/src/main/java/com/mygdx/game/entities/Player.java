package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Player_Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.*;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.items.minis.Minis;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.platform.FinalRopeKnot;
import com.mygdx.game.sfx.Sounds;
import com.mygdx.game.system.ScreenshotHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.images.Images.legs;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.equipped;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.items;
import static com.mygdx.game.manager.StateManager.setStates;
import static com.mygdx.game.screens.Stats.exp_Points;
import static com.mygdx.game.screens.levels.Level.ninjaStars;
import static com.mygdx.game.screens.levels.Level.world;
import static com.mygdx.game.screens.levels.Level_Manager.*;
import static com.mygdx.game.sfx.Sounds.*;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;
import static com.mygdx.game.platform.FinalRopeKnot.mouseBody;

public class Player extends Objeto{

    public static final float WIDTH = 128, HEIGHT = 128f;
    public static Array<Minis> minis = new Array<>();
    public static boolean lvlUP;
    public static final float BOX_WIDTH = 50f, BOX_HEIGHT = 95f;
    @Getter @Setter
    private Viewport viewport;
    public static float worldX, worldY;
    @Getter @Setter
    private boolean looping, useOnlyLastFrame;
    public static float velocityX = 5_000f, timerLvlUP;
    private boolean walking, usingWeapon, laser_attack, shooting, sword, punching, saber, throwing_fire, thrown_ninjaStar;
    protected Mouse mouse;
    private ArrayList<Body> attacking_box_bodies = new ArrayList<>();

    private Array<Laser> laser_rail = new Array<>();

    public static Rifle rifle;

    public static ArrayList<Fire> fire_objects = new ArrayList<>();
    private float degrees, radians;

    public static Character_Features character_features = new Character_Features();

    private Sprite headsetlaser;
    private float flickering_time;
    private boolean hit;
    private boolean punch;
    public static boolean ropeShoot;

    @Getter
    private FinalRopeKnot fragment;
    private boolean jetPackBoolean;
    public static boolean thrownStar;

    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(BOX_WIDTH/2f, BOX_HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, this.toString(), 0.1f);
        body.setFixedRotation(true);
        visible = true;
        mass(5.0f, new Vector2((BOX_WIDTH/2f) - 5, BOX_HEIGHT/2f), 0f);
        isFacingRight = true;
        this.viewport = viewport;
        changeAnimation("IDLE");
        rifle = new Rifle(new Vector2(10_000, 20_000));
        isFacingRight = true;
        mouse = new Mouse(getBody().getPosition());
    }


    @Override
    public void render(SpriteBatch s) {
        renderJetPack(s);
        super.render(s);
        if (beenHit)
            character_features.drawDamage(s, font, body);
        character_features.update(this);
        renderAnimation(s);
        renderMinis(s);
        renderLaser(s);
        updateBaseLevel(s);
        switching(s);
        renderFireBall(s);
        updateWalking();
        renderFragment(s);
    }

    public void renderFragment(SpriteBatch s){
        if (fragment != null) {
            fragment.render(s);
        }
    }

    private void renderJetPack(SpriteBatch s){
        for (Item item : items.keySet()) {
            if (equipped[item.getIndex()]) {
                if (item.toString().contains("Jet")){
                   jetPackBoolean = true;
                }
            }
        }
        if (jetPackBoolean) {
            Sprite jet = new Sprite(Player_Animations.JETPACK.animator.currentSpriteFrameUpdateStateTime(false, true, !isFacingRight));
            jet.draw(s);
        }
    }

    private void updateWalking() {
        if (onGround && isMovingXAxis())
            walking = true;
    }

    private void renderFireBall(SpriteBatch s) {
        if (!fire_objects.isEmpty()) {
            for (Fire fire : fire_objects) {
                fire.render(spriteBatch);
            }
        }
    }

    private void switching(SpriteBatch s){
        if (!animationName().contains("STRICKEN")) {
            if (throwing_fire) {
                throwing_fire();
            }
            if (usingWeapon && !animationName().equals("WALKING_SWORD")) {
                if (rifle != null)
                    rifle.update();
                resetAnimationWeapon();
                aim();
                switchWeaponAnimation(s);
            } else {
                if (punching) {
                    punching();
                } else {
                    if (isMovingXAxis() || walking) {
                        walking();
                    } else {
                        idle();
                    }
                }
            }
        }
        updateBeenHit();
    }

    private void throwing_fire() {
        if (animationName().equals("THROWING_FIRE")) {
            if (isFinishedCurrentAnimation()) {
                throwing_fire = false;
                resetCurrentAnimation();
                fire_objects.add(new Fire(new Vector2(isFacingRight ? body.getPosition().x + 50f : body.getPosition().x - 50f, body.getPosition().y + 20f), isFacingRight));
            }
        }

    }

    private void punching(){
        attackingBodiesUpdate();
        if (punching && animation().animator.isAnimFinished()) {
            punching = false;
            punch = false;
            resetCurrentAnimation();
            for (int i = 0; i < attacking_box_bodies.size(); i++) {
                if (attacking_box_bodies.get(i) != null) {
                    attacking_box_bodies.get(i).setTransform(new Vector2(10_000, 10_000), 0);
                }
            }
            changeAnimation("IDLE");
        }
    }
    private void updateBeenHit() {
        if (animationName().equals("STRICKEN")) {
//            float forceX = 10;
//            body.applyForceToCenter(playerBodyXPositionHigherThanAnotherBody ? forceX : -forceX, 0, true);
//            changeAnimation("STRICKEN");

            flickering_time += Gdx.graphics.getDeltaTime();
            System.out.println(flickering_time);
            if (flickering_time >= 1.5f){
                flickering_time = 0f;
                isScale = false;
                beenHit = false;
                walking = false;
                changeAnimation("IDLE");
            }
        }
    }

    @Override
    public void beenHit(){
        super.beenHit();
        if (!animationName().equals("STRICKEN") && beenHit) {
            Sounds.HURT.play();
            changeAnimation("STRICKEN");
        }
    }

    private void updateBaseLevel(SpriteBatch spriteBatch){
        if (Player.lvlUP) {
            Sprite sprite = new Sprite(Player_Animations.LVL_UP.animator.currentSpriteFrameUpdateStateTime(false,false, !isFacingRight));
            sprite.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
            sprite.draw(spriteBatch);
            timerLvlUP += Gdx.graphics.getDeltaTime();
            if (timerLvlUP >= Player_Animations.LVL_UP.animator.totalTime){
                Player.lvlUP = false;
                EAGLE.play();
                exp_Points = 1;
                timerLvlUP = 0;
            }
        }
    }

    private void renderLaser(SpriteBatch spriteBatch){
        if (!laser_rail.isEmpty()) {
            for (Laser laser : laser_rail)
                laser.render(spriteBatch);
        }
    }

    private void renderAnimation(SpriteBatch s){
        Sprite sprite = new Sprite(animation().getAnimator().currentSpriteFrame(useOnlyLastFrame,
            looping || walking || animationName().equals("STRICKEN"), !isFacingRight));
        sprite.setOriginCenter();
        setBodyPosition(sprite);
        sprite.draw(s);
    }

    private void setBodyPosition(Sprite sprite) {
        if (animationName().equals("ATTACKING_SWORD_FIRE_2"))
            sprite.setPosition(isFacingRight ? body.getPosition().x - 20f: body.getPosition().x - 192f + 40f, body.getPosition().y - BOX_HEIGHT/2f);
        else
            sprite.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
    }

    private void renderMinis(SpriteBatch s){
        for (Minis m : minis){
            m.render(s);
            m.update();
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    public void update(){
       super.update();
        updateAnimation();
        respawn();
        whichOneEquip();
        updateFireBalls();

    }

    private void updateFireBalls() {
        if (!fire_objects.isEmpty()) {
            for (Fire fire : fire_objects) {
                fire.update();
            }
        }
    }

    private void resetAnimationWeapon() {
        if (isFinishedCurrentAnimation() && (shooting || laser_attack || sword || ropeShoot || thrown_ninjaStar)) {
           if (animationName().contains("SWORD_FIRE") || animationName().contains("SABER")) {
               resetCurrentAnimation();
               hit = false;
           }
        }
    }

    private void updateAnimation() {
        animation().getAnimator().update();
//        System.out.println(animationName() + " " + walking);
    }

    private void attackingBodiesUpdate() {
        addPunchingAttackBody();
    }

    private void respawn(){
        if (body != null) {
            if (currentLevelName.contains("1") || currentLevelName.contains("2")) {
                if (body.getPosition().y < -100) {
                    beenHit();
                    body.setTransform(new Vector2(100, 400), 0);
                }
            }
        }
    }

    private void walking(){
        if (animationName().contains("WALKING") || animationName().contains("LEGS") ){
//            walking = true;
            if (isFinishedCurrentAnimation())
                resetCurrentAnimation();
            if (!isMovingXAxis()) {
                resetCurrentAnimation();
                walking = false;
//                changeAnimation("IDLE");

            }
        }
        if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f && !onGround()){
            changeAnimation("JUMPING");
        }
    }

    private String whichOneEquip(){
        for (Item item : items.keySet()) {
            if (equipped[item.getIndex()]) {
                System.out.println("using weapon: " + item.toString());
                usingWeapon = true;
                return item.toString();
            }
        }
        usingWeapon = false;
        return "";
    }

    private void switchWeaponAnimation(SpriteBatch spriteBatch){
         switch (whichOneEquip()){
            case "Sword":{
                sword = true;
                changeAnimation("ATTACKING_SWORD_FIRE_2");
                shooting = false;
                laser_attack = false;
                saber = false;
                thrown_ninjaStar = false;
                if (animationName().contains("ATTACKING_SWORD")){
//                    for (Body body1 : attacking_box_bodies) {
//                        body1.setTransform(new Vector2(isFacingRight ? (getBody().getPosition().x + WIDTH / 2f) - 20f :
//                        getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50), 0f);
//                    }
                    if ((frameCounter() >= 5)){
                        Body body1 = BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + (WIDTH/2f):
                                getBody().getPosition().x - (WIDTH / 2f) - 5, getBody().getPosition().y + (HEIGHT / 2f) - 50),
                            new Vector2(20, 40f), BodyDef.BodyType.KinematicBody, false, " Boy", 1f);
//                        body.applyForceToCenter(new Vector2(isFacingRight ? 500 : - 500, 0f), true);
                        attacking_box_bodies.add(body1);

                    } if (frameCounter() > 10){
                        for (Body attacking_box_body : attacking_box_bodies) {
                            if (attacking_box_body != null) {
                                attacking_box_body.setTransform(new Vector2(10_000, 10_000), 0);
                            }
                        }
                    }
                }
                if (isMovingXAxis() && !hit) {
                    changeAnimation("WALKING_SWORD");
                }
                if (!isMovingXAxis() && !hit) {
                    changeAnimation("SWORD");
                }
                break;
            }
            case "Rifle":{
                rifle.render(spriteBatch);
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                shooting = true;
                sword = false;
                laser_attack = false;
                saber = false;
                thrown_ninjaStar = false;
                Rifle.showingNumbBullets = true;
                top = new Sprite(Player_Animations.valueOf("RELOADING").getAnimator().currentSpriteFrameUpdateStateTime(!rifle.isReloading(), rifle.isReloading(), !isFacingRight));
                top.setOriginCenter();
                top.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                legs = new Sprite(Player_Animations.valueOf("LEGS_ONLY").getAnimator().currentSpriteFrameUpdateStateTime(!isMovingXAxis(), isMovingXAxis(), !isFacingRight));
                if (!isMovingXAxis())
                    Player_Animations.valueOf("LEGS_ONLY").getAnimator().setFrameCounter(0);
                top.setRotation(degrees);
                if (Math.abs(degrees) > 90f && Math.abs(degrees) < 180f)
                    top.setRotation(-Math.abs(180f - degrees));
                top.setFlip(Math.abs(degrees) > 90f, false);
                legs.setFlip(Math.abs(degrees) > 90f, false);
                isFacingRight = (Math.abs(degrees) < 90f);
                legs.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                legs.draw(spriteBatch);
                top.draw(spriteBatch);
                changeAnimation("NONE");
//                oldAnimation = "NONE";
                break;
            }
            case "Saber":{
                sword = false;
                shooting = false;
                laser_attack = false;
                thrown_ninjaStar = false;
                saber = true;
                changeAnimation("SABER");
                break;
            } case "Star":{
                 ropeShoot = false;
                 laser_attack = false;
                 sword = false;
                 shooting = false;
                 saber = false;
                 thrown_ninjaStar = true;

                 if (!thrownStar) {
                     throwNinjaStar1.setOriginCenter();
                     throwNinjaStar1.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
//                     throwNinjaStar1.setRotation(degrees);
                     throwNinjaStar1.setFlip(!isFacingRight, false);
                     throwNinjaStar1.draw(spriteBatch);
                 } else{
                     throwNinjaStar2.setOriginCenter();
                     throwNinjaStar2.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                     throwNinjaStar2.setFlip(!isFacingRight, false);
                     throwNinjaStar2.draw(spriteBatch);
                 }
                 for (Star star : ninjaStars)
                     star.render(spriteBatch);
                 spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                 changeAnimation("NONE");
                 legs = new Sprite(Player_Animations.valueOf("LEGS_ONLY").getAnimator().currentSpriteFrameUpdateStateTime(!isMovingXAxis(), isMovingXAxis(), !isFacingRight));
                 legs.setFlip(!isFacingRight, false);
                 if (!isMovingXAxis())
                     Player_Animations.valueOf("LEGS_ONLY").getAnimator().setFrameCounter(0);
                 legs.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                 legs.draw(spriteBatch);
                 thrownStar = false;
                 break;
             }
            case "Laser_Headset":{
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                laser_attack = true;
                sword = false;
                shooting = false;
                saber = false;
                thrown_ninjaStar = false;
                headsetlaser = new Sprite(Player_Animations.HEADSET.getAnimator().currentSpriteFrameUpdateStateTime(false,
                    true, !isFacingRight));
                headsetlaser.setPosition(isFacingRight ? body.getPosition().x - 5f : body.getPosition().x - 10f, body.getPosition().y + 12f);
                headsetlaser.setOriginCenter();
                headsetlaser.setRotation(degrees);
                if (Math.abs(degrees) > 90f) {
                    headsetlaser.setRotation(-Math.abs(180f - degrees));
                }
                headsetlaser.setFlip(Math.abs(degrees) > 90f, false);
                isFacingRight = !(Math.abs(degrees) > 90f);
                if (!animationName().equals("JUMPING_FRONT_LASER"))
                    headsetlaser.draw(spriteBatch);
                if (!onGround && !isMovingXAxis() && !hit)
                    changeAnimation("JUMPING_FRONT_LASER");
                break;
            }
             case "NinjaRope":{
                ropeShoot = true;
                laser_attack = false;
                sword = false;
                shooting = false;
                saber = false;
                thrown_ninjaStar = false;
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                legs = new Sprite(Player_Animations.valueOf("LEGS_ONLY").getAnimator().currentSpriteFrameUpdateStateTime(!isMovingXAxis(), isMovingXAxis(), !isFacingRight));
                if (!isMovingXAxis())
                    Player_Animations.valueOf("LEGS_ONLY").getAnimator().setFrameCounter(0);
                legs.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                isFacingRight = (degrees < 90f && degrees > -90f);
                ninjaRope_shoot.setOriginCenter();
                ninjaRope_shoot.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                ninjaRope_shoot.setRotation(degrees);
                if (Math.abs(degrees) > 90f && Math.abs(degrees) < 180f)
                    ninjaRope_shoot.setRotation(-Math.abs(180f - degrees));
                ninjaRope_shoot.setFlip(Math.abs(degrees) > 90f, false);
                legs.setFlip(!isFacingRight, false);
//                  jetPackSprite.setFlip(Math.abs(degrees) < 100f, false);
                legs.draw(spriteBatch);
                changeAnimation("NONE");
                ninjaRope_shoot.draw(spriteBatch);
                break;
            }

        }
    }

    private void aim(){
        if (laser_attack || shooting || ropeShoot || thrown_ninjaStar) {
            float dx = worldX - Math.abs(body.getPosition().x + 64);
            float dy = worldY - Math.abs(body.getPosition().y + 64);
            degrees = (float) Math.abs(Math.toDegrees(Math.atan2(dy, dx)));
            radians = (float) Math.toRadians(degrees);
        }
        if (thrown_ninjaStar){
            isFacingRight = (degrees < 90 || degrees > 270);
        }
    }

    private boolean isMovingXAxis(){
        return Math.abs(body.getLinearVelocity().x) > 0f;
    }

    private boolean isFinishedCurrentAnimation(){
        return animation().animator.finishedAnimation;
    }

    private void resetCurrentAnimation(){
        animation().getAnimator().resetAnimation();
    }

    private String animationName(){
        return animation().name();
    }

    private void idle(){
//        System.out.println(onGround);
        if (!beenHit) {
            if (!onGround()) {
                if (!isMovingXAxis()) {
                    if (laser_attack) {
                        changeAnimation("JUMPING_FRONT_LASER");
                    } else {
                        changeAnimation("JUMPING_FRONT");
                    }
                }
                if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f) {
                    changeAnimation("JUMPING");
                    walking = false;
                }
            }
            if (onGround()) {
                if (isntMovingAxisXnorY() && isFinishedCurrentAnimation())
                    changeAnimation("IDLE");
            }
        }
    }

    private boolean isntMovingAxisXnorY(){
        return Math.abs(getBody().getLinearVelocity().x) == 0f && Math.abs(getBody().getLinearVelocity().y) == 0f;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    public void addPunchingAttackBody(){
        if (animationName().contains("PUNCHING") && !punch) {
            attacking_box_bodies.add(BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH/2f :
                    getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50),
                new Vector2(10, 40f), BodyDef.BodyType.KinematicBody, true, "Boy", 0f));
            punch = true;
        }
    }

    public float frameCounter(){
        return Player_Animations.currentAnimation.animator.frameCounter();
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
            hit = false;
            body.applyForceToCenter(new Vector2(keycode == Input.Keys.A ? -velocityX : velocityX, 0), true);
//            body.applyForce(new Vector2(keycode == Input.Keys.D ? velocityX : -velocityX, 0f), getBody().getWorldCenter(), true);
//            body.setLinearVelocity(new Vector2(15,0));
            isFacingRight = (keycode == Input.Keys.D);
//            if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f && !onGround()){
//                changeAnimation("JUMPING");
//                walking = false;
//            }
            if (onGround()){
                if (sword)
                    changeAnimation("WALKING_SWORD");
                else
                    changeAnimation("WALKING");
                if (!walking) {
                    walking = true;
//                    animation().getAnimator().resetAnimation();
                }
            }

        }
        if (keycode == Input.Keys.SPACE) {
//            walking = false;
            JUMP.play();
            body.applyForceToCenter(0, character_features.getJumpingStrength() ,true);
            if (!isMovingXAxis()){
                if (laser_attack)
                    changeAnimation("JUMPING_FRONT_LASER");
                else
                    changeAnimation("JUMPING_FRONT");
//                looping = true;
            } else {
                if (getBody().getLinearVelocity().y != 0 && getBody().getLinearVelocity().x != 0) {
                    changeAnimation("JUMPING");
//                walking = false;
                }
            }
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
            takeScreenshot();
            setStates(StateManager.States.INVENTORY);
        }
        if (keycode == Input.Keys.Q){
            ScreenshotHelper.takeScreenshot();
            StateManager.setStates(StateManager.States.STATS);
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
//            if (!animationName().contains("FIRE")) {
//                if (!onGround()){
//                    if (isMovingXAxis())
//                        changeAnimation("JUMPING_FRONT");
//                    else
//                        changeAnimation("JUMPING");
//                    walking = false;
//                }
//            }
            if (animationName().contains("WALKING")){
//                walking = false;
                if (onGround)
                    changeAnimation("IDLE");
            }

        }
        if (keycode == Input.Keys.F) {
            if (PowerBar.sp_0 > 0) {
                changeAnimation("THROWING_FIRE");
                throwing_fire = true;
                character_features.setSp(Math.max(character_features.getSp() -10f, 0));
            }
        }
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public void mouseMoved(int screenX, int screenY){
//        mouse.mouseMoved(screenX, screenY);

        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (fragment != null) {
            fragment.touchDown(screenX, screenY, pointer, button);
        }
        if (mouse != null)
            mouse.touchDown(screenX, screenY, button);
        if (button == Input.Buttons.LEFT) {
            walking = false;
            if (thrown_ninjaStar) {
                ninjaStars.add(new Star(new Vector2(isFacingRight ? ((getBody().getPosition().x +
                    WIDTH / 2f) + 50) : (getBody().getPosition().x - 50),
                    getBody().getPosition().y + HEIGHT / 2f),
                    radians, false));
                thrownStar = true;
            } else {
                if (laser_attack) {
                    laser_rail.add(new Laser(
                        new Vector2(isFacingRight ? (getBody().getPosition().x +
                            WIDTH / 2f) : (getBody().getPosition().x - WIDTH / 4f),
                            isFacingRight ? (getBody().getPosition().y + (HEIGHT / 4f) - 20f) : (getBody().getPosition().y + (HEIGHT / 2f))),
                        radians >= (Math.PI / 2f), radians, this.toString()));
                    LASER_HEADSET.play();
                    character_features.setPower(character_features.getPower() - 5f);
                    hit = true;
                } else {
                    if (shooting) {
                        if (!rifle.isReloading()) {
                            if (!rifle.getLeftSideBullets().getBulletsLeft().isEmpty()) {
                                Bullet bullet = new Bullet(
                                    new Vector2(isFacingRight ? (getBody().getPosition().x +
                                        WIDTH / 2f) : (getBody().getPosition().x),
                                        (getBody().getPosition().y + HEIGHT / 4f)),
                                    isFacingRight, radians, false, this.toString());
                                rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                            }
                        }
                    } else {
                        if (ropeShoot) {
                            if (fragment != null && !fragment.body.isActive())
                                world.destroyBody(fragment.body);
                            mouseBody = box(new Vector2(worldX, worldY), new Vector2(2f, 2f), BodyDef.BodyType.StaticBody, true, "mouse");
                            fragment = new FinalRopeKnot(new Vector2(isFacingRight ? (getBody().getPosition().x + WIDTH / 2f) :
                                (getBody().getPosition().x),
                                (getBody().getPosition().y)),
                                isFacingRight, radians);
                        } else {
                            if (usingWeapon && sword && !hit) {
                                changeAnimation("ATTACKING_SWORD_FIRE_2");
                                body.setLinearVelocity(0, getBody().getLinearVelocity().y);
                                hit = true;
                            } else {
//                    if (animationName().contains("SWORD") || sword) {
//                        if (!isMovingXAxis())
//
//

//                    else {
//                        if (!punching) {
                                punching = true;
                                changeAnimation("PUNCHING_FIRE");
                                walking = false;
                                body.setLinearVelocity(0, getBody().getLinearVelocity().y);
                            }
                        }
                    }
                }
            }
        }
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



    public void takeItem(Item item){
        if (item.isVisible()) {
            item.setVisible(false);
            new ItemToBeDrawn(item);
            TRIGGER.play();
        }
    }

    public Rectangle getBodyBounds() {
        return new Rectangle(body.getPosition().x + width/2f - 30f, body.getPosition().y + height/2f - 50f, BOX_WIDTH, BOX_HEIGHT);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Player_Animations animation(){
        return Player_Animations.currentAnimation;
    }

    public void changeAnimation(String name) {
        if (!animationName().contains("PUNCHING")){
            punch = false;
            for (Body attackingBoxBody : attacking_box_bodies) {
                if (attackingBoxBody != null && attackingBoxBody.getUserData().toString().contains("Boy")) {
//                    attackingBoxBody.setTransform(new Vector2(10_000, 10_000), 0);
                    attackingBoxBody.setUserData("");
                }
            }
        }
        if (!animationName().equals(name))
            Player_Animations.changeAnimation(name);

//        if (!animationName().contains("WALKING") && !animationName().contains("SWORD"))
//            walking = false;
//        if (!animationName().contains("SWORD"))
//            sword = false;
    }

    @Override
    public void beginContact(Body body1, Body body2){
        super.beginContact(body1, body2);
        if (fragment != null) {
            fragment.beginContact(body1, body2);

        }
        for (Fire fire : fire_objects){
            fire.beginContact(body1, body2);
        }

        if ((body1.equals(body) && body2.getUserData().toString().contains("Enemy"))
            || (body2.equals(body) && body1.getUserData().toString().contains("Enemy"))){
            applyForceToBody(body1, body2);
            beenHit();
        }
        if (body1.equals(body) && body2.getUserData().toString().contains("Colliders")
            || body2.equals(body) && body1.getUserData().toString().contains("Colliders")) {
            beenHit();
        }


    }

    private void applyForceToBody(Body body1, Body body2){
        float yForce = 300f;
        if (body1.getUserData().toString().contains("Enemy")){
            Vector2 force = new Vector2(left_or_right(getBody(), body1), yForce);
            Vector2 point = getBody().getWorldCenter(); // aplica no centro de massa
            getBody().applyForce(force, point, true);

            Vector2 force2 = new Vector2(left_or_right(body1, getBody()), yForce);
            Vector2 point2 = getBody().getWorldCenter(); // aplica no centro de massa
            body1.applyForce(force2, point2, true);

        } else {
            if (body2.getUserData().toString().contains("Enemy")) {
                Vector2 force = new Vector2(left_or_right(getBody(), body2), yForce);
                Vector2 point = getBody().getWorldCenter(); // aplica no centro de massa
                getBody().applyForce(force, point, true);

                Vector2 force2 = new Vector2(left_or_right(body2, getBody()), yForce);
                Vector2 point2 = getBody().getWorldCenter(); // aplica no centro de massa
                body2.applyForce(force2, point2, true);
            }
        }
    }

    private float left_or_right(Body bodyA, Body bodyB){
        float xForce = 5f;
        return bodyA.getPosition().x > bodyB.getPosition().x ? xForce : -xForce;
    }

}
