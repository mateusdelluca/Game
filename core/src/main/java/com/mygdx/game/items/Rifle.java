package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.PausePage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

import static com.mygdx.game.images.Images.rifle;
import static com.mygdx.game.items.Cartridge.MAX_ROUNDS;

public class Rifle extends Objeto implements Item, Serializable {

    public static final float WIDTH = rifle.getWidth();
    public static final float HEIGHT = rifle.getHeight();
//    public static final int MAX_ROUNDS = 30;
    public final float MULTIPLY = 1/6f;
    @Getter
    private ArrayList<Cartridge> numCartridges = new ArrayList<>();
    @Getter
    private Cartridge leftSideBullets;
    private Vector2 position;

    @Getter @Setter
    private boolean buttonReloadingPressed;
    public static boolean showingNumbBullets = false;
    private int rightSide;
    public static String stringNumbBullets = "";
    @Getter
    private int total;
    private int angle;
    @Getter @Setter
    private boolean reloading;

    public Rifle(Vector2 position){

        super(WIDTH, HEIGHT);
        super.width = WIDTH * MULTIPLY;
        super.height = HEIGHT * MULTIPLY;
        this.position = position;
        super.visible = true;
        body = createBody(new Vector2(width/2f, height/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, body.getAngle());
        body.setUserData(this.toString());

        for (int index = 0; index < 2; index++){
            numCartridges.add(new Cartridge());
        }

        leftSideBullets = new Cartridge();
        for (Cartridge c : numCartridges) {
            total += c.getBulletsLeft().size();
        }
    }

    @Override
    public void render(SpriteBatch s) {
        if (body == null) {
            loadBody(BodyDef.BodyType.StaticBody, true);
            position = bodyData.position;
//            rifle = new Sprite(new Texture(Gdx.files.internal("boy/rifle.png")));
        }
        else
            position = body.getPosition();
        if (!PausePage.pause) {
            rifle.setSize(width, height);
            rifle.setOriginCenter();
            rifle.rotate(1f);
            rifle.setPosition(position.x, position.y);
        }
        if (body.getUserData().toString().equals(this.toString())) {
            rifle.draw(s);
        }
        if (showingNumbBullets) {
            stringNumbBullets = leftSideBullets.getBulletsLeft().size() + "/" + total;
        } else{
            stringNumbBullets = "";
        }
        leftSideBullets.render(s);
    }

    public void reloading() {
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (isReloading()) {
                    leftSideBullets.setBulletsLeft(init(max_bullets()));
                    buttonReloadingPressed = false;
                    setReloading(false);
                }
            }
        }, 1f);
    }

    @Override
    public void updateItem() {
       reloading();
    }

    @Override
    public void updateItem(World world) {
        if (leftSideBullets.getBulletsLeft().isEmpty()){
            reloading = true;
            reloading();
        }
    }

    @Override
    public void update() {
//        bodyData.angle++;
        super.update();
    }

    @Override
    public Objeto objeto() {
        return this;
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    private ArrayList<Bullet> init(int max_rounds){
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int index = 1; index <= max_rounds; index++){
            bullets.add(new Bullet());
        }
        return bullets;
    }

    private void removeBulletsFromFirstCartridge() {
//        if (numCartridges.getFirst().equals(leftSideBullets))
//            return;
        if (!numCartridges.isEmpty()) {
            for (int i = leftSideBullets.getAccumulated(); i > 0; i--)
                numCartridges.getFirst().getBulletsLeft().removeLast();
            numCartridges.removeFirst();
            leftSideBullets.setAccumulated(0);
        }
    }

    /*
    30/30
    25/30
    30/25
    25/25
    30/20
    5/20
    20/0
     */

    private int max_bullets(){
        if ((total + leftSideBullets.getBulletsLeft().size()) >= MAX_ROUNDS) {
            total = total - leftSideBullets.getAccumulated();
            leftSideBullets.setAccumulated(0);
            return MAX_ROUNDS;
        } else{
            int value = total + leftSideBullets.getBulletsLeft().size();
            total = 0;
            return value;
        }
    }

}
