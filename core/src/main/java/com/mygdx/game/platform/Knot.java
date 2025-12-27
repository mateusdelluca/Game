package com.mygdx.game.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.entities.Objeto;

public class Knot extends Objeto {

    public static final float DIVISOR = 1.5f;

    public static final float RADIUS = 60f/DIVISOR;

    private Sprite knot = new Sprite(new Texture(Gdx.files.internal("block/Joint.png")));

    public Knot(Vector2 position){
        body = BodiesAndShapes.circle(position, RADIUS/2f, BodyDef.BodyType.DynamicBody, false, this.toString());
    }

    @Override
    public void render(SpriteBatch s) {
        knot.setSize(RADIUS, RADIUS);
        knot.setPosition(body.getPosition().x - RADIUS / 2f, body.getPosition().y - RADIUS / 2f);
        knot.setOriginCenter();
        knot.draw(s);
    }

    @Override
    public void update() {

    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
