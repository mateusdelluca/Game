package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;

public class Grass {

    public static final int TILE_SIZE = 20;
    public static final int WIDTH = 300 * TILE_SIZE, HEIGHT = 300;
    private Body body;
    private PolygonShape polygonShape;
    private FixtureDef fixtureDef;


    public Grass(World world){
        body = createBoxBody(world);
    }

    private Body createBoxBody(World world){
        Vector2 dimensions = new Vector2(WIDTH/2f, HEIGHT/2f);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(WIDTH/2f, HEIGHT/2f,dimensions,0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 0;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.createFixture(fixtureDef);
        body.setActive(true);
        fixtureDef.density = 1.0f;
        return body;
    }

    public void render(SpriteBatch spriteBatch){
        for (int i = 0; i < TILE_SIZE; i++)
            spriteBatch.draw(Images.grass, i * 300,0);
    }

    public Body getBody() {
        return body;
    }

}
