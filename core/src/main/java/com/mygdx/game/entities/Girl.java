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
    private boolean flip = true;
    private float alpha = 1.0f;
    @Getter @Setter
    private boolean beenHit;
    private Sprite hp = new Sprite(Images.hp);
    private Sprite hpBar = new Sprite(Images.hp2);
    Sprite sprite = new Sprite(Images.girl);

    private float timer, deltaTime;
    public Girl(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
        sprite.flip(flip, false);
    }

    private void update(){ //TODO fazer interface de hp e morte quando zerá-lo
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 5f){
            bullets.add(new Bullet(world, new Vector2(!flip ? getBody().getPosition().x +
                WIDTH / 2f : getBody().getPosition().x - WIDTH / 2f,
                getBody().getPosition().y + HEIGHT / 2f), !flip, (float) Math.PI));
            timer = 0f;
            SHOTGUN.play();
        }
    }

    public void render(SpriteBatch s){
        if (body.getPosition().y > 0) {
            update();

            if (beenHit) {
                timer += Gdx.graphics.getDeltaTime();
                if (timer < 3f) {
                    alpha = new Random().nextFloat(1f);
                    s.setColor(1f,1f,1f,alpha);
//                    sprite.setColor(1f,1f,1f,alpha);
                }
                if (timer > 3f) {
                    beenHit = false;
                    timer = 0f;
                    deltaTime = 0f;
                    alpha = 1f;
                }
                if (deltaTime <= 0.1f) {
                    Sounds.GIRL_HURT.play();
                }
                sprite.setAlpha(alpha);
                deltaTime += Gdx.graphics.getDeltaTime();
            }
            s.draw(hpBar, body.getWorldCenter().x, body.getWorldCenter().y, WIDTH, HEIGHT);
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
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
