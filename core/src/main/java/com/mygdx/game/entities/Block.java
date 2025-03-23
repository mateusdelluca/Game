package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

public class Block extends Objeto{

    public static final float WIDTH = 200f, HEIGHT = 200f;

    private Animations animations = Animations.MINI_BLOCK;

    @Getter @Setter
    private boolean onlyFirstFrame = true, destroyed;
    public final float DURATION_ANIMATION_IN_SECONDS = animations.animator.getAnimationDuration();

    public Block(Vector2 position){
        body = createBody(new Vector2(115f/4f, 150/4f), BodyDef.BodyType.StaticBody, false);
        body.setTransform(position, 0);
        visible = true;
    }

    @Override
    public void render(SpriteBatch s) {
        if (visible) {
//            System.out.println(animations.animator.getAnimationDuration());
            System.out.println(animations.animator.getAnimation().getKeyFrame(animations.animator.getStateTime()).getU2());
            Sprite sprite = new Sprite(animations.getAnimator().currentSpriteFrame(onlyFirstFrame));
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setOriginCenter();
            sprite.setPosition(body.getPosition().x - 203f/2f, body.getPosition().y - 177f/2f);
            sprite.draw(s);
        }
        visible = !(animations.animator.getStateTime() >= animations.animator.getAnimation().getAnimationDuration());
        if (destroyed)
            animations.animator.setStateTime(animations.animator.getStateTime() + Gdx.graphics.getDeltaTime());
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void beenHit(){
        onlyFirstFrame = false;
        destroyed = true;
    }
}
