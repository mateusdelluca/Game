package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.images.Robot2_Sprites;
import com.mygdx.game.items.Laser;
import lombok.Getter;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.images.Images.laser_rail;
import static com.mygdx.game.images.Robot2_Sprites.HEIGHT;
import static com.mygdx.game.images.Robot2_Sprites.WIDTH;
import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;

public class Robot extends Objeto{

    @Getter
    private Character_Features char_features;
    private Robot2_Sprites sprites = new Robot2_Sprites();
    private Array<Laser> laser_rail = new Array<>();
    private boolean looping = true, facingRight, useOnlyLastFrame;

    public Robot(Vector2 position){
        super(WIDTH, HEIGHT);
        dimensions = new Vector2(46/2f, 108/2f);
        body = box(position, dimensions, BodyDef.BodyType.DynamicBody, false);
        char_features = new Character_Features();
        visible = true;
        facingRight = false;
        sprites.changeAnimation("fire");
    }

    @Override
    public void render(SpriteBatch s) {
        update();
        if (visible) {
//            Sprite sprite = new Sprite(sprites.currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
//            sprite.setPosition(body.getPosition().x - dimensions.x - 20f, body.getPosition().y - dimensions.y/2f);
//            sprite.setSize(Robot2_Sprites.WIDTH, Robot2_Sprites.HEIGHT);
//            sprite.draw(s);
            Sprite sprite = new Sprite(sprites.currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
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
        if (sprites.nameOfAnimation.equals("fire"))
            if (sprites.currentAnimation.ani_finished()){
                laser_rail.add(new Laser(
                    new Vector2(!facingRight ? (getBody().getPosition().x +
                        WIDTH) : (getBody().getPosition().x - WIDTH),
                        getBody().getPosition().y + (HEIGHT / 4f)),
                    !facingRight,(!facingRight ? (float) Math.PI : 0f), this.toString() ));
                LASER_HEADSET.play();
                sprites.currentAnimation.resetStateTime();
                looping = true;
            }
        for (Laser laser : laser_rail)
            laser.update();
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
