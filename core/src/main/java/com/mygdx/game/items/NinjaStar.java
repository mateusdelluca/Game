package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

import java.io.Serializable;

public class NinjaStar extends Objeto implements Item, Serializable {

float degrees = 1f;
    public static final float VELOCITY = 50f;
    public static final float WIDTH = Images.ninjaStar.getWidth()/3f, HEIGHT = Images.ninjaStar.getHeight()/3f;
    public NinjaStar(Vector2 position, boolean isFacingLeft, float radians){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        //TODO: fazer o movimento da estrela de ninja como o movimento das bullets de rifle
        body.setLinearVelocity(VELOCITY, 0f);
        body.setGravityScale(0.2f);
    }


    public void render(SpriteBatch s, Sprite sprite){
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOriginCenter();
        sprite.setRotation(degrees += 10);
        if (visible)
            sprite.draw(s);
    }

    @Override
    public void render(SpriteBatch s) {
        render(s, new Sprite((Images.ninjaStar)));
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
