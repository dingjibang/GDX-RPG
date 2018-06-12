package com.rpsg.rpg.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Console;
import com.rpsg.rpg.core.*;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.object.game.ConsoleFromType;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.ui.widget.Button;
import com.rpsg.rpg.ui.widget.Label;
import script.ui.view.UIView;

import java.util.ArrayList;
import java.util.List;

/**
 * GDX-RPG 控制台（调试）view
 */
public class ConsoleView extends UIView {

    Table out;
    ScrollPane pane;
    TextField field;

    Group console;

    List<String> history = new ArrayList<>();

    int cursor = 0;


    public void create() {

        buuleable(true);

        console = new Group();
        console.setVisible(false);

        $.add(new Button(Res.getDrawable(Path.IMAGE_GLOBAL + "console.png"), Res.getDrawable(Path.IMAGE_GLOBAL + "console_p.png")))
                .size(30, 30)
                .position(Game.width() - 50, Game.height() - 50)
                .to(stage)
                .click(() -> {
                    buuleable(false);
                    console.setVisible(true);
                });

        $.add(UI.base()).color("000000").size(Game.width(), Game.height() - 43).y(43).a(0.7f).to(console);
        $.add(UI.base()).color("333333").size(Game.width(), 43).a(0.85f).to(console);

        out = new Table().left().top();
        out.setWidth(Game.width());

        pane = new ScrollPane(out);
        pane.setSize(Game.width(), Game.height() - 45);
        pane.setPosition(0, 45);

        console.addActor(pane);


        com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle textButtonStyle = UI.buttonNoBorder(new com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle());
        textButtonStyle.font = Res.text.get(20);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.cursor = textFieldStyle.selection = UI.baseDrawable();
        textFieldStyle.focusedFontColor = Color.WHITE;
        textFieldStyle.font = Res.text.get(20);

        field = $.tadd(new TextField("", textFieldStyle)).placeHolder("<type command here>").size(Game.width() - 290, 43).x(5).to(console).get();
        field.setFocusTraversal(false);
        field.setTextFieldListener((that, c) -> {
            if(c == '\n' || c == '\r'){
                Console.send(ConsoleFromType.Console, field.getText(), "ffffff", true);
                history.add(field.getText());
                field.setText("");
            }
        });

        $.add(new TextButton("Enter", textButtonStyle)).size(140, 43).position(Game.width() - 280, 0).click(() -> {
            Console.send(ConsoleFromType.Console, field.getText(), "ffffff", true);
            history.add(field.getText());
            field.setText("");
        }).to(console);

        $.add(new TextButton("Close", textButtonStyle)).size(140, 43).position(Game.width() - 140, 0).to(console).click(() -> {
            console.setVisible(false);
            buuleable(true);
        });

        $.add(UI.base()).color(Color.WHITE).size(Game.width(), 2).position(0, 43).to(console);
        stage.addActor(console);

    }

    private String cursor(int offset){
        if(history.size() == 0)
            return null;

        cursor += offset;

        if(cursor < 0)
            cursor = 0;

        if(cursor > history.size() - 1)
            cursor = history.size() - 1;

        return history.get(cursor);

    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.UP){
            field.setText(cursor(-1));

            int cursor = field.getText().length();
            field.setCursorPosition(cursor < 0 ? 0 : cursor);
        }

        if(keycode == Input.Keys.DOWN) {
            field.setText(cursor(1));

            int cursor = field.getText().length();
            field.setCursorPosition(cursor < 0 ? 0 : cursor);
        }

        if(keycode == Input.Keys.ESCAPE){
            console.setVisible(false);
            buuleable(true);
        }


        return super.keyDown(keycode);
    }

    public void send(Console.ConsoleLog log){
        if(log.text == null)
            return;

        cursor = history.size();

        String command = "";

        if(log.from != null){
            command = "[#aaaaaaff][[" + log.time + "][] [#" + log.from.color() + "]<" + log.from.name() + ">[]";
        }

        if(log.color != null){
            command += "[#" + log.color + "]" + log.text.replaceAll("\\[", "[[") + "[]";
        }else{
            command += log.text;
        }

        Label label = new Label(command, 18).maxWidth(Game.width() - 10).warp(true).markup(true).left();
        out.add(label).left().width(Game.width() - 10).pad(4, 5, 4, 5).row();

        out.layout();
        pane.layout();
        pane.setScrollPercentY(100);

    }

    public void draw() {
        if(!Game.setting.console)
            return;

        stage.act();
        stage.draw();
    }

    public void act() {
        if(!Game.setting.console)
            return;


        if(Views.views.indexOf(this) != 0){
            Views.views.remove(this);
            Views.views.add(0, this);
        }
    }

}
