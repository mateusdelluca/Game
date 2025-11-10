package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Animator;

public class Fire extends Objeto {

    public static final int divisor = 2;
    public static final int WIDTH = 128 / divisor, HEIGHT = 128 / divisor;

    public static final float RADIUS = 85f/divisor;

    private Animator animation = new Animator(4,4,8, 128, 128,"fire/Fire.png");
    public Fire(Vector2 position){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.circle(position, RADIUS/2f, BodyDef.BodyType.DynamicBody, false, this.toString(), 0f);
        body.setTransform(position, 0f);
        mass(1.0f, position, 0f);
        visible = true;
    }


    @Override
    public void render(SpriteBatch s) {
        if (visible) {
            Sprite sprite = new Sprite(animation.currentSpriteFrameUpdateStateTime(false, true, false));
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x - RADIUS + WIDTH / 4f + (-5f), body.getPosition().y - RADIUS / 2f);
            sprite.draw(s);
        }
    }

    public void update(){
        body.setLinearVelocity(7f, 0f);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return " Boy";
    }
}
