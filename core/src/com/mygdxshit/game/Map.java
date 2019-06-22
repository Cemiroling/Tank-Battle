package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private Sprite grass;
    private Sprite wall;
    private Sprite stoneWall;
    private int[][] map = new int[52][96];
    private int correction;

    Map() {
        LoadMap();
        Texture WallImage = new Texture(Gdx.files.internal("stena.png"));
        Texture StoneWallImage = new Texture(Gdx.files.internal("stonewall.png"));
        Texture GrassImage = new Texture(Gdx.files.internal("grass.png"));
        grass = new Sprite(GrassImage);
        wall = new Sprite(WallImage);
        stoneWall = new Sprite(StoneWallImage);
        correction = (Gdx.graphics.getWidth() - 20 * 96) / 2;
    }

    private void LoadMap() {
        FileHandle handle = Gdx.files.internal("map.txt");
        String ms = handle.readString();
        ms = ms.replaceAll("\\n", "");
        ms = ms.replaceAll("\\r", "");
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 96; j++) {
                switch (ms.charAt(i * 96 + j)) {
                    case '0':
                        map[51 - i][j] = 0;
                        break;
                    case '1':
                        map[51 - i][j] = 1;
                        break;
                    case '2':
                        map[51 - i][j] = 2;
                        break;

                }
            }
        }
    }

    public void show(SpriteBatch batch) {
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 96; j++) {
                switch (map[i][j]) {
                    case 0:
                        grass.setPosition((j * 20 + correction), (i * 20));
                        grass.draw(batch);
                        break;
                    case 1:
                        wall.setPosition((j * 20 + correction), (i * 20));
                        wall.draw(batch);
                        break;
                    case 2:
                        stoneWall.setPosition((j * 20 + correction), (i * 20));
                        stoneWall.draw(batch);
                        break;
                    case -1:
                        grass.setPosition((j * 20 + correction), (i * 20));
                        grass.draw(batch);
                        break;
                }
            }
        }
        updateHitboxes();
    }

    public void tankToMap(Tank tank, int state) {
        switch (tank.getDirection()) {
            case 0:
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (map[(int) (tank.getTank().getY() + i * 20) / 20][(int) (tank.getTank().getX() - j * 20 + 60 - correction) / 20] == 0)
                            map[(int) (tank.getTank().getY() + i * 20) / 20][(int) (tank.getTank().getX() - j * 20 + 60 - correction) / 20] = state;
                        else {
                            tank.movingBack();
                            return;
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (map[(int) (tank.getTank().getY() + 20 + i * 20) / 20][(int) (tank.getTank().getX() - 20 + j * 20 - correction) / 20] == 0)
                            map[(int) (tank.getTank().getY() + 20 + i * 20) / 20][(int) (tank.getTank().getX() - 20 + j * 20 - correction) / 20] = state;
                        else {
                            tank.movingBack();
                            return;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (map[(int) (tank.getTank().getY() + i * 20) / 20][(int) (tank.getTank().getX() - j * 20 + 60 - correction) / 20] == 0)
                            map[(int) (tank.getTank().getY() + i * 20) / 20][(int) (tank.getTank().getX() - j * 20 + 60 - correction) / 20] = state;
                        else {
                            tank.movingBack();
                            return;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (map[(int) (tank.getTank().getY() + 20 + i * 20) / 20][(int) (tank.getTank().getX() - 20 + j * 20 - correction) / 20] == 0)
                            map[(int) (tank.getTank().getY() + 20 + i * 20) / 20][(int) (tank.getTank().getX() - 20 + j * 20 - correction) / 20] = state;
                        else {
                            tank.movingBack();
                            return;
                        }
                    }
                }
                break;
        }
    }

    public void updateHitboxes() {
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 96; j++) {
                switch (map[i][j]) {
                    case -1:
                        map[i][j] = 0;
                        break;
                }
            }
        }
    }

    public int[][] getMap() {
        return map;
    }
}