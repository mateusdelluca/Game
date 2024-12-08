package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.mygdx.game.screens.levels.Level.bullets;
import static com.mygdx.game.sfx.Sounds.SHOTGUN;

public class Girl extends Objeto{

    public static final float DIVISOR = 1.4f;
    public static final float WIDTH = Images.girl.getWidth()/DIVISOR, HEIGHT = Images.girl.getHeight()/DIVISOR;
    @Getter @Setter
    private boolean flip = true;
    private float alpha = 1.0f;
    @Getter @Setter
    private boolean beenHit;
    Sprite sprite = new Sprite(Images.girl);
    public int HP = 5;
    private float timer, deltaTime;
    private boolean isRunning;

    public Girl(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
        sprite.flip(flip, false);
    }

    private void update(){
        deltaTime += Gdx.graphics.getDeltaTime();
        if (deltaTime > 2f){
            bullets.add(new Bullet(world, new Vector2(!flip ? getBody().getPosition().x +
                WIDTH / 2f : getBody().getPosition().x - WIDTH / 2f,
                getBody().getPosition().y + HEIGHT / 2f), !flip, (float) Math.PI));
            deltaTime = 0f;
            SHOTGUN.play();
        }
        if (isRunning) {
            Sounds.GIRL_HURT.play();
            isRunning = false;

        }
    }

    public void render(SpriteBatch s){
        if (body.getPosition().y > 0 && HP > 0) {
        update();
        if (beenHit) {
            timer += Gdx.graphics.getDeltaTime();
            if (timer < 0.05f)
                isRunning = true;
            if (timer < 1.5f) {
                alpha = new Random().nextFloat(1f);
                s.setColor(1f,1f,1f,alpha);
//                    sprite.setColor(1f,1f,1f,alpha);
            }
            if (timer > 1.5f) {
                timer = 0f;
//                    deltaTime = 0f;
                alpha = 1f;
                isRunning = false;
                beenHit = false;
            }

            sprite.setAlpha(alpha);
//                deltaTime += Gdx.graphics.getDeltaTime();
        }
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
        } else{
            body.setTransform(0,0,0);
        }
    }

    @Override
    public void render(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
