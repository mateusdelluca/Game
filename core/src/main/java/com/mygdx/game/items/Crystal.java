package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Crystal extends Objeto implements Item{

    public static final float WIDTH = 40, HEIGHT = 80;
    private int index;
    @Getter
    @Setter
    private int rand = new Random().nextInt(2);
    public Crystal(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
    }

    public Crystal(Vector2 position, int index){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        this.index = index;
        body.setUserData(this);
    }

    @Override
    public void render(SpriteBatch s) {
        if (body == null)
            loadBody(BodyDef.BodyType.StaticBody, true);
        if (visible)
            if (rand > 0)
                s.draw(Images.crystal, body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
            else
                s.draw(Images.crystal_red, body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + index;
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
        super.update();
    }
    @Override
    public void setVisible(boolean b){
        visible = b;
    }
}
