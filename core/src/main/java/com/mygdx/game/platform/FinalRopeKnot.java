package com.mygdx.game.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.screens.levels.Level.world;

public class FinalRopeKnot extends Objeto {

    public static final float WIDTH = 50, HEIGHT = 20;
    public static  float VELOCITY = 30f;
    private float degrees, radians;
    private boolean collides;

    private Body end;

    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("block/Fragment.png")));
    private boolean joint;

    public static boolean facingRight;

    public FinalRopeKnot(Vector2 position, boolean isFacingRight, float radians) {
        super(WIDTH, HEIGHT);
        super.width = WIDTH;
        super.height = HEIGHT;
        Vector2 size = new Vector2(width / 2f, height / 2f);
        body = createBody(size, BodyDef.BodyType.KinematicBody, false);
        body.setGravityScale(0f);
        this.isFacingRight = isFacingRight;
        facingRight = isFacingRight;
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        this.isFacingRight = radians < Math.PI / 2f || radians > (3f / 2f) * Math.PI;
        body.setTransform(position, radians);
        body.setLinearVelocity((this.isFacingRight ? VELOCITY * (float) Math.cos(this.radians) : -Math.abs(VELOCITY * (float) Math.cos(this.radians))), (float) (VELOCITY * Math.sin(radians))); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
        body.setUserData(this.toString());
        bodyData = new BodyData(body, size, WIDTH, HEIGHT, 1f);
    }

    public void render(SpriteBatch s){
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOriginCenter();
        sprite.setOrigin(0,0);
        sprite.flip(isFacingRight, false);
        sprite.setRotation((float) Math.toDegrees(body.getTransform().getRotation()));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        if (visible) {
            sprite.draw(s);
            update();
        }
    }

    public void update(){
        if (collides && !joint){
            RopeJointDef ropeJointDef = new RopeJointDef();
            ropeJointDef.bodyA = body;
            ropeJointDef.bodyB = end;
            ropeJointDef.collideConnected = true;
            ropeJointDef.maxLength = 1f;
            world.createJoint(ropeJointDef);
            joint = true;
        }
        if (joint) {
            body.setLinearVelocity(0, 0);
        }
    }

    public void beginContact(Body body1, Body body2){
        if (body1 == null || body2 == null || body == null)
            return;

        if (body1.equals(body) && !body2.equals(body)) {
            end = body2;
            collides = true;
        }
        if (body2.equals(body) && !body1.equals(body)) {
            end = body1;
            collides = true;
        }

//            ||
//            (body1.equals(body) && body2.getUserData().toString().contains("End")
//                || body2.equals(body) && body1.getUserData().toString().contains("End")))
//        {
    }


    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
