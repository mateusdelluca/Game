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

import static com.mygdx.game.sfx.Sounds.SHOTGUN;

public class Jack extends Objeto {

    public static final float DIVISOR = 1.4f;
    public static final float WIDTH = Images.jack.getWidth()/DIVISOR, HEIGHT = Images.jack.getHeight()/DIVISOR;
    private boolean facingLeft = true;
    private float alpha = 1.0f;
//    @Getter @Setter
//    private boolean beenHit;
    public transient Sprite sprite = new Sprite(Images.jack);
    private float timer, deltaTime;
//    @Getter
//    private Rifle rifle = new Rifle(new Vector2(-10000, -10000));
    private float deltaTime2;

    private Sprite sprite2 = new Sprite(Images.jack_reloading);

    private Bullet bullet;

    public Jack(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
        sprite.flip(facingLeft, false);
        body.setUserData(this.toString());
    }

    public void update(){
        super.update();
        if (character_features.getHp() > 0 && visible) {
            deltaTime += Gdx.graphics.getDeltaTime();
//            rifle.update();
//            if (!rifle.isReloading()) {
                if (deltaTime > 5f) {
//                    if (rifle.getTotal() > 0) {
                        bullet = new Bullet(new Vector2(!facingLeft ? body.getPosition().x +
                            WIDTH / 2f : body.getPosition().x - WIDTH / 2f,
                            body.getPosition().y + HEIGHT / 2f), facingLeft, 0f, false, this.toString());
//                        rifle.getLeftSideBullets().addAndRemove(bullet, rifle);
                        deltaTime = 0f;
                        SHOTGUN.play();
//                    }
                }
//            }
//            if (rifle.isReloading() && rifle.getTotal() > 0) {
//                deltaTime2 += Gdx.graphics.getDeltaTime();
//                if (deltaTime2 > 0.6f) {
//                    deltaTime2 = 0f;JACK_RELOADING.play();
//                    rifle.setReloading(false);
//                }
//            }
//            rifle.updateItem(world);
        }
        if (character_features.getHp() <= 0){
            visible = false;
        }
    }

    @Override
    public void render(SpriteBatch s){
        if (character_features.getHp() <= 0)
            visible = false;
//        if (rifle == null)
//            rifle = new Rifle(new Vector2(-10_000, -20_000));
        if (body == null && visible) {
            loadBody(BodyDef.BodyType.DynamicBody, false);
            timer = 0f;
        }
        if (sprite == null) {
            sprite = new Sprite(Images.jack);
            sprite.flip(facingLeft, false);
        }
        if (visible) {
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            if (beenHit) {
                timer += Gdx.graphics.getDeltaTime();
                if (timer < 0.5f) {
                    alpha -= 0.1f;
                    sprite.setAlpha(alpha);
                } else {
                        beenHit = false;
                        timer = 0f;
                        alpha = 1f;
                        sprite.setAlpha(alpha);
                        Sounds.HURT.play();
                    }
                }
            sprite.draw(s);
            if (bullet != null) {
                bullet.render(s);
            }
        }
    }


    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " Enemy";
    }

    public Rectangle getBodyBounds() {
        if (character_features.getHp() <= 0 || body == null)
            return new Rectangle();
        return new Rectangle(body.getPosition().x - 2.5f, body.getPosition().y + 5f, WIDTH + 20f, HEIGHT + 5f);
    }

    @Override
    public void beenHit(){
        super.beenHit();
    }

}
