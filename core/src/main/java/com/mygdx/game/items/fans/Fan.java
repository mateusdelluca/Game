package com.mygdx.game.items.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Fan extends Objeto{
    public static final float multiply = 1.0f;

    public static final float WIDTH = 76 * multiply, HEIGHT = 93 * multiply;
    private boolean useOnlyLastFrame = true;

    public Fan(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody,true);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Images.fan.currentSpriteFrameUpdateStateTime(false,true,false), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    public boolean bodyCloseToFan(Body b){
        return (((b.getWorldCenter().x >= (body.getWorldCenter().x)) && (b.getWorldCenter().x) < (body.getWorldCenter().x))
          && Math.abs(b.getPosition().y - body.getPosition().y) <= 50);
    }

    public void update(){
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
            (bodyA.equals(body) && bodyB.getUserData().toString().equals("PLayer")
                || bodyB.equals(body) && bodyA.getUserData().toString().equals("Player"))){
            bodyA.setLinearVelocity(bodyA.getLinearVelocity().x, bodyA.getLinearVelocity().y + 1500);
            bodyB.setLinearVelocity(bodyA.getLinearVelocity().x, bodyB.getLinearVelocity().y + 1500);
        }
    }

}
