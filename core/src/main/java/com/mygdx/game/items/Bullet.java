package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.system.BodyData;
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

    public Bullet(Vector2 position, boolean isFacingLeft, float radians, boolean isSensor, String user){
        super(WIDTH, HEIGHT);
        super.width = WIDTH;
        super.height = HEIGHT;
        Vector2 size = new Vector2(width/2f, height/2f);
        body = createBody(size, BodyDef.BodyType.DynamicBody, isSensor);
        body.setGravityScale(0f);
        this.isFacingLeft = isFacingLeft;
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        body.setTransform(position, 0);
//      getBody().setAwake(true);
//      getBody().setBullet(false);
        body.setLinearVelocity((!isFacingLeft ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
//        body.setFixedRotation(true);
        body.setUserData(this + " " + user);
        if (degrees > 90f)
            this.isFacingLeft = true;
        else
            body.setTransform(position, (float) Math.toRadians(degrees));
        bodyData = new BodyData(body, size, WIDTH, HEIGHT, 1f);
    }

//    public Bullet(Vector2 position, boolean isFacingLeft, float radians, boolean isSensor){
//        super(WIDTH, HEIGHT);
//        super.width = WIDTH;
//        super.height = HEIGHT;
//        Vector2 size = new Vector2(width/2f, height/2f);
//        body = createBody(size, BodyDef.BodyType.DynamicBody, isSensor); //boy -> sensor = true
//        body.setGravityScale(0f);
//        this.isFacingLeft = isFacingLeft;
//        this.degrees = (float) Math.toDegrees(radians);
//        this.radians = radians;
//        body.setTransform(position, 0);
////      getBody().setAwake(true);
////      getBody().setBullet(false);
//        body.setLinearVelocity((!isFacingLeft ? VELOCITY : -VELOCITY) * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
//        visible = true;
////        body.setFixedRotation(true);
//        body.setUserData(this.toString());
//        if (degrees > 90f)
//            this.isFacingLeft = true;
//        else
//            body.setTransform(position, (float) Math.toRadians(degrees));
//        bodyData = new BodyData(body, size, WIDTH, HEIGHT, 1f);
//    }


    @Override
    public void update(){
        super.update();
        if (Math.abs(body.getLinearVelocity().x) < 30f && Math.abs(body.getLinearVelocity().y) < 10f) {
            visible = false;
            body.setUserData("null");
//            body = null;
        } else{
            body.setUserData(bodyData.userData);
        }
        if (body == null || body.getFixtureList().size == 0)
            return;
        timer += Gdx.graphics.getDeltaTime();
        if (timer < 0.01f) {
            body.getFixtureList().get(0).setSensor(true);
//            body.setUserData("null");
        } else {
            body.getFixtureList().get(0).setSensor(false);
//            body.setUserData(this);
        }
    }

    public void render(SpriteBatch spriteBatch){

//        if (body == null || body.getFixtureList().size == 0)
//            return;
        if (body == null) {
            body = bodyData.convertDataToBody(BodyDef.BodyType.DynamicBody, false);
            visible = true;
        }
        Sprite sprite = new Sprite(Images.bullet);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOriginCenter();
        sprite.setOrigin(0,0);
        sprite.flip(isFacingLeft, false);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
//        sprite.getBoundingRectangle(); TODO: usar rectangle da imagem de bullet para avaliar quando a bala colide ou não
        if (visible) {
//            fixBullet(this);
            sprite.draw(spriteBatch);
            update();
        } else{
            body.setTransform(-10_000, -10_000, 0);
        }
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

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
