package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Rifle extends Objeto implements Item {

    public static final float WIDTH = Images.rifle.getWidth();
    public static final float HEIGHT = Images.rifle.getHeight();

    public final float MULTIPLY = 1/5f;

    private float angle = 0f;

    public Rifle(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        super.width = WIDTH * MULTIPLY;
        super.height = HEIGHT * MULTIPLY;
        body = createBoxBody(position, BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
    }


    @Override
    public void render(SpriteBatch s) {
        Sprite rifle = new Sprite(Images.rifle);
        rifle.setSize(width, height);
        rifle.setOriginCenter();
        rifle.setRotation(angle++);
        rifle.setPosition(body.getPosition().x, body.getPosition().y);
        rifle.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
