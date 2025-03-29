package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

import java.io.Serializable;

import static com.mygdx.game.screens.levels.Level.items;

public class NinjaStar extends Objeto implements Item, Serializable {

    private float degrees;
    public static final float VELOCITY = 60f;
    public static float divisor = 3f;
    public static final float WIDTH = Images.ninjaStar.getWidth()/ divisor, HEIGHT = Images.ninjaStar.getHeight()/ divisor;

    private float multiply = 2f;
    private boolean multiply2;
    public static int index;
    private Body body2;

    public NinjaStar(Vector2 position, boolean isSensor){
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body2 = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        visible = true;
    }

    public NinjaStar(Vector2 position, float radians, boolean isSensor){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, isSensor);
        body2 = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setLinearVelocity(VELOCITY * (float) Math.cos(radians), VELOCITY * (float) Math.sin(radians));
//        body.setLinearVelocity(VELOCITY, 0);
        body.setGravityScale(0.2f);
        visible = true;
    }

    public void render(SpriteBatch s, Sprite sprite){
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setRotation(degrees += 10);
        sprite.draw(s);
    }

    public void render(SpriteBatch s, Sprite sprite, Body body){
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        sprite.setRotation(0f);
        sprite.draw(s);
    }

    @Override
    public void render(SpriteBatch s) {
        if (!visible)
            body2.setTransform(10_000,1_000, 0f);
        if (visible && Math.abs(body.getLinearVelocity().x) > 1f)
            render(s, new Sprite((Images.ninjaStar)));
        if (visible && Math.abs(body.getLinearVelocity().x) < 1f) {
            if (!multiply2) {
                width *= multiply;
                height *= multiply;
                multiply2 = true;
//                items.put("NinjaStar" + index++, this);
                body2.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle());
                body.setTransform(10000,10000, 0);
                body.setGravityScale(0f);
                body2.setGravityScale(0f);
            }
            render(s, new Sprite((Images.ninjaStar)), body2);
        }
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
