package com.mygdx.game.handler;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    public MyContactListener(World world){
        world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA == null || fixtureB == null)
            return;
        if (fixtureA.getBody() == null || fixtureB.getBody() == null)
            return;
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null)
            return;
        if (fixtureA.getBody().getUserData().toString().equals("Bullet")){
            System.out.println(true);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
