package com.mygdx.game.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Fan2 extends Objeto implements Fans{
    public static final float multiply = 1.0f;

    public static final float WIDTH = 76 * multiply, HEIGHT = 79 * multiply;
    private boolean useOnlyLastFrame = true;

    public Fan2(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody,true);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Images.fan2.currentSpriteFrame(useOnlyLastFrame,true,true), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    public boolean bodyCloseToFan(Body anotherBody, float width){
        return (Math.abs(anotherBody.getPosition().x) - (body.getPosition().x) < 70f && Math.abs(anotherBody.getPosition().y - body.getPosition().y) <= 10);
    }

    public void bodyCloseToFan2(Body b, float width){
        if (bodyCloseToFan(b, width)) {
//            System.out.println(b.getPosition().y - fanBody.getPosition().y);
            useOnlyLastFrame = false;
            b.setLinearVelocity(b.getLinearVelocity().x + 2, b.getLinearVelocity().y);
        } else{
            useOnlyLastFrame = true;
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
