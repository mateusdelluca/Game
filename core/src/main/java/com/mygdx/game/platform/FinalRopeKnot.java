package com.mygdx.game.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.system.BodyData;

import java.util.ArrayList;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box2;
import static com.mygdx.game.entities.Player.BOX_HEIGHT;
import static com.mygdx.game.entities.Player.BOX_WIDTH;
import static com.mygdx.game.screens.levels.Level.player;
import static com.mygdx.game.screens.levels.Level.world;

public class FinalRopeKnot extends Objeto {

    public static final float WIDTH = 20, HEIGHT = 20;
    public static  float VELOCITY = 100f;
    private float degrees, radians;
    private boolean collides;

    private Body end;

    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("block/Fragment.png")));
    private boolean joint;

    RopeJointDef ropeJointDef;

    private RopeKnot ropeKnot1, ropeKnot2;
    private ArrayList<RopeKnot> knots = new ArrayList<>();
    private Body playerBody;

    private Joint joint0;

    public FinalRopeKnot(Vector2 position, boolean isFacingRight, float radians) {
        super(WIDTH, HEIGHT);
        super.width = WIDTH;
        super.height = HEIGHT;
        Vector2 size = new Vector2(width / 2f, height / 2f);
        body = createBody(size, BodyDef.BodyType.DynamicBody, false);
        body.setGravityScale(0f);
        this.isFacingRight = isFacingRight;
        this.degrees = (float) Math.toDegrees(radians);
        this.radians = radians;
        this.isFacingRight = radians < Math.PI / 2f || radians > (3f / 2f) * Math.PI;
        body.setTransform(isFacingRight ? position : new Vector2(position.x - 50f, position.y), (float) radians);
        body.setLinearVelocity((this.isFacingRight ? VELOCITY * (float) Math.cos(this.radians) : -Math.abs(VELOCITY * (float) Math.cos(this.radians))), (float) (VELOCITY * Math.sin(radians))); //TODO calcular velocidade x e y de acordo com o ângulo
        visible = true;
        body.setUserData(this.toString());
        bodyData = new BodyData(body, size, WIDTH, HEIGHT, 1f);
        playerBody = box2(new Vector2(player.getBody().getPosition().x + BOX_WIDTH/2f, player.getBody().getPosition().x + BOX_HEIGHT/2f),
             new Vector2(BOX_WIDTH/10f, BOX_HEIGHT/10f), BodyDef.BodyType.KinematicBody, true, "Player", radians);
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
            if (knots != null)
                for (RopeKnot knot : knots)
                    knot.render(s);
        }
    }

    public void update(){
        if (collides && !joint){
            for (int index = 0; index < 100; index++) {
                ropeKnot1 = new RopeKnot(new Vector2(body.getPosition().x - (float) (WIDTH * Math.cos(radians) * index), body.getPosition().y - (float) (HEIGHT * Math.sin(radians)) * index), radians);
                ropeKnot2 = new RopeKnot(new Vector2(body.getPosition().x - (float) (WIDTH * Math.cos(radians) * index), body.getPosition().y - (float) (HEIGHT * Math.sin(radians)) * index), radians);
                knots.add(ropeKnot1);
                knots.add(ropeKnot2);


                joint(ropeKnot1.getBody(), ropeKnot2.getBody());

                if (Math.abs(ropeKnot2.getBody().getPosition().x - player.getBody().getPosition().x) < WIDTH * 4) {
                    joint = true;
                    joint(ropeKnot2.getBody(), player.getBody());
                    break;
                }
                if (Math.abs(ropeKnot2.getBody().getPosition().y - player.getBody().getPosition().y) < HEIGHT * 4) {
                    joint(ropeKnot2.getBody(), player.getBody());
                    joint = true;
                    break;
                }
            }

        }
        if (joint) {
            body.setLinearVelocity(0, 0);
        }
    }

    private void joint(Body body1, Body body2){
        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = body1;
        ropeJointDef.bodyB = body2;
        ropeJointDef.collideConnected = true;
        ropeJointDef.maxLength = 10f;
        if (body1.getUserData().toString().contains("Player") || body2.getUserData().toString().contains("Playe"))
            joint0 = world.createJoint(ropeJointDef);
        else
            world.createJoint(ropeJointDef);
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

    public void touchDown(int button){
        if (button == Input.Buttons.RIGHT && joint0 != null) {
            world.destroyJoint(joint0);
            if (knots != null && !knots.isEmpty()) {
                for (RopeKnot knot : knots) {
                    world.destroyBody(knot.getBody());
                    knot.setVisible(false);
                }
                knots.clear();
            }
        }
    }


    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
