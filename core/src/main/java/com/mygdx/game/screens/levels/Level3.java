package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.*;
import com.mygdx.game.items.*;
import com.mygdx.game.items.fans.Fans;

import java.util.HashMap;

import static com.mygdx.game.items.Rope.NUM_ROPES;

public class Level3 extends Level{
    public Level3() throws Exception {
        super();
        monsters1.clear();

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(640, 6000 - 2000),     Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(480, 6000 - 2000),    Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2080, 6000 - 360),    Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3200, 6000 - 1080),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(4800, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2680, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(240, 6000 - 5880),    Monster1.class.getSimpleName() + monsters1.size(), boy));
        for (int index = 1, posX = 320, posY = (6000 - 240); index < 16; index++) {
            if (index < 5) {
                posX = 320 + (100 * index);
            }
            if (index >= 5) {
                posY = 6000 - 240 - (150 * index);
                posX = 320 + 3 * 200;
            }
            if ((index >= 8 && index <= 11)) {
                posX = 320 + (100 * (index - 5));
                posY = 6000 - 2000;
            }
            if ((index >= 12 && index <= 15)) {
                posX = 520 + (100 * (index - 10));
                posY = 6000 - 2300;
            }
            items.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY), items.size()));
            items2.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY)));

            items.put(Rifle.class.getSimpleName(), new Rifle(new Vector2(850, 6000 - 450)));


            items2 = new HashMap<>();
            items2.put(Rifle.class.getSimpleName(),(Objeto) items.get(Rifle.class.getSimpleName()));
            items2.put(Rifle.class.getSimpleName(), jack.getRifle());
            items2.put(Rifle.class.getSimpleName(), girl.getRifle());

            jack = new Jack(new Vector2(300, 6000 - 380f));

            girl = new Girl(new Vector2(4000, 6000 - 700f));

            items.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
            items.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
            items.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));
            items.put(NinjaRope.class.getSimpleName(), new NinjaRope(new Vector2(450, 6000 - 400)));
            items.get("Portal").updateItem();

            items2.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
            items2.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
            items2.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));

            ninjaStar = new NinjaStar(new Vector2(200, 6000 - 400));
            for (int i = 0; i < 5; i++)
                blocks.add(new Block(new Vector2(850 + i * 50, 6000 - 530)));

            for (int i = 0; i < 5; i++)
                blocks.add(new Block(new Vector2(850 + i * 50, 4050)));

            for (int i = 0; i < 28; i++)
                blocks.add(new Block(new Vector2(3330 + (i * 50), 4810)));

            for (int i = 0; i < 27; i++)
                blocks.add(new Block(new Vector2(4200 + (i * 50), 4050)));


            for (int i = 1; i < 7; i++) {
                stands.add(new Stand(3000 + (200 * i), 6000 - 720));
            }

//        objetos.addAll(blocks);

            rope = new Rope(new Vector2(300, 26000 - 400 + (NUM_ROPES * Rope.HEIGHT)), true);
            for (int i = 0; i < NUM_ROPES; i++){
                ropes.add(new Rope(new Vector2(300f, 6000 - 400 + (i * Rope.HEIGHT)), false));
                if (i == 0) {
                    rope.joint(ropes.get(i).getBodyA());
                    continue;
                }
                ropes.get(i).joint(ropes.get(i-1).getBodyA());
            }
            objetos.addAll(items2.values());
            objetos.add(boy);
            objetos.add(jack);
            objetos.add(girl);
            objetos.addAll(stands);
            objetos.addAll(monsters1.values());
        }
    }
    @Override
    public void update(){
        super.update();
        ninjaRope.update();
//        for (Rope rope : ropes)
//            rope.update();
//        rope.update();
        items.get("Rifle").update();
//        ninjaRope.update(0f);
        for (Fans fan : fans)
            fan.update2(boy.getBody(), Boy.BOX_WIDTH);
    }
}
