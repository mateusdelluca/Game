package com.mygdx.game.platform;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.Objeto;

public class RopeKnot extends Objeto implements InputProcessor {



    public RopeKnot(Vector2 position, int length){

        for (int i = 0; i < length; i++){

        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }


    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {

        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void beginContact(Body bodyA, Body bodyB) {
        if (bodyA == null || bodyB == null)
            return;


       //TODO: análise de colisões entre o gatilho da ninjaRope para saber a extensão da Rope

    }
}
