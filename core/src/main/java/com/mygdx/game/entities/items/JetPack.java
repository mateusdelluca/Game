package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class JetPack extends Objeto implements Item{

    public static final float WIDTH = 128, HEIGHT = 128;

    private float angle = 0;

    public JetPack(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
    }

    @Override
    public void render(SpriteBatch s) {
        if (!body.getUserData().toString().equals("null")) {
            Images.jetPack.draw(s);
            Images.jetPack.setPosition(body.getPosition().x, body.getPosition().y);
            Images.jetPack.setRotation(angle++);
        } else{
            body.setTransform(-1000, - 1000, 0);
        }
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
