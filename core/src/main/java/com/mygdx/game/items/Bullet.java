package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Bullet extends Objeto implements Item {

    public static final float WIDTH = 27, HEIGHT = 9;
    private boolean isFacingLeft;
    public static  float VELOCITY = 300f;
    private float degrees, radians;
    private float timer;

    public Bullet(){
    }

    public Bullet(World world, Vector2 position, boolean isFacingLeft){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT));
        body.setGravityScale(0.1f);
        this.isFacingLeft = isFacingLeft;
        body.setTransform(position, radians);
        body.setLinearVelocity(isFacingLeft ? -VELOCITY : VELOCITY, 0f);
        getBody().setAwake(true);
//        getBody().setBullet(true);
        visible = true;
        body.setUserData(this.toString());
    }

    public Bullet(World world, Vector2 position, boolean isFacingLeft, float radians){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT));
        body.setGravityScale(0.1f);
        this.isFacingLeft = isFacingLeft;
        body.setTransform(position, radians);
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        body.setLinearVelocity((!isFacingLeft ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
//        getBody().setAwake(true);
//        getBody().setBullet(true);
        visible = true;
        body.setFixedRotation(true);
        body.setUserData(this.toString());
    }

    public Bullet(World world, Vector2 position, boolean isFacingLeft, float radians, boolean isSensor){
        super(world, WIDTH, HEIGHT);
        super.width = WIDTH;
        super.height = HEIGHT;
        Vector2 size = new Vector2(width/2f, height/2f);
        body = createBoxBody(size, BodyDef.BodyType.DynamicBody, isSensor); //boy -> sensor = true
        body.setGravityScale(0f);
        this.isFacingLeft = isFacingLeft;
//      position.add(Boy.WIDTH/2f, Boy.HEIGHT/2f);
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;

        body.setTransform(position, 0);
//      getBody().setAwake(true);
//      getBody().setBullet(false);
        body.setLinearVelocity((!isFacingLeft ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
//        body.setFixedRotation(true);
        body.setUserData(this.toString());
        if (degrees > 90f)
            this.isFacingLeft = true;
        else
            body.setTransform(position, (float) Math.toRadians(degrees));
    }

    @Override
    public void update(){
        if ((Math.abs(body.getLinearVelocity().x) < 30f && Math.abs(body.getLinearVelocity().y) < 30f) && visible) {
            body.setTransform(-1500, 0, 0);
            visible = false;
            body.setUserData("null");
        }
        if (body == null || body.getFixtureList().size == 0)
            return;
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 0.01f)
            body.getFixtureList().get(0).setSensor(false);
    }

    public void render(SpriteBatch spriteBatch){
        Sprite sprite = new Sprite(Images.bullet);
        sprite.setSize(WIDTH, HEIGHT);
//        sprite.setOriginCenter();
        sprite.flip(isFacingLeft, false);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setPosition(body.getPosition().x , body.getPosition().y);
        sprite.setOrigin(WIDTH/2f, HEIGHT/2f);
        if (visible)
            sprite.draw(spriteBatch);
    }

    @Override
    public void updateItem() {

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
