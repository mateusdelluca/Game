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
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.screens.Inventory;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.mygdx.game.screens.levels.Level.items;

public class NinjaStar extends Item implements Serializable {

    private float degrees;
    public static final float VELOCITY = 60f;
    public static float divisor = 3f;
    public static final float WIDTH = Images.ninjaStar.getWidth()/ divisor, HEIGHT = Images.ninjaStar.getHeight()/ divisor;

    private float multiply = 2f;
    private boolean multiply2;
    public static int index;
    private Body body2;

    @Setter
    @Getter
    private ItemToBeDrawn itemToBeDrawn;

    Sprite sprite = new Sprite((Images.ninjaStar));

    public NinjaStar(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, true);
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

    @Override
    public void render(SpriteBatch s) {
        if (!visible && body.getLinearVelocity().x == 0f)
            body2.setTransform(10_000,1_000, 0f);
        if (visible && Math.abs(body.getLinearVelocity().x) > 0.5f) {
            render(s, sprite);
            items.put(this.toString(), this);
            index++;
            if (itemToBeDrawn != null)
                Inventory.removeFromInventory(itemToBeDrawn);
        }
        if (visible && Math.abs(body.getLinearVelocity().x) < 0.5f) {
            if (!multiply2) {
                width *= multiply;
                height *= multiply;
                multiply2 = true;
                body2.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle());
                body.setTransform(10000,10000, 0);
                body.setGravityScale(0f);
                body2.setGravityScale(0f);
            }
            Sprite sprite = new Sprite(Images.ninjaStar);
            sprite.setPosition(body2.getPosition().x, body2.getPosition().y);
            sprite.setSize(width, height);
            sprite.setOriginCenter();
            sprite.setRotation(0f);
            sprite.draw(s);
        }
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void setUserData(Body body) {

    }

    @Override
    public void setUserData(String name) {

    }

    @Override
    public BodyData getBodyData() {
        return null;
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
