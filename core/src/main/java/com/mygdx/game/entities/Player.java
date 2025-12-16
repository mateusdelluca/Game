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
import com.mygdx.game.sfx.Sounds;
import com.mygdx.game.system.ScreenshotHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.images.Images.legs;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.equipped;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.items;
import static com.mygdx.game.manager.StateManager.setStates;
import static com.mygdx.game.screens.Stats.exp_Points;
import static com.mygdx.game.screens.levels.Level_Manager.*;
import static com.mygdx.game.sfx.Sounds.*;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public class Player extends Objeto{

    public static final float WIDTH = 128, HEIGHT = 128f;
    public static Array<Minis> minis = new Array<>();
    public static boolean lvlUP;
    public static final float BOX_WIDTH = 50f, BOX_HEIGHT = 95f;
    @Getter @Setter
    private Viewport viewport;
    @Getter @Setter
    private float worldX, worldY;
    @Getter @Setter
    private boolean looping, useOnlyLastFrame;
    public static float velocityX = 5_000f, timerLvlUP;
    private boolean walking, usingWeapon, laser_attack, shooting, sword, punching, saber, throwing_fire;

    private ArrayList<Body> attacking_box_bodies = new ArrayList<>();

    private Array<Laser> laser_rail = new Array<>();

    public static Rifle rifle;

    public static ArrayList<Fire> fire_objects = new ArrayList<>();
    private float degrees, radians;
    private String oldAnimation = "IDLE";
    public Character_Features character_features = new Character_Features();

    private Sprite headsetlaser;
    private float flickering_time;
    private boolean hit;

    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(BOX_WIDTH/2f, BOX_HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, "Boy", 0.1f);
        body.setFixedRotation(true);
        visible = true;
        mass(5.0f, new Vector2((BOX_WIDTH/2f) - 5, BOX_HEIGHT/2f), 0f);
        isFacingRight = true;
        this.viewport = viewport;
        changeAnimation("IDLE");
        rifle = new Rifle(new Vector2(10_000, 20_000));
        isFacingRight = true;
    }


    @Override
    public void render(SpriteBatch s) {
        super.render(s);
        if (beenHit)
            character_features.drawDamage(s, font, body);
        renderAnimation(s);
        renderMinis(s);
        renderLaser(s);
        updateBaseLevel(s);
        switching(s);
        updateBeenHit(s);
        renderFireBall(s);
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
            if (usingWeapon) {
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
        if (isFinishedCurrentAnimation()) {
            punching = false;
            resetCurrentAnimation();
            changeAnimation("WALKING");
        }
        for (Body attacking_box_body : attacking_box_bodies) {
            if (attacking_box_body != null) {
                attacking_box_body.setTransform(new Vector2(10_000, 10_000), 0);
            }
        }
    }
    private void updateBeenHit(SpriteBatch s) {
        if (beenHit) {
            float forceX = 10;
            body.applyForceToCenter(playerBodyXPositionHigherThanAnotherBody ? forceX : -forceX, 0, true);
            changeAnimation("STRICKEN");

            flickering_time += Gdx.graphics.getDeltaTime();
            if (flickering_time >= 0.5f){
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

        if (animation() != Player_Animations.STRICKEN) {
            setBeenHit(true);
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
        Sprite sprite = new Sprite(animation().getAnimator().currentSpriteFrame(useOnlyLastFrame, looping || walking, !isFacingRight));
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
        if (isFinishedCurrentAnimation() && (shooting || laser_attack || sword)) {
           if (animationName().contains("SWORD_FIRE") || animationName().contains("SABER")) {
               resetCurrentAnimation();
               hit = false;
           }
        }
    }

    private void updateAnimation() {
        animation().getAnimator().update();
    }

    private void attackingBodiesUpdate() {
        attackUsingBodies();
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
                changeAnimation("IDLE");
            }
        }
        if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f){
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
                if (animationName().contains("ATTACKING_SWORD")){
                    for (Body body1 : attacking_box_bodies) {
                        body1.setTransform(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH / 2f - 40f :
                        getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50), 0f);
                    }
                    if ((frameCounter() >= 5 && frameCounter() <= 7) || frameCounter() > 9){
                        Body body1 = BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH/2f - 20f:
                                getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50),
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
                Rifle.showingNumbBullets = true;
                top = new Sprite(Player_Animations.valueOf("RELOADING").getAnimator().currentSpriteFrameUpdateStateTime(!rifle.isReloading(), rifle.isReloading(), !isFacingRight));
                top.setOriginCenter();
                top.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                legs = new Sprite(Player_Animations.valueOf("LEGS_ONLY").getAnimator().currentSpriteFrameUpdateStateTime(!isMovingXAxis(), isMovingXAxis(), !isFacingRight));
                if (!isMovingXAxis())
                    Player_Animations.valueOf("LEGS_ONLY").getAnimator().setFrameCounter(0);
                top.setRotation(degrees);
                if (Math.abs(degrees) > 90f)
                    top.setRotation(-Math.abs(180f - degrees));
                top.setFlip(Math.abs(degrees) > 90f, false);
                legs.setFlip(Math.abs(degrees) > 90f, false);
                isFacingRight = !(Math.abs(degrees) > 90f);
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
                saber = true;
                changeAnimation("SABER");
                break;
            }
            case "Laser_Headset":{
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                laser_attack = true;
                sword = false;
                shooting = false;
                saber = false;
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
                break;
            }
        }
    }

    private void aim(){
        if (laser_attack || shooting) {
            float dx = worldX - Math.abs(body.getPosition().x + 64);
            float dy = worldY - Math.abs(body.getPosition().y + 64);
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
            radians = (float) Math.atan2(dy, dx);
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
        if (!usingWeapon && !beenHit) {
            if (!onGround) {
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

    public void attackUsingBodies(){
        if (animationName().equals("PUNCHING_FIRE")) {
            if (animation().getAnimator().stateTime > 0.30 && animation().getAnimator().stateTime < 0.32) {
                attacking_box_bodies.add(BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH/2f :
                        getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50),
                    new Vector2(10, 40f), BodyDef.BodyType.KinematicBody, true, " Boy", 0f));
            }
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
            if (!animationName().contains("WALKING") && onGround()){
                if (sword)
                    changeAnimation("WALKING_SWORD");
                else
                    changeAnimation("WALKING");
                walking = true;
                animation().getAnimator().resetAnimation();
            }
            if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f && !onGround){
                changeAnimation("JUMPING");
                walking = false;
            }
        }
        if (keycode == Input.Keys.SPACE) {
            walking = false;
            JUMP.play();
            body.applyForceToCenter(0, character_features.getJumpingStrength() ,true);
            if (!isMovingXAxis()){
                if (laser_attack)
                    changeAnimation("JUMPING_FRONT_LASER");
                else
                    changeAnimation("JUMPING_FRONT");
//                looping = true;
            }
            if (getBody().getLinearVelocity().y != 0f && getBody().getLinearVelocity().x != 0f){
                changeAnimation("JUMPING");
                walking = false;
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
            if (!animationName().contains("FIRE")) {
                body.setLinearVelocity(0f, body.getLinearVelocity().y);
                if (!onGround()){
                    if (isMovingXAxis())
                        changeAnimation("JUMPING_FRONT");
                    else
                        changeAnimation("JUMPING");
                    walking = false;
                }
            }
//            if (animationName().contains("WALKING"))
//                walking = false;
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
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (button == Input.Buttons.LEFT) {
            walking = false;
            if (laser_attack) {
                if (PowerBar.power >= character_features.getPowerSpent()) {
                    laser_rail.add(new Laser(
                        new Vector2(isFacingRight ? (getBody().getPosition().x +
                            WIDTH / 2f) : (getBody().getPosition().x - WIDTH / 4f),
                            isFacingRight ? (getBody().getPosition().y + (HEIGHT / 4f) - 20f) : (getBody().getPosition().y + (HEIGHT/2f))),
                        radians > (Math.PI / 2f), radians, this.toString()));
                    LASER_HEADSET.play();
                    PowerBar.power -= character_features.getPowerSpent();
                }
            } else {
                if (shooting) {
//                    if (!rifle.isReloading()) {
//                        if (!rifle.getLeftSideBullets().getBulletsLeft().isEmpty()) {
                            Bullet bullet = new Bullet(
                                new Vector2(isFacingRight ? (getBody().getPosition().x +
                                    WIDTH / 2f) : (getBody().getPosition().x),
                                    (getBody().getPosition().y + HEIGHT / 2f)),
                                !isFacingRight, radians, false, this.toString());
                            rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
//                        }
//                    }
                } else {
                    if (usingWeapon && sword && !hit){
                        changeAnimation("ATTACKING_SWORD_FIRE_2");
                        hit = true;
                    }
//                    if (animationName().contains("SWORD") || sword) {
//                        if (!isMovingXAxis())
//
//

//                    else {
//                        if (!punching) {
//                            punching = true;
//                            changeAnimation("PUNCHING_FIRE");
//                            body.applyForceToCenter(new Vector2(isFacingRight ? 10_000 : -10_000, 0), true);
//                        }
                    }
                }
            }
//            resetCurrentAnimation();
//        }
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
        return "Boy";
    }

    public Player_Animations animation(){
        return Player_Animations.currentAnimation;
    }

    public void changeAnimation(String name) {
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
