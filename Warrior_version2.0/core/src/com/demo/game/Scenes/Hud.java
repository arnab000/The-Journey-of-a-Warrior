package com.demo.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demo.game.Demo;

import java.util.*;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private  Integer WorldTimer;
    private float timeCount;
    private  static Integer score;
    private static Label countdownLabel;
    private  static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    public Hud(SpriteBatch sb){
        WorldTimer =300;
        timeCount = 0;
        score=4000;
        viewport =new FillViewport(Demo.V_Width,Demo.V_Height,new OrthographicCamera());
        stage=new Stage(viewport,sb);
        Table table= new Table();
        table.top();
        table.setFillParent(true);
        countdownLabel= new Label(String.format("%03d",WorldTimer),new Label.LabelStyle(new BitmapFont(), Color.GOLDENROD));
        scoreLabel=new Label(String.format("%04d",score),new Label.LabelStyle(new BitmapFont(), Color.RED));
        timeLabel=new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        levelLabel=new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        worldLabel=new Label("WORLD",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel=  new Label("Warrior",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);

    }
    public  void update(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            WorldTimer--;
            countdownLabel.setText(String.format("%03d",WorldTimer));
            timeCount=0;
        }
    }

    public int getTime(){
        return WorldTimer;
    }



    public Viewport getViewport() {
        return viewport;
    }

    public static void addScore(int value){
        scoreLabel.setText(String.format("%06d",score));
        score+=value;
    }

    public int getScore(){
        return score;
    }

    @java.lang.Override
    public void dispose() {
        stage.dispose();
    }
}