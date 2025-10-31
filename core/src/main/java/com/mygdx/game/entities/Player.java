package com.mygdx.game.entities;

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
import static com.mygdx.game.screens.Stats.char_features;
import static com.mygdx.game.sfx.Sounds.JUMP;
import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public class Player extends Objeto{

    public static final float WIDTH = 128, HEIGHT = 128f;
    public static Array<Minis> minis = new Array<>();
    public static final float BOX_WIDTH = 50f, BOX_HEIGHT = 95f;
    @Setter @Getter
    private boolean isFacingRight;
    @Getter @Setter
    private Viewport viewport;
    @Getter @Setter
    private float worldX, worldY;
    @Getter @Setter
    private boolean looping, useOnlyLastFrame;
    public static float velocityX = 2_000f;
    private boolean walking, attacking, laser, shooting;
    private ArrayList<Body> attacking_box_bodies = new ArrayList<>();

    private Array<Laser> laser_rail = new Array<>();

    public static Rifle rifle;
    private float degrees, radians;
    private String oldAnimation = "IDLE";
    public static Character_Features character_features = new Character_Features();

    private Sprite headsetlaser;
    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(BOX_WIDTH/2f, BOX_HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, "Boy", 0.1f);
        body.setFixedRotation(true);
        visible = true;
        mass(5.0f, new Vector2((BOX_WIDTH/2f) - 5, BOX_HEIGHT/2f), 1.0f);
        isFacingRight = true;
        this.viewport = viewport;
        changeAnimation("IDLE");
        rifle = new Rifle(new Vector2(10_000, 20_000));
    }


    @Override
    public void render(SpriteBatch s) {
        renderAnimation(s);
        renderMinis(s);
        if (!attacking)
            renderWeaponAnimations(s);
        renderLaser(s);
    }

    private void renderLaser(SpriteBatch spriteBatch){
        if (!laser_rail.isEmpty()) {
            for (Laser laser : laser_rail)
                laser.render(spriteBatch);
        }
    }

    private void renderAnimation(SpriteBatch s){
        Sprite sprite = new Sprite(animation().getAnimator().currentSpriteFrame(useOnlyLastFrame, looping, !isFacingRight));
        sprite.setOriginCenter();
        sprite.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
        sprite.draw(s);
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
        updateAnimation();
        walking();
        respawn();
        attacking();
        idle();
        aim();
        character_features.update();
    }

    private void attacking() {
        attackingBodiesUpdate();
        if (!attacking) {
            for (Body attacking_box_body : attacking_box_bodies) {
                if (attacking_box_body != null) {
                    attacking_box_body.setTransform(new Vector2(10_000, 10_000), 0);
                }
            }
        }
        if (isFinishedCurrentAnimation() && attacking) {
            animation().getAnimator().resetAnimation();
            attacking = false;
        }
    }

    private void updateAnimation() {
        animation().getAnimator().update();
    }

    private void attackingBodiesUpdate() {
        attackUsingBodies();
    }

    private void respawn(){
        if (body.getPosition().y < -100f) {
            beenHit();
            body.setTransform(new Vector2(100, 400), 0);
        }
    }

    private void walking(){
        if (animationName().equals("WALKING") || animationName().equals("WALKING_SWORD")){
            if (isFinishedCurrentAnimation())
                resetCurrentAnimation();
        }

    }

    private String whichOneEquip(){
        for (Item item : items.keySet())
            if (equipped[item.getIndex()]){
                return item.toString();
            }
        return "";
    }

    private void renderWeaponAnimations(SpriteBatch spriteBatch){
         switch (whichOneEquip()){
            case "Sword":{
                if (!animationName().equals("WALKING_SWORD")) {
                    oldAnimation = "SWORD";
                    if (!isntMoving()) {
                        changeAnimation("WALKING_SWORD");
                    }
                    else {
                        changeAnimation("SWORD");
                    }
                }
                break;
            }
            case "Rifle":{
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                shooting = true;
                top = new Sprite(Player_Animations.valueOf("RELOADING").getAnimator().currentSpriteFrameUpdateStateTime(!rifle.isReloading(), rifle.isReloading(), !isFacingRight));
                top.setOriginCenter();
                top.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
                legs = new Sprite(Player_Animations.valueOf("LEGS_ONLY").getAnimator().currentSpriteFrameUpdateStateTime(!isMoving(), isMoving(), !isFacingRight));
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
                oldAnimation = "NONE";
                break;
            }
            case "Saber":{
                changeAnimation("SABER");
                break;
            }
            case "Laser_Headset":{
                spriteBatch.draw(shoot, worldX - 13, worldY - 9);
                laser = true;
                headsetlaser = new Sprite(Player_Animations.HEADSET.getAnimator().currentSpriteFrameUpdateStateTime(false,
                    true, !isFacingRight));
                headsetlaser.setPosition(isFacingRight ? body.getPosition().x - 3f : body.getPosition().x - 10f, body.getPosition().y + 12f);
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
            default:{
                if (idle()){
                    changeAnimation("IDLE");
                    break;
                }
            }
        }
    }

    private void aim(){
        if (laser || shooting) {
            float dx = worldX - Math.abs(body.getPosition().x + 64);
            float dy = worldY - Math.abs(body.getPosition().y + 64);
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
            radians = (float) Math.atan2(dy, dx);
        }
    }

    private boolean isMoving(){
        return body.getLinearVelocity().x != 0;
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

    private boolean idle(){
        if (!beenHit && !attacking) {
            if (!onGround()){
                if (Math.abs(getBody().getLinearVelocity().x) <= 2f)
                    if (laser)
                       changeAnimation("JUMPING_FRONT_LASER");
                    else
                        changeAnimation("JUMPING_FRONT");
                if (Math.abs(getBody().getLinearVelocity().x) > 2f && Math.abs(getBody().getLinearVelocity().x) < 15f)
                    changeAnimation("JUMPING");

            } else {
                System.out.println(getBody().getLinearVelocity().x);
                if (Math.abs(getBody().getLinearVelocity().x) >= 15f) {
                    changeAnimation("WALKING");
                    walking = true;
                } else{
                    if (!isMoving() || !walking){
                        if (!oldAnimation.equals("NONE"))
                            changeAnimation("IDLE");
                        if (oldAnimation.equals("WALKING_SWORD"))
                            changeAnimation("SWORD");
                        return true;
                    }
                }
            }
        } return false;
    }

    private boolean isntMoving(){
        return Math.abs(getBody().getLinearVelocity().x) == 0 && Math.abs(getBody().getLinearVelocity().y) == 0;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    public void attackUsingBodies(){
        if (animationName().equals("PUNCHING_FIRE")) {
            if (animation().getAnimator().stateTime > 0.30 && animation().getAnimator().stateTime < 0.32) {
                attacking_box_bodies.add(BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH/2f :
                        getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50),
                    new Vector2(10, 40f), BodyDef.BodyType.KinematicBody, false, " Boy", 0f));
            }
        }
    }

    public float frameCounter(){
        return Player_Animations.currentAnimation.animator.frameCounter();
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
//            body.applyForceToCenter(new Vector2(keycode == Input.Keys.A ? -velocityX : velocityX, 0), true);
            body.applyForce(new Vector2(keycode == Input.Keys.D ? velocityX : -velocityX, 0f), getBody().getWorldCenter(), true);
            isFacingRight = (keycode == Input.Keys.D);
            changeAnimation("WALKING");
            walking = true;
        }
        if (keycode == Input.Keys.SPACE) {
            JUMP.play();
            body.applyForceToCenter(0, 10_000, true);
            if (!isMoving()){
                changeAnimation("JUMPING_FRONT_LASER");
                looping = true;
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
            if (!animationName().contains("FIRE"))
                body.setLinearVelocity(0f, body.getLinearVelocity().y);
            walking = false;
//            changeAnimation("IDLE");
        }
    }

    public void mouseMoved(int screenX, int screenY){
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (button == Input.Buttons.LEFT) {
            if (!attacking) {
                if (laser) {
                    if (PowerBar.power >= char_features.getPowerSpent()) {
                        laser_rail.add(new Laser(
                            new Vector2(isFacingRight ? (getBody().getPosition().x +
                                WIDTH / 2f) : (getBody().getPosition().x - WIDTH / 4f),
                                isFacingRight ? (getBody().getPosition().y + (HEIGHT / 2f)) : (getBody().getPosition().y + (HEIGHT))),
                            radians > Math.PI / 2f, radians, this.toString()));
                        LASER_HEADSET.play();
                        PowerBar.power -= char_features.getPowerSpent();
                    }
                } else {
                    if (shooting) {
                        if (!rifle.isReloading()) {
                            if (!rifle.getLeftSideBullets().getBulletsLeft().isEmpty()) {
                                Bullet bullet = new Bullet(
                                    new Vector2(isFacingRight ? (getBody().getPosition().x +
                                        WIDTH / 2f) : (getBody().getPosition().x),
                                        (getBody().getPosition().y + HEIGHT / 2f)),
                                    !isFacingRight, radians, true, this.toString());
                                rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                            }
                        }
                    } else {
                        if (animationName().contains("SWORD"))
                            changeAnimation("ATTACKING_SWORD_FIRE_2");
                        else {
                            changeAnimation("PUNCHING_FIRE");
                            body.applyForceToCenter(new Vector2(isFacingRight ? 10_000 : -10_000, 0), true);
                        }
                    }attacking = true;
                }
                resetCurrentAnimation();

            }
        }
    }

    @Override
    public void beenHit(){
        if (animation() != Player_Animations.STRICKEN) {
//            getBody().setLinearVelocity(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y + 40f);
            changeAnimation("STRICKEN");
            setBeenHit(true);
            Sounds.HURT.play();
            PowerBar.hit = true;
        }
    }

    public void takeItem(Item item){
        if (item.isVisible()) {
            item.setVisible(false);
            new ItemToBeDrawn(item);
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

    public void changeAnimation(String name){
        Player_Animations.changeAnimation(name);
    }

    @Override
    public void beginContact(Body body1, Body body2){
        super.beginContact(body1, body2);
    }
}
