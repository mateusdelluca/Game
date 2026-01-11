package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.screens.levels.Level_Manager.currentLevelName;
import static com.mygdx.game.screens.levels.Level_Manager.lvl;

public class Portal extends Item{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;

    public boolean open_portal = true;

    public Portal(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setUserData(getClass().getSimpleName());
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch s){
        if (body == null)
            body = bodyData.convertDataToBody(BodyDef.BodyType.StaticBody, true);
        s.setColor(1f,1f,1f,1f);
        s.draw(Images.portal.currentSpriteFrameUpdateStateTime(!open_portal, true, false), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public void updateItem() {
        open_portal = true;
    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {

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
    public void setVisible(boolean b) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void renderShape(ShapeRenderer s) {
        s.rect(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public void setIndex(int index) {

    }

    public Rectangle getRectangle(){
        return new Rectangle(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }


    @Override
    public void beginContact(Body body1, Body body2) {
//        super.beginContact(body1, body2);

        if (body1 == null || body2 == null)
            return;

        if ((body1.equals(body) && body2.getUserData().toString().equals("Player"))
        || (body2.equals(body) && body1.getUserData().toString().equals("Player"))){
            currentLevelName = "Level" + ++lvl;
        }
    }

}
