package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.images.Animator;

public class Images {
    public static Texture[] saves = new Texture[6];
    public static Texture box;
    public static Texture menu;
    public static Texture level1;
    public static Texture equipped;
    public static Texture baseball_bat;
    public static Texture baseball_bat_equip;
    public static Texture game_over;
    public static Texture load_page;
    public static Texture save_page;
    public static Texture block;
    public static Animator sea, tiger;
    public static Texture grass, grass2, ny;
    public static Texture printScreen;
    public static Texture saber;
    public static Texture sabre_equip;
    public static Texture cap, cap2;
    public static Texture cap_equip, cap_equip2;
    public static Texture sunglasses, sunglasses2;
    public static Texture sunglasses_equip, sunglasses_equip2;
    public static Texture headset, headset_equip;
    public static Texture tiger_face;
    public static Sprite s1;
    public static Texture backGround;
    public static Texture crystal, crystal3;
    public static Texture bullet;
    public static Texture splashScreen;
    public static Animator portal;
    public static Texture hp, hp2;
    public static Texture sp, sp2;
    public static Texture pauseBox;
    public static Texture jack, girl;
    public static Texture leaf;
    public static Texture shooting1, shooting2, shoot;
    public SpriteBatch spriteBatch;

    public Images() {

        box = new Texture(Gdx.files.internal("saves/Box.png"));
//        menu = new Texture(Gdx.files.internal("src/main/res/Menu.png"));
//        game_over = new Texture(Gdx.files.internal("src/main/res/Game Over.png"));
//        level1 = new Texture(Gdx.files.internal("src/main/res/Level1.png"));
        load_page = new Texture(Gdx.files.internal("Load_Page.png"));
        save_page = new Texture(Gdx.files.internal("Save_Page.png"));
//        block = new Texture(Gdx.files.internal("src/main/res/objects/Crate.png"));
//        equipped = new Texture(Gdx.files.internal("src/main/res/Equiped.png"));
//        baseball_bat = new Texture(Gdx.files.internal("src/main/res/objects/Baseball_bat.png"));
//        baseball_bat_equip = new Texture(Gdx.files.internal("src/main/res/objects/Baseball_bat_equip.png"));
//        sabre = new Texture(Gdx.files.internal("src/main/res/objects/Sabre.png"));
//        sabre_equip = new Texture(Gdx.files.internal("src/main/res/objects/Sabre_equip.png"));
//        grass = new Texture(Gdx.files.internal("objects/Grass.png"));
//        grass2 = new Texture(Gdx.files.internal("objects/Grass2.png"));
        backGround = new Texture(Gdx.files.internal("background/Mountains4.png"));
//        ny = new Texture(Gdx.files.internal("src/main/res/background/ny2.png"));
//        cap = new Texture(Gdx.files.internal("src/main/res/objects/Cap.png"));
//        cap2 = new Texture(Gdx.files.internal("src/main/res/objects/Cap2.png"));
//        cap_equip = new Texture(Gdx.files.internal("src/main/res/objects/Cap_equip.png"));
//        cap_equip2 = new Texture(Gdx.files.internal("src/main/res/objects/Cap_equip2.png"));
//        sunglasses = new Texture(Gdx.files.internal("src/main/res/objects/Sunglasses.png"));
//        sunglasses_equip = new Texture(Gdx.files.internal("src/main/res/objects/Sunglasses_equip.png"));
//        sunglasses2 = new Texture(Gdx.files.internal("src/main/res/objects/Sunglasses2.png"));
//        sunglasses_equip2 = new Texture(Gdx.files.internal("src/main/res/objects/Sunglasses_equip2.png"));
//        headset = new Texture(Gdx.files.internal("src/main/res/objects/Headset.png"));
//        headset_equip = new Texture(Gdx.files.internal("src/main/res/objects/Headset_equip.png"));
//        sea = new Animator(7, 7, 20,1024, 193, "src/main/res/background/sea7.png");
//        tiger = new Animator (3,3,20,256,256, "src/main/res/objects/Tiger.png");
//        tiger_face = new Texture(Gdx.files.internal("src/main/res/objects/Tiger_face.png"));
//        deleteTab();
        saber = new Texture(Gdx.files.internal("Saber.png"));
        s1 = new Sprite(saber);
        crystal = new Texture(Gdx.files.internal("objects/Crystal.png"));
        crystal3 = new Texture("objects/Crystal3_red.png");
        bullet = new Texture("objects/Bullet.png");
        splashScreen = new Texture(Gdx.files.internal("background/SplashScreen 5.png"));
        portal = new Animator(23, 10, 25, 857, 873, "objects/Portal_Spritesheet.png");
        hp = new Texture(Gdx.files.internal("HP_Bar.png"));
        hp2 = new Texture(Gdx.files.internal("HP_Bar2.png"));
        sp = new Texture(Gdx.files.internal("SP_Bar.png"));
        sp2 = new Texture(Gdx.files.internal("SP_Bar2.png"));
        pauseBox = new Texture(Gdx.files.internal("PauseBox.png"));
        for (int i = 0; i < saves.length; i++){
            saves[i] = new Texture(Gdx.files.internal("saves/Save" + i + ".png"));
        }
        jack = new Texture(Gdx.files.internal("Jack/Jack.png"));
        girl = new Texture(Gdx.files.internal("Girl/Girl.png"));
        shoot = new Texture(Gdx.files.internal("boy/Shoot.png"));
        shooting1 = new Texture(Gdx.files.internal("boy/Shooting1.png"));
        shooting2 = new Texture(Gdx.files.internal("boy/Shooting2.png"));
        leaf = new Texture(Gdx.files.internal("boy/Leaf.png"));
        spriteBatch = new SpriteBatch();}

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



}
