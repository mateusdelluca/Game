package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Ball extends Objeto {

    public static float divisor = 10f;

    public static final float WIDTH = Images.ball.getWidth()/divisor, HEIGHT = Images.ball.getHeight()/divisor;

    public Ball(Vector2 position){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.circle(position, WIDTH/2f, BodyDef.BodyType.DynamicBody, false, this.toString(), 0.1f);
        body.setTransform(position, 0);
        body.getFixtureList().get(0).setRestitution(1f);
    }

    @Override
    public void render(SpriteBatch s) {
        Sprite ball = Images.ball;
        ball.setSize(WIDTH, HEIGHT);
        ball.setPosition(body.getPosition().x - WIDTH/2f, body.getPosition().y - HEIGHT/2f);
        ball.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
