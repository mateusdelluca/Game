package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;

public class Rifle extends Objeto {

    public static final float WIDTH = Images.rifle.getWidth();
    public static final float HEIGHT = Images.rifle.getHeight();
    public static final int MAX_ROUNDS = 30;
    public final float MULTIPLY = 1/6f;
    @Getter
    private Magazine magazine = new Magazine(MAX_ROUNDS);
    private Vector2 position;
    private float angle = 0f;

    public Rifle(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        super.width = WIDTH * MULTIPLY;
        super.height = HEIGHT * MULTIPLY;
        this.position = position;
        body = createBoxBody(new Vector2(width, height), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
    }


    @Override
    public void render(SpriteBatch s) {
        position = body.getPosition();
        Sprite rifle = new Sprite(Images.rifle);
        rifle.setSize(width, height);
        rifle.setOriginCenter();
        rifle.setRotation(angle++);
        rifle.setPosition(position.x,position.y);
        rifle.draw(s);
        magazine.render(s);
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
