package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

public class Girl extends Objeto{

    public static final float DIVISOR = 1.4f;
    public static final float WIDTH = Images.girl.getWidth()/DIVISOR, HEIGHT = Images.girl.getHeight()/DIVISOR;
    @Getter @Setter
    private boolean flip;
    private float alpha = 1.0f;
    @Getter @Setter
    private boolean beenHit;
    transient Sprite sprite = new Sprite(Images.girl);
    public int HP = 5;
    private float timer, deltaTime;
    private boolean isRunning;
    @Getter @Setter
    private Rifle rifle = new Rifle(new Vector2(-10_000f, -5_000));
    private float deltaTime2;

    public Girl(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
        sprite.flip(!flip, false);
    }

    public void update(){
        super.update();
        if (HP > 0 && visible) {
            deltaTime += Gdx.graphics.getDeltaTime();
            if (deltaTime > 2f) {
                if (!rifle.isReloading()) {
                    if (deltaTime > 5f) {
                        Bullet bullet = new Bullet(new Vector2(!flip ? body.getPosition().x +
                            WIDTH / 2f : body.getPosition().x - WIDTH / 2f,
                            body.getPosition().y + HEIGHT / 2f), !flip, flip ? (float) Math.PI : 0f, true);
                        rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                        deltaTime = 0f;
                        SHOTGUN.play();
                    }
                }
            }
            if (isRunning) {
//                Sounds.GIRL_HURT.play();
                isRunning = false;
            }
            if (rifle.isReloading()) {
                deltaTime2 += Gdx.graphics.getDeltaTime();
                if (deltaTime2 > 0.6f) {
                    deltaTime2 = 0f;
                    rifle.setReloading(false);
                }
            }
            rifle.updateItem(world);
        }
    if (HP <= 0)
        visible = false;
    }

    public void render(SpriteBatch s){
        if (HP <= 0)
            visible = false;
        if (rifle == null)
            rifle = new Rifle(new Vector2(-10_000, -20_000));
        if (body == null && visible) {
            loadBody(BodyDef.BodyType.DynamicBody, false);
            timer = 0f;
        }
        if (sprite == null) {
            sprite = new Sprite(Images.girl);
            sprite.flip(!flip, false);
        }
        if (visible) {
            if (beenHit) {
                timer += Gdx.graphics.getDeltaTime();
                if (timer < 1.1f) {
                    alpha = new Random().nextFloat(1f);
                    sprite.setColor(1f, 1f, 1f, alpha);
                }
                if (timer > 1f) {
                    beenHit = false;
                    timer = 0f;
                    alpha = 1f;
                    HP--;
                }
                if (timer <= 0.05f) {
//                    Sounds.GIRL_HURT.play();
                }
                sprite.setAlpha(alpha);
            }
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
            rifle.render(s);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " Enemy";
    }
    @Override
    public void beenHit(){
        beenHit = true;
    }

}
