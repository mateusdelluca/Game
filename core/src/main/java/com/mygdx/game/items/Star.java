package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.images.Images;

import java.io.Serializable;

import static com.mygdx.game.entities.Player.thrownStar;
import static com.mygdx.game.screens.levels.Level.player;

public class Star extends Item implements Serializable {

    private float degrees;
    public static final float VELOCITY = 60f;
    public static float divisor = 3f;
    public static final float WIDTH = Images.ninjaStar.getWidth()/ divisor, HEIGHT = Images.ninjaStar.getHeight()/ divisor;

    private float multiply = 2f;
    private boolean multiply2;
    private Body bodyThrown;

    Sprite sprite = new Sprite((Images.ninjaStar));

    public Star(Vector2 position){
        super(WIDTH, HEIGHT, Star.class.getSimpleName());
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, true);
        bodyThrown = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
    }

    public Star(Vector2 position, float radians, boolean isSensor){
        super(WIDTH, HEIGHT, position);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, isSensor);
        bodyThrown = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setLinearVelocity(VELOCITY * (float) Math.cos(radians), VELOCITY * (float) Math.sin(radians));
        body.setGravityScale(0.2f);
        visible = true;
        thrownStar = false;
    }

    public void render(SpriteBatch s, Sprite sprite){
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setRotation(degrees += 10);
        sprite.draw(s);
    }

    @Override
    public void render(SpriteBatch s) {
        if (!visible && body.getLinearVelocity().x == 0f)
            bodyThrown.setTransform(10_000,1_000, 0f);
        if (visible && Math.abs(body.getLinearVelocity().x) > 0.5f) {
            render(s, sprite);
        }
        if (visible && Math.abs(body.getLinearVelocity().x) < 0.5f) {
            if (!multiply2) {
                width *= multiply;
                height *= multiply;
                multiply2 = true;
                bodyThrown.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle());
                body.setTransform(10000,10000, 0);
                body.setGravityScale(0f);
                bodyThrown.setGravityScale(0f);
            }
            Sprite sprite = new Sprite(Images.ninjaStar);
            sprite.setPosition(bodyThrown.getPosition().x, bodyThrown.getPosition().y);
            sprite.setSize(width, height);
            sprite.setOriginCenter();
            sprite.setRotation(0f);
            sprite.draw(s);
        }
    }


    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void beginContact(Body bodyA, Body bodyB){
        if (body != null && bodyA != null && bodyB != null){
            if ((bodyA.equals(body) || bodyB.equals(body)) && (bodyA.getUserData().toString().contains("Player") || bodyB.getUserData().toString().contains("Player"))){
                player.takeItem(this);
            }
        }
    }
}
