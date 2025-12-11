package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.images.Images.polygons_bodies;
import static com.mygdx.game.images.Images.tile;

public class Tile {

    TmxMapLoader tmxMapLoader;
    public TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;

    public ArrayList<Body> bodies_of_thorns = new ArrayList<>();
    public ArrayList<Body> bodies_of_rects = new ArrayList<>();

    @Getter @Setter
    private String name = "";

    public Tile(String name) {
        this.name = name;
        tmxMapLoader = new TmxMapLoader();
        tiledMap = tmxMapLoader.load(name);
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public MapObjects loadMapObjects(String layerName) {
        return tiledMap.getLayers().get(layerName).getObjects();
    }

    public MapObjects loadMapObjects(int index) {
        return tiledMap.getLayers().get(index).getObjects();
    }

    public void createBodies(MapObjects mapObjects, World world, boolean isSensor, String userData) {
//        int i = 0;
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                BodyDef bodyDef = new BodyDef();
                bodyDef.active = true;
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(new Vector2(0f,0f));
//                if  (++i >= 3)
//                    bodyDef.angle = (float) Math.toRadians(330f);

                Body body = world.createBody(bodyDef);
                PolygonShape shape = createPolygonShape((RectangleMapObject) mapObject);
                Fixture f = body.createFixture(shape, 1f);
                f.setFriction(0f);
                f.setSensor(isSensor);
                body.setUserData(userData);
                if (userData.equals("Thorns") || userData.equals("Thorns_Colliders"))
                    bodies_of_thorns.add(body);
                if (userData.equals("Rects"))
                    bodies_of_rects.add(body);

                shape.dispose();
            } else {
                if (mapObject instanceof PolygonMapObject) {
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(new Vector2(0f, 0f));
                    bodyDef.active = true;
                    PolygonShape ps = new PolygonShape();
                    PolygonMapObject polygonMapObject = null;
                    polygonMapObject = (PolygonMapObject) mapObject;
                    float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();

                    float[] box2Dvertices = new float[vertices.length];
                    for (int index = 0; index < vertices.length; index++) {
                        box2Dvertices[index] = vertices[index];
                    }

                    ps.set(box2Dvertices);
                    Body body = world.createBody(bodyDef);
                    body.setActive(true);
                    Fixture f = body.createFixture(ps, 1f);
                    f.setFriction(0f);
                    f.setSensor(isSensor);
                    body.setUserData(userData);
                    if (userData.equals("Polygons"))
                        bodies_of_rects.add(body);
                    ps.dispose();
                }
            }
        }
    }

    private PolygonShape createPolygonShape(RectangleMapObject mapObject) {

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(mapObject.getRectangle().getWidth() / 2f, mapObject.getRectangle().getHeight() / 2f,
                new Vector2(mapObject.getRectangle().getX() + mapObject.getRectangle().getWidth() / 2f,
                        mapObject.getRectangle().getY() + mapObject.getRectangle().getHeight() / 2f), 0f);
        return polygonShape;
    }

    public void invisible(String layerName){
        TiledMap map = new TmxMapLoader().load(name);
        MapLayer layer = map.getLayers().get(layerName);
        for (MapObject object : layer.getObjects()) { // Verificar se o objeto é do tipo Tile
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
                // Verificar a propriedade 'visible'
                boolean isVisible = tileObject.getProperties().get("visible", Boolean.class); // Ajustar a visibilidade com base na propriedade
                if (!isVisible) {
                    tileObject.setVisible(false);
                }
            }
        }
    }


    public TextureRegion getTextureRegionOfCell(int layerIndex, int tileX, int tileY){
        MapLayer layer = tiledMap.getLayers().get(layerIndex);

//        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            TiledMapTileLayer.Cell cell;
            cell = tileLayer.getCell(tileX, tileY);
            if (cell != null && cell.getTile() != null) {
                return cell.getTile().getTextureRegion();
            }
//        }
        return null;
    }

    public TextureRegion getTextureRegionOfCell(String layerName, int tileX, int tileY){
        MapLayer layer = tiledMap.getLayers().get(layerName);

        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
            TiledMapTileLayer.Cell cell;
            cell = tileLayer.getCell(tileX, tileY);
            if (cell != null && cell.getTile() != null) {
                return cell.getTile().getTextureRegion();
            }
        }
        return null;
    }

}
