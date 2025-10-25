package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static com.mygdx.game.images.Images.*;
public class Images2 {

    public Images2(){
        power = new Sprite(new Texture(Gdx.files.internal("boy/FUEL.png")));
        jack = new Texture(Gdx.files.internal("Jack/Jack.png"));
        girl = new Texture(Gdx.files.internal("Girl/Girl.png"));
        shoot = new Sprite(new Texture(Gdx.files.internal("boy/Shoot.png")));
        shooting1 = new Sprite(new Texture(Gdx.files.internal("boy/Shooting1.png")));
        shooting2 = new Sprite(new Texture(Gdx.files.internal("boy/Shooting2.png")));
        ninjaRope_shoot = new Sprite(new Texture(Gdx.files.internal("boy/NinjaRope.png")));
        rope = new Sprite(new Texture(Gdx.files.internal("boy/NinjaRope2.png")));
        leaf = new Texture(Gdx.files.internal("boy/Leaf.png"));
        fire = new Animator(4, 4, 5, 32, 55, "fire/fire.png");
        fan = new Animator(4, 4, 15, 76, 93, "fan/Fan.png");
        fan2 = new Animator(4, 4, 15, 76, 79, "fan/Fan2.png");
        rifle = new Sprite(new Texture(Gdx.files.internal("boy/rifle.png")));
        jetPack = new Sprite(new Texture(Gdx.files.internal("boy/JetPack.png")));
        aim = new Sprite(Images.shoot);
        spriteJetPack = new Sprite(new Texture(Gdx.files.internal("boy/JetPack2.png")));
        ninjaStar = new Texture(Gdx.files.internal("objects/NinjaStar.png"));
        saber_inventory = new Sprite(new Texture(Gdx.files.internal("items/Saber_75x85.png")));
        rifle_inventory = new Sprite(new Texture(Gdx.files.internal("items/Rifle_75x85.png")));
        ninjaStar_inventory = new Sprite(new Texture(Gdx.files.internal("items/NinjaStar_75x85.png")));
        jetPack_inventory = new Sprite(new Texture(Gdx.files.internal("items/JetPack_75x85.png")));
        equipped_inventory = new Sprite(new Texture(Gdx.files.internal("items/Equipped_75x85.png")));
        ninjaRope_inventory = new Sprite(new Texture(Gdx.files.internal("items/NinjaRope_75x85.png")));
        sword_inventory = new Sprite(new Texture(Gdx.files.internal("items/Sword_75x85.png")));
        equipped_inventory.setSize(67, 74);
    }






}
