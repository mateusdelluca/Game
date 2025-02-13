package com.mygdx.game.items.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Fan extends Objeto implements Fans {
    public static final float multiply = 1.0f;

    public static final float WIDTH = 76 * multiply, HEIGHT = 93 * multiply;
    private boolean useOnlyLastFrame = true;

    public Fan(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody,true);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(Images.fan.currentSpriteFrame(useOnlyLastFrame,true,false), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    public boolean bodyCloseToFan(Body b, float width){
        return (((b.getPosition().x + (width/2f)) >= (body.getPosition().x - WIDTH/2f) && (b.getPosition().x) < (body.getPosition().x + WIDTH))
          && Math.abs(b.getPosition().y - body.getPosition().y) <= 50);
    }

    public void update2(Body b, float width){
        update();
        if (bodyCloseToFan(b, width)) {
            System.out.println(b.getPosition().x + " " + body.getPosition().x);
            useOnlyLastFrame = false;
            b.setLinearVelocity(b.getLinearVelocity().x, b.getLinearVelocity().y + 15);
        } else{
            useOnlyLastFrame = true;
        }
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
}
