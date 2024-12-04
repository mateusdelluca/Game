package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.Animations;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

public class Monster1 extends Objeto{

    public static final float WIDTH = 94, HEIGHT = 128;
    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, looping = true, facingRight;
    private Vector2 dimensions = new Vector2(78f, 118f);
    private float flickering_time;
    @Setter @Getter
    private float HP = 5;
    @Setter
    @Getter
    private boolean split;
    public static int id;
    public Monster1(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }
    @Override
    protected Body createBoxBody(Vector2 dimensions, BodyDef.BodyType bodyType, boolean isSensor){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(dimensions.x, dimensions.y, new Vector2(width/2f, height/2f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100f;
        fixtureDef.isSensor = isSensor;
        Body body = world.createBody(bodyDef);
        body.setUserData(getClass().getSimpleName() + id++);
        body.setActive(true);
        body.createFixture(fixtureDef);
        System.out.println(body.getUserData());
        return body;
    }

    public void render(SpriteBatch spriteBatch){
        if (visible){
            update();
            Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingRight));
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(spriteBatch);
        }
    }

    private void update(){
        String name = animations.name();
        if (HP <= 0){
            animations = Animations.MONSTER1_SPLIT;
            split = true;
        }
        if (name.equals("MONSTER1_SPLIT")){
            looping = false;
            for (Fixture f : getBody().getFixtureList()){
                f.setSensor(true);
            }
            getBody().setGravityScale(0f);
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task(){
                @Override
                public void run() {
                    setVisible(false);
                }
            }, 3);
        } else {
            if (name.equals("MONSTER1_FLICKERING")) {
                flickering_time += Gdx.graphics.getDeltaTime();
                if (flickering_time >= 1.5f) {
                    flickering_time = 0f;
                    animations = Animations.MONSTER1_WALKING;
                    HP--;
                    body.setLinearVelocity(0,0);
                    Sounds.MONSTER_HURT.play();
                }
            }
        }
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + id;
    }

    public Rectangle getBodyBounds() {
        if (split)
            return new Rectangle();
        return new Rectangle(body.getPosition().x - 2.5f, body.getPosition().y + 5f, dimensions.x + 20f, dimensions.y + 5f);
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

}
