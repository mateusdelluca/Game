package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Robot2_Sprites;
import com.mygdx.game.items.Laser;
import lombok.Getter;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.images.Robot2_Sprites.HEIGHT;
import static com.mygdx.game.images.Robot2_Sprites.WIDTH;
import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;

public class Robot extends Objeto{

    @Getter
    private Character_Features char_features;
    private Robot2_Sprites sprites = new Robot2_Sprites();
    private Array<Laser> laser_rail = new Array<>();

    private int counter;
    private boolean looping = true, facingRight, useOnlyLastFrame;

    private Boy boy;
    private float timer2;

    private Body punch_box;

    public Robot(Vector2 position, Boy boy){
        super(WIDTH, HEIGHT);
        this.boy = boy;
        dimensions = new Vector2(46/2f, 108/2f);
        body = box(position, dimensions, BodyDef.BodyType.DynamicBody, false);
        char_features = new Character_Features();
        visible = true;
        looping = true;
        facingRight = false;
        body.setUserData(this.toString());
        changeAnimation("punching");
    }

    @Override
    public void render(SpriteBatch s) {
        if (visible && HP > 0) {
            update();
//            Sprite sprite = new Sprite(sprites.currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
//            sprite.setPosition(body.getPosition().x - dimensions.x - 20f, body.getPosition().y - dimensions.y/2f);
//            sprite.setSize(Robot2_Sprites.WIDTH, Robot2_Sprites.HEIGHT);
//            sprite.draw(s);
            Sprite sprite = sprites.currentFrame(useOnlyLastFrame, looping, facingRight);
            sprite.setPosition(body.getPosition().x - dimensions.x - 40, body.getPosition().y - 120f/2f);
            sprite.setSize(WIDTH, HEIGHT);
            sprite.draw(s);

            for (Laser laser : laser_rail)
                laser.render(s);
        }
    }

    @Override
    public void update(){
        super.update();
        if (nameAnim().equals("fire")) {
            fire();
        }
        if (nameAnim().equals("punching")){
            timer2 += Gdx.graphics.getDeltaTime();
            if (animCounter(timer2) >= 5f){
                timer2 = 0f;
                if (punch_box != null)
                    punch_box.setTransform(22_000, 22_000, 0);
            }
            if (animCounter(timer2) >= 4f && animCounter(timer2) < 5f){
                punch_box = BodiesAndShapes.box(new Vector2(facingRight ? body.getPosition().x + 110 : body.getPosition().x - 30,
                    body.getPosition().y + 20f), new Vector2(20f, 20f), BodyDef.BodyType.StaticBody, true, " Enemy", 50f);
            }
        }
        if (beenHit) {
            beenHit();
            beenHit = false;
        }
        if (HP <= 0){
            falling();
        } else
            sprites.update();

    }

    private void falling() {
        changeAnimation("falling");
        looping = false;
        body.applyForceToCenter(new Vector2(facingRight ? -100f : 100f, 10), true);
        if (sprites.falling.ani_finished() && onGround())
            visible = false;
//        body.setTransform(body.getPosition(), facingRight ? (float) Math.PI/2f : (float) -Math.PI/2f);
    }

    private void fire(){
        if (sprites.currentAnim.equals("fire")) {
            if (sprites.currentAnimation.ani_finished()) {
                laser_rail.add(new Laser(
                    new Vector2(!facingRight ? (getBody().getPosition().x) : (getBody().getPosition().x - WIDTH),
                        getBody().getPosition().y + (HEIGHT / 4f)),
                    !facingRight, (!facingRight ? (float) Math.PI : 0f), this.toString()));
                LASER_HEADSET.play();
            }
            if (counter++ > 3) {
                changeAnimation("idle");
                counter = 0;
            }
            for (Laser laser : laser_rail)
                laser.update();
        }
    }

    @Override
    public void beenHit(){
        super.beenHit();
        changeAnimation("beenHit");
    }


    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " Enemy";
    }

    public void beenHit(Body body1, Body body2){
        super.beenHit(body1, body2);
    }

    public String nameAnim(){
        return sprites.currentAnim;
    }

    public void changeAnimation(String name){
        sprites.changeAnimation(name);
    }

    private float animCounter(float stateTime){
        return sprites.currentAnimation.frameCounter(stateTime);
    }
}
