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
import com.mygdx.game.items.Item;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.items.minis.Minis;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.manager.StateManager.setStates;
import static com.mygdx.game.screens.levels.Level_Manager.world;
import static com.mygdx.game.sfx.Sounds.JUMP;
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
    private boolean walking, attacking;
    private Body attacking_box_body;

    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(BOX_WIDTH/2f, BOX_HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, "Boy", 0.1f);
        body.setFixedRotation(true);
        visible = true;
        mass(5.0f, new Vector2((BOX_WIDTH/2f) - 5, BOX_HEIGHT/2f), 1.0f);
        isFacingRight = true;
        this.viewport = viewport;
        changeAnimation("IDLE");
    }


    @Override
    public void render(SpriteBatch s) {
        renderAnimation(s);
        renderMinis(s);
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
    }

    private void attacking() {
        attacking = (animationName().contains("FIRE"));
        if (isFinishedCurrentAnimation()) {
            animation().getAnimator().resetAnimation();
            changeAnimation("IDLE");
        }
        attackingBodiesUpdate();
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
        if (animationName().equals("WALKING")){
            if (isFinishedCurrentAnimation())
                resetCurrentAnimation();
        }

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
        if (!beenHit && !attacking) {
            if (!onGround()){
                if (Math.abs(getBody().getLinearVelocity().x) <= 1f)
                    changeAnimation("JUMPING_FRONT");
                if (Math.abs(getBody().getLinearVelocity().x) > 1f && Math.abs(getBody().getLinearVelocity().x) < 15f)
                    changeAnimation("JUMPING");

            } else {
                if (Math.abs(getBody().getLinearVelocity().x) >= 15f) {
                    changeAnimation("WALKING");
                    walking = true;
                } else{
                    if (Math.abs(getBody().getLinearVelocity().x) == 0 && Math.abs(getBody().getLinearVelocity().y) == 0){
                        changeAnimation("IDLE");
                    }
                }
            }
        }
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    public void attackUsingBodies(){
        if (animationName().equals("PUNCHING_FIRE")) {
            if (frameCounter() == 4) {
                attacking_box_body = BodiesAndShapes.box(new Vector2(isFacingRight ? getBody().getPosition().x + WIDTH/2f :
                        getBody().getPosition().x - (WIDTH / 2f) + 20, getBody().getPosition().y + (HEIGHT / 2f) - 50),
                    new Vector2(10, 40f), BodyDef.BodyType.StaticBody, true, " Boy", 100f);
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
            body.applyForceToCenter(0, 5_000, true);
        }
        if (keycode == Input.Keys.I){
            takeScreenshot();
            setStates(StateManager.States.INVENTORY);
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
            walking = false;
            changeAnimation("IDLE");
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
            changeAnimation("PUNCHING_FIRE");
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
            new ItemToBeDrawn(item.toString());
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
