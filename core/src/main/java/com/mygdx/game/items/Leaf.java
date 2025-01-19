package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

import java.util.Random;

public class Leaf extends Objeto implements Item{

    public static final float DIVIDED_BY = 25f;
    public static final float WIDTH = 850/DIVIDED_BY, HEIGHT = 850/DIVIDED_BY;
    private Body body;

    public Leaf(World world, Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH, HEIGHT), BodyDef.BodyType.DynamicBody, true);
        body.setGravityScale(0.05f);
        body.setTransform(position, new Random().nextInt(91));
        float velocity_x = -new Random().nextInt(100);
        body.setLinearVelocity(velocity_x, 0);
    }

    public void render(SpriteBatch s){
        if (body.getPosition().x < - 10)
            return;
        Sprite sprite = new Sprite(Images.leaf);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.rotate(1f);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.draw(s);
        if (body.getPosition().y < - 100f){
            sprite.flip(new Random().nextBoolean(), new Random().nextBoolean());
            body.setTransform(body.getPosition().x - 7_000, 7000, 0);
        }

    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World wolrd) {

    }

    @Override
    public void update() {

    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
