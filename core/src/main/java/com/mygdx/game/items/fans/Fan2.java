package com.mygdx.game.items.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;


public class Fan2 extends Objeto{
    public static final float multiply = 1.5f;

    public static final float WIDTH = 76 * multiply, HEIGHT = 79 * multiply;

    public Fan2(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody,true);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Images.fan2.currentSpriteFrame(false,true,true), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public void update() {
        if (body == null)
            body = bodyData.convertDataToBody(BodyDef.BodyType.StaticBody, true);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }


    public void beginContact(Body bodyA, Body bodyB){
        if ((bodyA.equals(body) && bodyB.getUserData().toString().contains("Enemy")
            || bodyB.equals(body) && bodyA.getUserData().toString().contains("Enemy"))
            ||
            (bodyA.equals(body) && bodyB.getUserData().toString().equals("Player")
                || bodyB.equals(body) && bodyA.getUserData().toString().equals("Player"))){
//            world.clearForces();
            bodyA.applyForce(new Vector2(3_000f, 3_000f), bodyA.getWorldCenter(), true);
            bodyB.applyForce(new Vector2(3_000f, 3_000f), bodyA.getWorldCenter(), true);
        }
    }
}
