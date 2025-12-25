package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.images.Blocks_Sprites;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.mygdx.game.screens.levels.Level.world;

public class Block extends Objeto{

    public static final float WIDTH = 200f, HEIGHT = 200f;

    private Blocks_Sprites animations = new Blocks_Sprites();

    public static int index;

    @Getter @Setter
    private boolean onlyFirstFrame = true, destroyed;
    public final float DURATION_ANIMATION_IN_SECONDS = 0.5f;
    private boolean visible0;
    private float stateTime;

    public Block(Vector2 position){
        visible0 = true;
        body = createBody(new Vector2(115f/4f, 150/4f), BodyDef.BodyType.StaticBody, false, 0.0f);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
        index++;
    }

    @Override
    public void render(SpriteBatch s) {
        if (this.visible0) {
//            System.out.println(animations.animator.getAnimationDuration());
//            System.out.println(animations.animator.getAnimation().getKeyFrame(stateTime).getU2());
            Sprite sprite = new Sprite(animations.currentAnimation.currentSpriteFrame(onlyFirstFrame, stateTime));
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setOriginCenter();
            sprite.setPosition(body.getPosition().x - 203f / 2f, body.getPosition().y - 177f / 2f);
            sprite.draw(s);
        }
        animations.currentAnimation.setStateTime(stateTime);
        this.visible0 = !(stateTime >= DURATION_ANIMATION_IN_SECONDS);
        if (destroyed)
            stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void update(){
        if (!this.visible0 && body != null) {
            body.setTransform(10_000 + new Random().nextFloat(10000), 10_000 + new Random().nextFloat(10000), 0);
            world.destroyBody(body);
            body = null;
        }
    }


    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + index;
    }

    @Override
    public void beenHit(){
        onlyFirstFrame = false;
        destroyed = true;
    }

    public Rectangle getRectangle(){
        Sprite sprite = new Sprite(animations.currentAnimation.getFrame(0));
        sprite.setSize(115, 150);
        return sprite.getBoundingRectangle();
    }
}
