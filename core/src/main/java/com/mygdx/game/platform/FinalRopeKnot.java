package com.mygdx.game.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box2;
import static com.mygdx.game.entities.Player.*;
import static com.mygdx.game.screens.levels.Level.player;
import static com.mygdx.game.screens.levels.Level.world;
import static com.mygdx.game.screens.levels.Level_Manager.viewport;

public class FinalRopeKnot extends Objeto {

    public static final float WIDTH = 20, HEIGHT = 20;
    public static  float VELOCITY = 100f;
    private float degrees, radians;
    private boolean collides;

    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("block/Fragment.png")));
    private boolean joint;

    RopeJointDef ropeJointDef;

    private RopeKnot ropeKnot1;
    private ArrayList<RopeKnot> knots = new ArrayList<>();

    private Joint joint0, mouseJoint;
    int index;
    private Joint firstKnotJoint;
    private Joint playerJoint;

    public static Body mouseBody = box(new Vector2(worldX, worldY), new Vector2(10f,10f), BodyDef.BodyType.StaticBody, true, "mouse");;
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

    }

    public void render(SpriteBatch s){
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setOriginCenter();
        sprite.setOrigin(0,0);
        sprite.flip(isFacingRight, false);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
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
//        if (collides && !first && mouse != null) {
//            knots.add(new RopeKnot(mouse.getPosition(), mouse.getAngle()));
//            first = true;
//            playerBody = box2(new Vector2(player.getBody().getPosition().x + BOX_WIDTH/2f, player.getBody().getPosition().x + BOX_HEIGHT/2f),
//                new Vector2(BOX_WIDTH/10f, BOX_HEIGHT/10f), BodyDef.BodyType.KinematicBody, true, "Player", radians);
//            joint(playerBody, player.getBody());
//        }
        if (collides && !joint){
            for (int index = 0; index < 10; index++) {
                ropeKnot1 = new RopeKnot(new Vector2(body.getPosition().x - (float) (WIDTH * Math.cos(radians) * index), body.getPosition().y - (float) (HEIGHT * Math.sin(radians)) * index), radians);
                ropeKnot1.getBody().setLinearVelocity(player.getBody().getLinearVelocity());
                knots.add(ropeKnot1);
                if (index == 0) {

                    mouseJoint = joint(body, mouseBody);
                    firstKnotJoint = joint(body, knots.getFirst().getBody());
                }
                if (Math.abs(ropeKnot1.getBody().getPosition().x - player.getBody().getPosition().x) < WIDTH * 16) {
                    joint = true;
                    this.index = index;
                    break;
                }
                if (Math.abs(ropeKnot1.getBody().getPosition().y - player.getBody().getPosition().y) < HEIGHT * 16) {
                    joint = true;
                    this.index = index;
                    break;
                }
            }
            for (int i = 0; i + 1 < knots.size(); i++) {
                joint(knots.get(i).getBody(), knots.get(i + 1).getBody());
            }
            playerJoint = joint(knots.getLast().getBody(), player.getBody());
            collides = false;
        }
//        if (joint) {
//            body.setLinearVelocity(0, 0);
//        }



    }

    private Joint joint(Body body1, Body body2){
        ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = body1;
        ropeJointDef.bodyB = body2;
        ropeJointDef.collideConnected = false;
        ropeJointDef.maxLength = 20f;
        if (body1.getUserData().toString().contains("Player") || body2.getUserData().toString().contains("Player")) {
            joint0 = world.createJoint(ropeJointDef);
            return joint0;
        }
        return world.createJoint(ropeJointDef);
    }

    public void beginContact(Body body1, Body body2){
        if (body1 == null || body2 == null || body == null)
            return;

        if (body1.equals(body) && !body2.equals(body)) {
            collides = true;
        }
        if (body2.equals(body) && !body1.equals(body)) {
            collides = true;
        }

//            ||
//            (body1.equals(body) && body2.getUserData().toString().contains("End")
//                || body2.equals(body) && body1.getUserData().toString().contains("End")))
//        {
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (button == Input.Buttons.LEFT){
            if (joint)
                destroyJoints();
        }
        if (button == Input.Buttons.RIGHT && joint0 != null && joint) {
            if (this.body.getUserData().toString().contains(getClass().getSimpleName())) {
                destroyJoints();
                joint = false;
                collides = false;

            }
//                world.destroyJoint(playerJoint);
//                world.destroyJoint(firstKnotJoint);
//                world.destroyJoint(mouseJoint);
//                first = false;
        }
    }

    private void destroyJoints(){

//        body.setUserData("null");
//        body.setActive(false);
        if (body != null && body.isActive())
            world.destroyBody(body);
        if (joint0 != null && joint0.isActive())
             world.destroyJoint(joint0);
        if (knots != null && !knots.isEmpty()) {
            for (RopeKnot knot : knots) {
                world.destroyBody(knot.getBody());
                knot.setVisible(false);
            }
            knots.clear();
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
