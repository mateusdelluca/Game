package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.Animations;

import static com.mygdx.game.sfx.Sounds.*;

public class Enemy extends Stickman{

    public Animations animations = Animations.E_IDLE;
    private float timer;

    private float rotation;
    private float y;

    public Enemy(World world, Vector2 position) {
        super(world);
        flip = true;
        box.getBody().setTransform(position, 0);
        y = position.y;
        getBody().setFixedRotation(true);
    }

    @Override
    public void render(SpriteBatch s) {
        if (visible) {
            Sprite s1 = new Sprite(animations.animator.currentSpriteFrame(false, loopTrueOrFalse(animations.name()), flip));
            s1.setOrigin(0, 0);
//        s1.setCenter(getBody().localPoint2.x, getBody().localPoint2.y);
            s1.setPosition(getBody().getPosition().x, getBody().getPosition().y);
//        s1.translate(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y);
            rotation = (float) Math.toDegrees(getBody().getTransform().getRotation());
            s1.setRotation(rotation);
            s1.draw(s);
        }
    }

    @Override
    public void update(float delta) {
        animation();
    }

    private void animation(){
        String name = animations.name();
//        getBody().setFixedRotation(false);
        if (Math.abs(rotation) >= 50f) {
            animations = Animations.E_HITED;
            timer += Gdx.graphics.getDeltaTime();
//            for (int i = 0; i < getBody().getFixtureList().size; i++)
//                getBody().getFixtureList().get(i).setFriction(10f);
            box.getBody().setLinearVelocity(0f, box.getBody().getLinearVelocity().y);
        }
        if (timer >= 5f) {
            box.getBody().setTransform(getBody().getPosition().x, y,0);
            rotation = 0;
            timer = 0f;
            getBody().setFixedRotation(true);
            animations = Animations.E_IDLE;
        }
        if (name.equals("E_SPLIT")){
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
            if (name.equals("E_PUNCHED")) {
                if (animations.animator.ani_finished()) {
                    animations.animator.resetStateTime();
                    animations = Animations.E_IDLE;
                    getBody().setLinearVelocity(0, 0);
                    PUNCHED.play();
                    hited = false;
                }
            } else {
            if (name.equals("E_PUNCH") && !hited) {
                if (frameCounter() == 1) {
                    WHOOSH.play();
                    setFrameCounter(2);
                }
                if (animations.animator.ani_finished()) {
                    animations.animator.resetStateTime();
                    animations = Animations.E_IDLE;
                }
            } else {
                    if (animations.name().equals("E_IDLE")) {
                        getBody().setLinearVelocity(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y);
                        getBody().setFixedRotation(false);
                    }
                }
            }
        }

    }

    @Override
    public void render(ShapeRenderer s) {
        s.rotate(rotation, 0, 0, 0);
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().getWidth(), getBodyBounds().getHeight());
    }


    public void setAnimation(String name){
        animations = Animations.valueOf(name);
    }

    public void dispose(){
        super.dispose();
        animations.getAnimator().dispose();
        JUMP.dispose();
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

    public float frameCounter(){
        return animations.animator.getAnimation().getKeyFrame(animations.animator.stateTime).getU2() * animations.animator.getNumFrames();
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public Rectangle getAction(){
        if (animations.name().equals("E_PUNCH"))
            return new Rectangle(!flip ? getBody().getPosition().x + WIDTH/2f : getBody().getPosition().x + WIDTH/2f -80,
                    getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
        return new Rectangle();
    }

}
