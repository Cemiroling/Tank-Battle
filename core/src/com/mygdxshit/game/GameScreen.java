package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdxshit.game.Client.ThreadOfReceive;
import com.mygdxshit.game.Client.ThreadOfSend;
import com.mygdxshit.game.Client.ThreadShootReceive;
import com.mygdxshit.game.Client.ThreadShootSend;

public class GameScreen implements Screen {

    private final TankBattle game;
    private Skin skin;
    private TextureAtlas atlas;
    private Tank tank;
    private Tank enemyTank;
    private Controller controller;
    private Map map;
    private int counter;
    private ThreadOfReceive threadOfReceive;
    private ThreadOfSend threadOfSend;
    private ThreadShootSend threadShootSend;
    private ThreadShootReceive threadShootReceive;

    public GameScreen(final TankBattle game) {

        threadOfReceive = new ThreadOfReceive();
        threadOfReceive.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadOfSend = new ThreadOfSend();
        threadOfSend.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadShootReceive = new ThreadShootReceive();
        threadShootReceive.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadShootSend = new ThreadShootSend();
        threadShootSend.start();

        this.game = game;
        atlas = new TextureAtlas("texture.pack");
        skin = new Skin(atlas);
        controller = new Controller(skin);
        tank = new Tank((Gdx.graphics.getWidth() / 2 - 30), Gdx.graphics.getHeight() / 10, 0);
        enemyTank = new Tank((Gdx.graphics.getWidth() / 2 - 30), Gdx.graphics.getHeight() / 2, 2);
        map = new Map();
        initButtons();

        game.batch = new SpriteBatch();
    }

    public void initButtons() {
        counter = 5;
        controller.atack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!tank.isShooted() && counter < 0) {
                    threadShootSend.setIsShoot(true);
                    tank.bullets.Shoot(tank);
                    counter = 5;
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tank.bullets.setShootedState(false);
            }
        });

        controller.moveUp.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("0");
                tank.SetDirection(0);
                tank.SetSpeed(200);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("4");
                tank.SetSpeed(0);
            }
        });

        controller.moveRight.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("1");
                tank.SetDirection(1);
                tank.SetSpeed(200);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("4");
                tank.SetSpeed(0);
            }
        });

        controller.moveLeft.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("3");
                tank.SetDirection(3);
                tank.SetSpeed(200);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("4");
                tank.SetSpeed(0);
            }
        });

        controller.moveDown.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("2");
                tank.SetDirection(2);
                tank.SetSpeed(200);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threadOfSend.setMessage("4");
                tank.SetSpeed(0);
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int x, int y) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void render(float delta) {
        threadOfSend.setMull((String.valueOf((int) (tank.getTank().getX() - (Gdx.graphics.getWidth() - 96 * 20) / 2)) +
                ',' + String.valueOf((int) (tank.getTank().getY())) + ',' + String.valueOf(tank.getLives()) +
                ',' + String.valueOf(tank.getScore())) + ',' + tank.getDirection());
        Gdx.gl.glClearColor(0, 0.15f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        counter--;
        game.batch.begin();

        map.show(game.batch);
        enemyTank.showCraters(game.batch);
        tank.showCraters(game.batch);

        if (enemyTank.isShooted()) {
            map.tankToMap(enemyTank, 0);
            enemyTank.explode(game.batch);
        } else {
            map.tankToMap(enemyTank, -1);
            enemyTank.show(game.batch);
            enemyTank.ride();
        }
        if (tank.isShooted()) {
            map.tankToMap(tank, 0);
            tank.explode(game.batch);
            threadOfSend.setMessage("4");
        } else {
            map.tankToMap(tank, -1);
            tank.show(game.batch);
        }

        if (threadShootReceive.getMessage().equals("1")) {
            enemyTank.bullets.Shoot(enemyTank);
            threadShootReceive.setMessage("0");
        }

        tank.bullets.show(game.batch);
        enemyTank.bullets.show(game.batch);

        enemyTank.bullets.showFlash((int) enemyTank.getTank().getX() - 20, (int) enemyTank.getTank().getY() + 20, enemyTank.getDirection(), game.batch);
        tank.bullets.showFlash((int) tank.getTank().getX() - 20, (int) tank.getTank().getY() + 20, tank.getDirection(), game.batch);
        if (tank.bullets.isCollision()) {
            tank.bullets.collision(game.batch);
        }
        if (enemyTank.bullets.isCollision()) {
            enemyTank.bullets.collision(game.batch);
        }

        tank.showLives(game.batch, true, enemyTank.getScore());
        enemyTank.showLives(game.batch, false, tank.getScore());
        enemyTank.readMessage(threadOfReceive.getMessage());

        game.batch.end();

        controller.drawStage();
        tank.bullets.setShootedState(false);
        enemyTank.bullets.setShootedState(false);

        tank.ride();

        enemyTank.checkMap(map.getMap());
        tank.checkMap(map.getMap());
        tank.bullets.check(map.getMap(), enemyTank);
        enemyTank.bullets.check(map.getMap(), tank);
    }

    @Override
    public void dispose() {
        skin.dispose();
        atlas.dispose();
        game.dispose();
    }
}