package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;

public class Laser extends Objeto {


    public static final float WIDTH = Images.hp.getWidth()/5f, HEIGHT = Images.hp.getHeight()/2f;
    public static final float VELOCITY = 300f;

    private boolean isFacingLeft;
    private float degrees, radians;
    private float timer;
    private float alpha = 1f;

    public Laser(){
    }

    public Laser(Vector2 position, boolean isFacingLeft, float radians, String user){
        super(WIDTH/2, HEIGHT/2);
        super.width = WIDTH/2;
        super.height = HEIGHT/2;
        Vector2 size = new Vector2(width/2f, height/2f);
        body = createBody(size, BodyDef.BodyType.DynamicBody, 1F, false);
        body.setGravityScale(0f);
        this.isFacingLeft = isFacingLeft;
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        body.setTransform(position, radians);
        body.setLinearVelocity(VELOCITY * (float) Math.cos(this.radians), VELOCITY * (float) Math.sin(this.radians)); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
        body.setUserData(this + " " + user);
//        if (degrees > 90f)
//            this.isFacingLeft = true;
//        else
//            body.setTransform(position, radians);
        bodyData = new BodyData(body, size, WIDTH, HEIGHT, 1f);
    }

    @Override
    public void update(){
        super.update();
        if (Math.abs(body.getLinearVelocity().x) < 30f && Math.abs(body.getLinearVelocity().y) < 10f) {
            visible = false;
            body.setUserData("null");
//            body = null;
        }
        if (body == null || body.getFixtureList().size == 0)
            return;
        timer += Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch spriteBatch){
        if (body == null) {
            body = bodyData.convertDataToBody(BodyDef.BodyType.DynamicBody, true);
            body.setGravityScale(0f);
            visible = true;
        }
        Sprite sprite = new Sprite(Images.hp);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOriginCenter();
        sprite.setOrigin(0,0);
        sprite.flip(isFacingLeft, false);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        if (visible) {
            alpha -= 0.01f;
            alpha = Math.max(alpha, 0.2f);
            sprite.setAlpha(alpha);
            if (alpha <= 0.2f)
                body.setTransform(-10_000, -10_000, 0);
            sprite.draw(spriteBatch);
        }
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
