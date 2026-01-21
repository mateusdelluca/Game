package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.entities.Character_Features.*;

public class Laser extends Item {


    public static final float WIDTH = Images.laser_rail.getWidth()/5f, HEIGHT = Images.laser_rail.getHeight()/2f;
    public static final float VELOCITY = 300f;

    private boolean isFacingLeft;
    private float degrees, radians;
    private float timer;
    private float alpha = 1f;

    private float initialDistance, distance;


    public Laser(){
    }

    public Laser(Vector2 position, boolean isFacingLeft, float radians, String user){
        super(WIDTH/2, HEIGHT/2);
        super.width = WIDTH/2;
        super.height = HEIGHT/2;
        Vector2 size = new Vector2(width/2f, height/2f);
        body = createBody(size, BodyDef.BodyType.DynamicBody, 1F, true);
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
        initialDistance = getBody().getPosition().x;
    }

    @Override
    public void update(){
        super.update();
        distance += getBody().getPosition().x;
        if (distance - initialDistance >= laserDistance && body != null && body.getUserData().toString().contains("Player")) {
            visible = false;
            body.setTransform(-10_000, -10_000, 0);
            body.setUserData("null");
        }
        if (body == null || body.getFixtureList().size == 0)
            return;
        timer += Gdx.graphics.getDeltaTime();
    }

    @Override
    public BodyData getBodyData() {
        return null;
    }

    public void render(SpriteBatch spriteBatch){
        update();
        if (body == null) {
            body = bodyData.convertDataToBody(BodyDef.BodyType.DynamicBody, true);
            body.setGravityScale(0f);
            visible = true;
        }
        Sprite sprite = new Sprite(Images.laser_rail);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOrigin(0,0);
        sprite.flip(isFacingLeft, false);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        if (visible) {
            sprite.draw(spriteBatch);
        }

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
