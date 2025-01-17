package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Rifle extends Objeto implements Item{

    public static final float WIDTH = Images.rifle.getWidth();
    public static final float HEIGHT = Images.rifle.getHeight();
//    public static final int MAX_ROUNDS = 30;
    public final float MULTIPLY = 1/6f;
    @Getter
    private ArrayList<Cartridge> numCartridges = new ArrayList<>();
    private Cartridge currentCartridge;
    private Vector2 position;
    private float angle = 0f;

    @Getter @Setter
    private boolean buttonReloadingPressed;
    public static boolean showingNumbBullets = false;
    private int total;
    public static String stringNumbBullets = "";

    public Rifle(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        super.width = WIDTH * MULTIPLY;
        super.height = HEIGHT * MULTIPLY;
        this.position = position;
        body = createBoxBody(new Vector2(width, height), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());

        for (int index = 0; index < 4; index++){
            numCartridges.add(new Cartridge());
        }
        currentCartridge = numCartridges.getLast();
    }


    @Override
    public void render(SpriteBatch s) {
        if (!body.getUserData().toString().equals("null")) {
            position = body.getPosition();
            Sprite rifle = new Sprite(Images.rifle);
            rifle.setSize(width, height);
            rifle.setOriginCenter();
            rifle.setRotation(angle);
            rifle.setPosition(position.x, position.y);
            rifle.draw(s);
        }
        if (showingNumbBullets) {
            total = 0;
            for (Cartridge c : numCartridges) {
                if (c.equals(currentCartridge))
                    continue;
                total += c.getBulletsLeft().size();
            }
            stringNumbBullets = currentCartridge + "/" + total;
        } else{
            stringNumbBullets = "";
        }
        currentCartridge.render(s);
    }

    public void reloading() {
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (Cartridge.reloading) {
                    if (!currentCartridge.getBulletsLeft().isEmpty() &&
                        currentCartridge.getBulletsLeft().size() < Cartridge.MAX_ROUNDS) {
                        numCartridges.getFirst().getBulletsLeft().clear();
                        numCartridges.getFirst().setBulletsLeft(new Cartridge().init(currentCartridge.getBulletsLeft().size()));
                        currentCartridge.getBulletsLeft().clear();
                        currentCartridge.setBulletsLeft(new Cartridge().init(Cartridge.MAX_ROUNDS));
                    } else {
                        if (!numCartridges.isEmpty()) {
                            if (numCartridges.getLast().getBulletsLeft().isEmpty())
                                numCartridges.removeLast();
                            if (currentCartridge.getBulletsLeft().isEmpty()) {
                                currentCartridge = new Cartridge();
                                if (!numCartridges.isEmpty())
                                    numCartridges.removeLast();
                                numCartridges.addLast(currentCartridge);
                            }
                        }
                    }
                    buttonReloadingPressed = false;
                    Cartridge.reloading = false;
                }
            }
        }, 1f);
    }

    @Override
    public void updateItem() {
       reloading();
    }

    @Override
    public void update() {
        angle++;
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
