package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Player_Animations;
import lombok.Getter;
import lombok.Setter;

public class Player extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;
    @Setter @Getter
    private boolean isFacingRight;
    public static Player_Animations animations;
    @Getter @Setter
    private Viewport viewport;
    @Getter @Setter
    private float worldX, worldY;
    @Getter @Setter
    private boolean looping, useOnlyLastFrame;

    public Player(Vector2 position, Viewport viewport){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, "Boy", 10f);
        visible = true;
        isFacingRight = true;
        this.viewport = viewport;
        animations.changeAnimation("IDLE");
    }


    @Override
    public void render(SpriteBatch s) {
        s.begin();
        renderAnimation(s);
        s.end();
    }

    private void renderAnimation(SpriteBatch s){
        Sprite sprite = new Sprite(Player_Animations.currentAnimation.animator.currentSpriteFrame(useOnlyLastFrame, looping, !isFacingRight));
        sprite.setPosition(body.getPosition().x - WIDTH/2f, body.getPosition().y - HEIGHT/2f);
        sprite.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    public void update(){
        animations.getAnimator().update();
    }


    public void keyDown(int keycode){
        if (keycode == Input.Keys.A || keycode == Input.Keys.D){
            body.applyForceToCenter(new Vector2(keycode == Input.Keys.A ? ,0), true);

        }
    }

    public void keyUp(int keycode){

    }

    public void mouseMoved(int screenX, int screenY){
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;
    }


    public void touchDown(int screenX, int screenY, int pointer, int button){

    }

    @Override
    public String toString() {
        return "Boy";
    }
}
