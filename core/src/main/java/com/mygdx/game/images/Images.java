package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.mygdx.game.screens.Tile;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.files.FileHandle;

public class Images implements Serializable {
    public static Texture[] saves = new Texture[6];
    public static Texture box;
    public static Texture game_over;
    public static Texture load_page;
    public static Texture save_page;
    public static Sprite saber;
    public static Sprite s1, rifle, jetPack;
    public static Texture ninjaStar;
    public static Sprite mountains4;

    public static Sprite backGround2;
    public static Texture crystal, crystal_red;
    public static Texture bullet;
    public static Texture splashScreen;
    public static Animator portal, fire, fan, fan2;
    public static Sprite hp, sp, bar;
    public static Texture pauseBox;

    public static Sprite inventory, saber_inventory, rifle_inventory, jetPack_inventory, ninjaStar_inventory, equipped_inventory;
    public static Texture jack, jack_reloading, girl;
    public static Texture leaf;
    public static Sprite shooting1, shooting2, shoot;
    public static Sprite top;
    public static Sprite aim;
    public static Sprite legs;
    public static MapObjects thorns ;
    public static MapObjects staticObjects;
    @Getter
    @Setter
    public static Tile tile;
    public static BitmapFont font;

    public static Sprite spriteJetPack;
    public static Sprite jetPackSprite = new Sprite(Animations.BOY_JETPACK.getAnimator().currentSpriteFrame(false, true, false));

    public SpriteBatch spriteBatch;

    public static Sprite[] sprites = new Sprite[8];

    public static Pixmap pixMapBox;

    public static HashMap<String, Sprite> itemsDraw = new HashMap<>();

