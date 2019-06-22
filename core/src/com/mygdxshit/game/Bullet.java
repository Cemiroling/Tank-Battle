package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;

public class Bullet {
    private Polygon bullet;
    private int direction;
    private int speed;

    Bullet() {
    }

    Bullet(float x, float y, int direction) {
        this.direction = direction;
        bullet = new Polygon(new float[]{0, 0, 2, 0, 2, 2, 0, 2});
        bullet.setOrigin(2, 2);
        bullet.setRotation(direction * 90);
        switch (direction) {
            case 0:
                bullet.setPosition(x + 50, y + 80);
                break;
            case 1:
                bullet.setPosition(x + 100, y + 29);
                break;
            case 2:
                bullet.setPosition(x + 50, y);
                break;
            case 3:
                bullet.setPosition(x, y + 29);
                break;
        }
        speed = 1000;
    }

    public void show() {
        switch (direction) {
            case 0:
                bullet.setPosition(bullet.getX(), bullet.getY() + speed * Gdx.graphics.getDeltaTime());
                break;
            case 1:
                bullet.setPosition(bullet.getX() + speed * Gdx.graphics.getDeltaTime(), bullet.getY());
                break;
            case 2:
                bullet.setPosition(bullet.getX(), bullet.getY() - speed * Gdx.graphics.getDeltaTime());
                break;
            case 3:
                bullet.setPosition(bullet.getX() - speed * Gdx.graphics.getDeltaTime(), bullet.getY());
                break;
        }
    }

    public int getX() {
        return (int) bullet.getX();
    }

    public int getY() {
        return (int) bullet.getY();
    }

    public Polygon getBullet() {
        return bullet;
    }

    public int getDirection() {
        return direction;
    }
}
