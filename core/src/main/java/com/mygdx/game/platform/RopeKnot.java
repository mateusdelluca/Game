package com.mygdx.game.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entities.Objeto;


public class RopeKnot extends Objeto {

    public static final float DIVISOR = 1.0f;

    public static final float WIDTH = 20/DIVISOR, HEIGHT = 20/DIVISOR;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("block/Fragment.png")));

    public RopeKnot(Vector2 position, float radians){
        super(WIDTH, HEIGHT);
        super.width = WIDTH;
        super.height = HEIGHT;
        Vector2 size = new Vector2(width / 2f, height / 2f);
        body = createBody(size, BodyDef.BodyType.DynamicBody, true);
        body.setGravityScale(10f);
        body.getFixtureList().get(0).setRestitution(0f);
        body.setTransform(position, radians);
        body.setUserData(this.toString());
    }

    public void render(SpriteBatch s){
        if (visible) {
            sprite.setSize(WIDTH, HEIGHT);
    //        sprite.setOriginCenter();
            sprite.setOrigin(0,0);
            sprite.flip(!isFacingRight, false);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
            update();
        }
    }

    public void update(){

    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    public void beginContact(Body body1, Body body2){
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