    public Images() {
        box = new Texture(Gdx.files.internal("saves/Box.png"));
//        menu = new Texture(Gdx.files.internal("src/main/res/Menu.png"));
//        game_over = new Texture(Gdx.files.internal("src/main/res/Game Over.png"));
        load_page = new Texture(Gdx.files.internal("Load_Page.png"));
        save_page = new Texture(Gdx.files.internal("Save_Page.png"));
        saber = new Sprite(new Texture(Gdx.files.internal("objects/Saber.png")));
        mountains4 = new Sprite(new Texture(Gdx.files.internal("background/Mountains4.png")));
        backGround2 = new Sprite(new Texture(Gdx.files.internal("background/Background.png")));
        jack_reloading = new Texture(Gdx.files.internal("Jack/Jack_Reloading.png"));
        saber = new Sprite(new Texture(Gdx.files.internal("Saber.png")));
        s1 = new Sprite(saber);
        crystal = new Texture(Gdx.files.internal("objects/Crystal.png"));
        crystal_red = new Texture("objects/Crystal3_red.png");
        bullet = new Texture("objects/Bullet.png");
        splashScreen = new Texture(Gdx.files.internal("background/SplashScreen 5.png"));
        portal = new Animator(23, 10, 25, 857, 873, "objects/Portal_Spritesheet.png");
        bar = new Sprite(new Texture(Gdx.files.internal("boy/Bar.png")));
        hp = new Sprite(new Texture(Gdx.files.internal("boy/HP.png")));
//        hp2 = new Texture(Gdx.files.internal("HP_Bar2.png"));
        sp = new Sprite(new Texture(Gdx.files.internal("boy/SP.png")));
//        sp2 = new Texture(Gdx.files.internal("SP_Bar2.png"));
        pauseBox = new Texture(Gdx.files.internal("PauseBox.png"));
        try {
            for (int i = 0; i < saves.length; i++) {
//                saves[i] = new Texture(Gdx.files.local("saves/Save" + i + ".png"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        jack = new Texture(Gdx.files.internal("Jack/Jack.png"));
        girl = new Texture(Gdx.files.internal("Girl/Girl.png"));
        shoot = new Sprite(new Texture(Gdx.files.internal("boy/Shoot.png")));
        shooting1 = new Sprite(new Texture(Gdx.files.internal("boy/Shooting1.png")));
        shooting2 = new Sprite(new Texture(Gdx.files.internal("boy/Shooting2.png")));
        leaf = new Texture(Gdx.files.internal("boy/Leaf.png"));
        fire = new Animator(4, 4, 5, 32, 55, "fire/fire.png");
        fan = new Animator(4, 4, 15, 76, 93, "fan/Fan.png");
        fan2 = new Animator(4, 4, 15, 76, 79, "fan/Fan2.png");
        rifle = new Sprite(new Texture(Gdx.files.internal("boy/rifle.png")));
        jetPack = new Sprite(new Animator(1,1,1,128,128, "boy/JetPack.png").getFrame(0));
        aim = new Sprite(Images.shoot);
        spriteJetPack = Images.jetPack;
        ninjaStar = new Texture(Gdx.files.internal("objects/NinjaStar.png"));
        saber_inventory = new Sprite(new Texture(Gdx.files.internal("items/Saber_75x85.png")));
        rifle_inventory = new Sprite(new Texture(Gdx.files.internal("items/Rifle_75x85.png")));
        ninjaStar_inventory = new Sprite(new Texture(Gdx.files.internal("items/NinjaStar_75x85.png")));
        jetPack_inventory = new Sprite(new Texture(Gdx.files.internal("items/JetPack_75x85.png")));
        equipped_inventory = new Sprite(new Texture(Gdx.files.internal("items/Equipped_75x85.png")));
        equipped_inventory.setSize(67,74);
        itemsDraw.put("Saber", saber_inventory);
        itemsDraw.put("Rifle", rifle_inventory);
        itemsDraw.put("JetPack", jetPack_inventory);
        itemsDraw.put("NinjaStar", ninjaStar_inventory);
        itemsDraw.put("Equipped", equipped_inventory);

        inventory = new Sprite(new Texture(Gdx.files.internal("items/Inventory_clear.png")));
        try {
            for (int k = 0; k < 6; k++) {
                FileHandle file = Gdx.files.local("saves/Save" + k + ".png"); // Caminho do arquivo de entrada
                Pixmap pixmap = new Pixmap(file);
                sprites[k] = new Sprite(new Texture(pixmap));
                sprites[k].flip(false, true);
                sprites[k].setSize(248, 166);
//                sprites[k] = new Sprite(Images.box);
            }
            sprites[6] = new Sprite(Images.box);
//            for (int i = 0; i < screenshootsSaves.length; i++) {
//                Texture t = new Texture(Gdx.files.external("Save" + i + ".png"));
//                screenshootsSaves[i] = new Sprite(t);
//                screenshootsSaves[i].setSize(248,166);
//            }
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        pixMapBox = new Pixmap(248, 166, Pixmap.Format.RGBA8888);
        pixMapBox.setColor(Color.WHITE);
        pixMapBox.fill();
        spriteBatch = new SpriteBatch();
    }

//    public static BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
//        double radians = Math.toRadians(degrees);
//        BufferedImage b = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
//        Graphics2D g2D = b.createGraphics();
//        g2D.rotate(radians, (double) originalImage.getWidth()/2,(double) originalImage.getHeight()/2);
//        g2D.drawImage(originalImage, null, null);
////            g2D.dispose();
//        return b;
//    }
//
//    public static BufferedImage rotateImage(BufferedImage originalImage, double degrees, int width, int height) {
//        double radians = Math.toRadians(degrees);
//        BufferedImage b = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
//        Graphics2D g2D = b.createGraphics();
//        g2D.rotate(radians, (double) originalImage.getWidth()/2,(double) originalImage.getHeight()/2);
//        g2D.drawImage(originalImage, null, null);
////            g2D.dispose();
//        return b;
//    }

//    private Texture paint(Pixmap pixmap, Color color){
//        try {
//            for (int i = 0; i < pixmap.getWidth(); i++) {
//                for (int j = 0; j < pixmap.getHeight(); j++) {
//                    int pixel = pixmap.getPixel(i, j);
//                    if ((pixel & 0x000000FF) != 0) {
//                        pixmap.setColor(color);
//                        pixmap.drawPixel(i,j, Color.rgba8888(color.r, color.g, color.b, color.a));
//                    }
//                }
//            }
//            return new Texture(pixmap);
//        } catch(GdxRuntimeException e){
//
//        }
//        return null;
//    }





//    public static void deleteTab(){
//        for(int i = 0; i < saves.length; i++) {
//            saves[i] = new Texture(Gdx.files.internal("src/main/res/saves/Save" + i + ".png"));
//            Pixmap pixmap = new Pixmap(saves[i].getWidth(), saves[i].getHeight(), Pixmap.Format.RGBA8888);
//            saves[i].getTextureData().prepare();
//            pixmap.draw(saves[i].getTextureData().consumePixmap(), 0, 0);
//            pixmap.getPixel(10,10);
//            int pixel = saves[i].getRGB(100, 100);
//            int red = (pixel & 16711680) >> 16;
//            int green = (pixel & '\uff00') >> 8;
//            int blue = pixel & 255;
//            if (red != 0 || green != 0 || blue != 0) {
//                Load_Page.exist[i] = true;
//                //System.out.println("existe aba colorida");
//
//            }
//            if (red == 0 && green == 0 && blue == 0) {
//                Load_Page.exist[i] = false;
//                overrideBufferedImage(i);
//                //System.out.println("Esta aba está toda em preto");
//            }
//
//            try {
//                ImageIO.write(saves[i], "png", new File("src/main/res/saves/Save" + i + ".png"));
//            } catch (IOException var7) {
//                var7.printStackTrace();
//            }
//        }
//    }
//
//
//    public static void deleteTab(int i){
//        int pixel = saves[i].getRGB(100, 100);
//        int red = (pixel & 16711680) >> 16;
//        int green = (pixel & '\uff00') >> 8;
//        int blue = pixel & 255;
//        if (red != 0 || green != 0 || blue != 0) {
//            Load_Page.exist[i] = false;
//            saves[i] = new BufferedImage(248, 166, BufferedImage.TYPE_INT_RGB);
//        }
//
//        try {
//            ImageIO.write(saves[i], "png", new File("src/main/res/saves/Save" + i + ".png"));
//        } catch (IOException var7) {
//            var7.printStackTrace();
//        }
//
//    }
//
//    public static void overrideBufferedImage(int i) {
//
//        try {
//            ImageIO.write(saves[i], "png", new File("src/main/res/saves/Save" + i + ".png"));
//        } catch (IOException var7) {
//            var7.printStackTrace();
//        }
//    }
//
//    public static void delete(int i){
//        Delete.delete("src/main/res/saves/Save" + i + ".nda");
//    }
//
//

    public static Sprite getItemDraw(String name){
        return itemsDraw.get(name);
    }

}
