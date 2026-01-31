package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.mygdx.game.screens.Inventory.positionsToFill;
import static com.mygdx.game.screens.Stats.level_font;
import static com.mygdx.game.screens.levels.Level.player;
import static com.mygdx.game.sfx.Sounds.clink2;

public class Crystal extends Item{

    public static final float WIDTH = 40, HEIGHT = 80;
    public static int quantity;

    public static boolean took;

    private Sprite sprite = new Sprite(Images.crystal);

    public static int i;
    private int id;
    public Crystal(Vector2 transformPosition){
        super(WIDTH, HEIGHT, Crystal.class.getSimpleName());
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(transformPosition, 0);
        body.setUserData(this.hashCode());
        visible = true;
    }

    @Override
    public void render(SpriteBatch s) {
        super.render(s);
        if (visible) {
            s.draw(sprite, body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
        } else{
            body.setTransform(new Vector2(-100_000, -100_000), 0);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }
    @Override
    public void drawItemInSlot(SpriteBatch spriteBatch, float x, float y, String name){
        itemSprite = Images.getItemDraw(name);
        itemSprite.setOrigin(0,0);
        itemSprite.setPosition(positionsToFill.get(index).x, positionsToFill.get(index).y);
        itemSprite.draw(spriteBatch);
        drawQuantity(spriteBatch);
    }

    @Override
    protected void drawQuantity(SpriteBatch spriteBatch){
        level_font.draw(spriteBatch, "" + quantity, positionsToFill.get(index).x + WIDTH, positionsToFill.get(index).y);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void update() {
        super.update();
    }

    public void beginContact(Body body1, Body body2){
        if ((body1.getUserData().toString().equals("" + this.hashCode()) && body2.getUserData().toString().contains("Player"))
            ||
            body2.getUserData().toString().equals("" + this.hashCode()) && body1.getUserData().toString().contains("Player")){
            clink2.play();
//            items.put(this.toString(), this);
            if (!took) {
                player.takeItem(this);
                took = true;
            }
            visible = false;
            this.beenTaken = true;
            quantity++;
        }
    }
}
