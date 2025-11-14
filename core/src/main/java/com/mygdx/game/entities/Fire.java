package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Animator;

public class Fire extends Objeto {

    public static final int divisor = 2;
    public static final int WIDTH = 128 / divisor, HEIGHT = 128 / divisor;

    public static final float RADIUS = 85f/divisor;
    private boolean isFacingRight;
    private Animator animation = new Animator(4,4,8, 128, 128,"fire/Fire.png");
    public Fire(Vector2 position, boolean isFacingRight){
        super(WIDTH, HEIGHT);
        this.isFacingRight = isFacingRight;
        body = BodiesAndShapes.circle(position, RADIUS/2f, BodyDef.BodyType.DynamicBody, true, this.toString(), 0f);
        mass(1.0f, position, 0);
        visible = true;
        body.setGravityScale(0f);
        body.setTransform(position, isFacingRight ? (float) Math.toRadians(90f) : (float) Math.toRadians(270f));
        body.setLinearVelocity(new Vector2(isFacingRight ? 50f : -50f, 0f));
    }


    @Override
    public void render(SpriteBatch s) {
        if (visible) {
            Sprite sprite = new Sprite(animation.currentSpriteFrameUpdateStateTime(false, true, !isFacingRight));
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(isFacingRight ? body.getPosition().x - 100f : body.getPosition().x, body.getPosition().y - 10f - (RADIUS / 2f));
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
            sprite.draw(s);
        }
    }

    public void update(){
        if (!visible){
            body.setTransform(35000, 35000, 0);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return " Boy Fire";
    }

    public void beginContact(Body body1, Body body2){
        if ((body.getUserData().toString().contains("Fire") && body2.getUserData().toString().contains("Enemy"))
            || (body.getUserData().toString().contains("Fire") && body1.getUserData().toString().contains("Enemy"))){
            visible = false;
        }
    }
}
