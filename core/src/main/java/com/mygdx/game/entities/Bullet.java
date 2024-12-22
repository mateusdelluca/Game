package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;

public class Bullet extends Objeto{

    public static final int WIDTH = 27, HEIGHT = 9;
    private boolean flip;
    public static  float VELOCITY = 300f;
    private float degrees, radians;
    private float timer;

    public Bullet(World world, Vector2 position, boolean flip){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT));
        body.setGravityScale(0.1f);
        this.flip = flip;
        body.setTransform(position, radians);
        body.setLinearVelocity(flip ? -VELOCITY : VELOCITY, 0f);
        getBody().setAwake(true);
//        getBody().setBullet(true);
        visible = true;
        body.setUserData(this.toString());
    }

    public Bullet(World world, Vector2 position, boolean flip, float radians){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT));
        body.setGravityScale(0.1f);
        this.flip = flip;
        body.setTransform(position, radians);
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        body.setLinearVelocity((!flip ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
//        getBody().setAwake(true);
//        getBody().setBullet(true);
        visible = true;body.setFixedRotation(true);
        body.setUserData(this.toString());
    }

    public Bullet(World world, Vector2 position, boolean flip, float radians, boolean isSensor){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT), isSensor); //boy -> sensor = true
        body.setGravityScale(0f);
        this.flip = flip;
//      position.add(Boy.WIDTH/2f, Boy.HEIGHT/2f);
        body.setTransform(position, radians);
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
//      getBody().setAwake(true);
//      getBody().setBullet(false);
        body.setLinearVelocity((!flip ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
        body.setFixedRotation(true);
        body.setUserData(this.toString());


    }

    public void update(){
        if (Math.abs(body.getLinearVelocity().x) < 10f && visible) {
            body.setTransform(0, 0, 0);
            visible = false;
        }
        if (body == null || body.getFixtureList().size == 0)
            return;
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 0.01f)
            body.getFixtureList().get(0).setSensor(false);
    }

    public void render(SpriteBatch spriteBatch){
        Sprite sprite = new Sprite(Images.bullet);
        sprite.setOrigin(0,0);
        sprite.flip(flip, false);
//        sprite.setOrigin(0,0);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setSize(WIDTH, HEIGHT);
        if (visible)
            sprite.draw(spriteBatch);
        update();
    }

    @Override
    public void renderShape(ShapeRenderer s) {
        s.rect(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
