package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Crystal extends Objeto implements Item{

    private Vector2 position;
    public static final float WIDTH = 40, HEIGHT = 80;
    public Crystal(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
    }

    @Override
    public void render(SpriteBatch s) {
        if (body.getUserData().equals(getClass().getSimpleName()))
            s.draw(Images.crystal, body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void updateItem() {

    }
}
