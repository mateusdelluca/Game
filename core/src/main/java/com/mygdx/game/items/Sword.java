package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Sword extends Objeto implements Item{

    public static float multiply = 1f;

    public static final float WIDTH = Images.sword_inventory.getWidth() * multiply,
        HEIGHT = Images.sword_inventory.getHeight() * multiply;

    public Sword(Vector2 position){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.circle(position, WIDTH/2f, BodyDef.BodyType.DynamicBody, true, this.toString() + " Boy", 1f);
        body.setTransform(position, 0);
    }


    @Override
    public void render(SpriteBatch s) {
        if (visible && body != null) {
            Sprite sprite = new Sprite(Images.sword_inventory);
            sprite.setPosition(body.getPosition().x - WIDTH/2f, body.getPosition().y - HEIGHT/2f);
            sprite.rotate(1f);
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
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
