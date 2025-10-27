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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Player_Animations;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.StateManager;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.manager.StateManager.setStates;
import static com.mygdx.game.sfx.Sounds.JUMP;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public class Player extends Objeto{

    public static final float WIDTH = 128, HEIGHT = 128f;
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
    @Getter @Setter
    private Rectangle actionRect = new Rectangle();
    private boolean walking;

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
    }

    private void renderAnimation(SpriteBatch s){
        Sprite sprite = new Sprite(Player_Animations.currentAnimation.animator.currentSpriteFrame(useOnlyLastFrame, looping, !isFacingRight));
        sprite.setOriginCenter();
        sprite.setPosition(body.getPosition().x - BOX_WIDTH, body.getPosition().y - BOX_HEIGHT/2f);
        sprite.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    public void update(){
        animation().getAnimator().update();
        actionRect = actionRect();
        idle();
        walking();
    }

    private void walking(){
        if (animationName().equals("WALKING")){
            if (animation().animator.finishedAnimation)
                animation().animator.setStateTime(0);
        }

    }

    private String animationName(){
        return animation().name();
    }

    private void idle(){
//        if (Math.abs(body.getLinearVelocity().x) <= 0 && onGround()){
//            changeAnimation("IDLE");
//        }
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
                    if (!beenHit) {
                        changeAnimation("IDLE");
                    }
                }
            }
        }
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    public Rectangle actionRect(){
        if (animation().name().equals("PUNCHING")) {
            if (frameCounter() >= 2)
                return new Rectangle(isFacingRight ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                    getBody().getPosition().y + HEIGHT / 2f - 25, 45, 45);
        } else{
            if (animation().name().equals("SABER")) {
                if (frameCounter() > 0)
                    return new Rectangle(isFacingRight ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                        getBody().getPosition().y + HEIGHT / 2f - 25, 70, 45);
            }
        }
        return new Rectangle();
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
        if (button == Input.Buttons.LEFT)
            changeAnimation("ATTACKING_SWORD_FIRE_2");
    }

    public void takeItem(Item item){
        item.setVisible(false);
        ItemToBeDrawn itemToBeDrawn = new ItemToBeDrawn(item.toString());
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
