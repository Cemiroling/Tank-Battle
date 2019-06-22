package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Controller {
    public ImageButton moveLeft;
    public ImageButton moveRight;
    public ImageButton moveUp;
    public ImageButton moveDown;
    public ImageButton atack;
    private Stage stage;
    private int screenX = Gdx.graphics.getWidth();
    private int screenY = Gdx.graphics.getHeight();

    public Controller(Skin skin) {
        stage = new Stage(new ScreenViewport());
        Texture putin = new Texture(Gdx.files.internal("shoot.png"));
        atack = new ImageButton(new TextureRegionDrawable(new TextureRegion(putin)));
        moveUp = new ImageButton(skin.getDrawable("UpUnpressed"), skin.getDrawable("UpPressed"));
        moveRight = new ImageButton(skin.getDrawable("RightUnpressed"), skin.getDrawable("RightPressed"));
        moveLeft = new ImageButton(skin.getDrawable("LeftUnpressed"), skin.getDrawable("LeftPressed"));
        moveDown = new ImageButton(skin.getDrawable("DownUnpressed"), skin.getDrawable("DownPressed"));
        setSizes();
        setPositions();
        setStage(stage);
    }

    private void setSizes() {
        atack.setSize(screenY / 3, screenY / 3);
        moveUp.setSize((screenY / 6), screenY / 6);
        moveRight.setSize((screenY / 6), screenY / 6);
        moveLeft.setSize((screenY / 6), screenY / 6);
        moveDown.setSize((screenY / 6), screenY / 6);
    }

    private void setPositions() {
        atack.setPosition(screenX - screenY / 3, 0);
        moveUp.setPosition(screenY / 6, screenY / 3);
        moveRight.setPosition(screenY / 3, screenY / 6);
        moveLeft.setPosition(0, screenY / 6);
        moveDown.setPosition(screenY / 6, 0);
    }


    private void setStage(Stage stage) {
        stage.addActor(moveUp);
        stage.addActor(moveRight);
        stage.addActor(moveDown);
        stage.addActor(moveLeft);
        stage.addActor(atack);
        Gdx.input.setInputProcessor(stage);
    }

    public void drawStage() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
