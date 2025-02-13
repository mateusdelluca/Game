package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Bullet;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.mygdx.game.screens.levels.Level_Manager.world;
import static com.mygdx.game.sfx.Sounds.SHOTGUN;

public class Jack extends Objeto {

    public static final float DIVISOR = 1.4f;
    public static final float WIDTH = Images.jack.getWidth()/DIVISOR, HEIGHT = Images.jack.getHeight()/DIVISOR;
    private boolean flip = false;
    private float alpha = 1.0f;
    @Getter @Setter
    private boolean beenHit;
    public int HP = 5;
    public Sprite sprite = new Sprite(Images.jack);
    private float timer, deltaTime;
    private Rifle rifle = new Rifle(new Vector2(-1000, -1000));
    private float deltaTime2;

    public Jack(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        sprite.flip(flip, false);
        body.setUserData(this.toString());

        rifle.updateItem();
    }

    public void update(){
        sprite.flip(flip, false);
        if (body == null)
            loadBody(BodyDef.BodyType.DynamicBody, false);
        deltaTime += Gdx.graphics.getDeltaTime();
        rifle.update();
        if (!rifle.isReloading()) {
            sprite = new Sprite(Images.jack);
            if (deltaTime > 4f) {
                Bullet bullet = new Bullet(new Vector2(!flip ? getBody().getPosition().x +
                    WIDTH / 2f : getBody().getPosition().x - WIDTH / 2f,
                    getBody().getPosition().y + HEIGHT / 2f), flip, flip? (float) Math.PI : 0f, true);
                rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                deltaTime = 0f;
                SHOTGUN.play();
            }
        } if (rifle.isReloading()){
            sprite = new Sprite(Images.jack_reloading);
            deltaTime2 += Gdx.graphics.getDeltaTime();
            if (deltaTime2 > 0.4f){
                deltaTime2 = 0f;
                rifle.setReloading(false);
            }
        }
        rifle.updateItem(world);
    }

    public void render(SpriteBatch s){
        if (body.getPosition().y > 0 && HP > 0) {
            update();
            if (beenHit) {
                timer += Gdx.graphics.getDeltaTime();
                if (timer < 3f) {
                    alpha = new Random().nextFloat(1f);
                    sprite.setColor(1f, 1f, 1f, alpha);
                }
                if (timer > 3f) {
                    beenHit = false;
                    timer = 0f;
                    alpha = 1f;
                    HP--;
                }
                if (timer <= 0.05f) {
                    Sounds.HURT.play();
                }
                sprite.setAlpha(alpha);
            }
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
        } else{
            body.setTransform(0,0,0);
        }
        rifle.render(s);
    }


    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Rectangle getBodyBounds() {
        if (HP <= 0)
            return new Rectangle();
        return new Rectangle(body.getPosition().x - 2.5f, body.getPosition().y + 5f, WIDTH + 20f, HEIGHT + 5f);
    }

    @Override
    public void beenHit(){
        beenHit = true;
    }

}
