package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.PausePage;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.images.Images.headset_laser;
import static com.mygdx.game.images.Images.rifle;
import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;

public class Laser_Headset extends Item{

    public static final float WIDTH = 75f, HEIGHT = 85f;

    private Vector2 position;
    private Sprite sprite = new Sprite(headset_laser);
    public Laser_Headset(Vector2 position){
        super(WIDTH,HEIGHT);
        this.position = position;
        super.dimensions = new Vector2(WIDTH,HEIGHT);
        body = box(position, dimensions, BodyDef.BodyType.StaticBody, true);
        body.setUserData(this.toString());
        bodyData = new BodyData(body, new Vector2(width/2f, height/2f), width, height);
    }

    @Override
    public void render(SpriteBatch s) {
        if (visible) {

            if (!PausePage.pause) {
                sprite.setSize(width, height);
                sprite.setOriginCenter();
                sprite.rotate(1f);
                sprite.setPosition(position.x, position.y);
            }
            if (body.getUserData().toString().equals(this.toString())) {
                sprite.draw(s);
            }
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public void updateItem() {
        visible = true;
    }

    @Override
    public void updateItem(World world) {
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setUserData(Body body) {
    }

    @Override
    public void setUserData(String name) {
    }

    @Override
    public BodyData getBodyData() {
        return null;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
