package com.mygdx.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Player_Animations;
import com.mygdx.game.items.Item;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.entities.Character_Features.velocityX;
import static com.mygdx.game.sfx.Sounds.JUMP;

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
    public static float velocityX = 10_000_000f;
    @Getter @Setter
    private Rectangle actionRect = new Rectangle();

    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(BOX_WIDTH/2f, BOX_HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, "Boy", 10f);
        body.setFixedRotation(true);
        visible = true;
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
        Player_Animations.currentAnimation.getAnimator().update();
        actionRect = actionRect();
        idle();
    }

    private void idle(){
        if (Math.abs(body.getLinearVelocity().x) <= 0){
            changeAnimation("IDLE");
        }
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    public Rectangle actionRect(){
        if (animations().name().equals("PUNCHING")) {
            if (frameCounter() >= 2)
                return new Rectangle(isFacingRight ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                    getBody().getPosition().y + HEIGHT / 2f - 25, 45, 45);
        } else{
            if (animations().name().equals("SABER")) {
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
        }
        if (keycode == Input.Keys.SPACE) {
            JUMP.play();
            if (Math.abs(getBody().getLinearVelocity().x) < 15f)
                changeAnimation("JUMPING_FRONT");
            if (Math.abs(getBody().getLinearVelocity().x) >= 15f)
                changeAnimation("JUMPING");
        }

    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
        }
    }

    public void mouseMoved(int screenX, int screenY){
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }


    public void touchDown(int screenX, int screenY, int pointer, int button){

    }

    public void takeItem(Item item){
        item.setVisible(false);
    }

    public Rectangle getBodyBounds() {
        return new Rectangle(body.getPosition().x + width/2f - 30f, body.getPosition().y + height/2f - 50f, BOX_WIDTH, BOX_HEIGHT);
    }

    @Override
    public String toString() {
        return "Boy";
    }

    public Player_Animations animations(){
        return Player_Animations.currentAnimation;
    }

    public void changeAnimation(String name){
        Player_Animations.changeAnimation(name);
    }
}
