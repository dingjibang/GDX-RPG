package com.rpsg.rpg.core;

import com.rpsg.rpg.object.game.ConsoleFromType;
import com.rpsg.rpg.view.ConsoleView;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Console {

    public static ConsoleView view;

    private static List<ConsoleLog> logs = new ArrayList<>();

    public static void init(){
        view = Views.addView(ConsoleView.class);
        logs.clear();
        send(null, "GDX-RPG Console System Link start!");
    }

    public static void post(){
        logs.forEach(view::send);
        logs.clear();
    }



    public static void send(ConsoleFromType from, String text){
        send(from, text, "ffffff", false);
    }

    public static void send(ConsoleFromType from, String text, String color, boolean execute){
        ConsoleLog log = new ConsoleLog();
        log.time = new SimpleDateFormat("MM:dd:ss").format(new Date());
        log.text = text;
        log.execute = execute;
        log.from = from;
        log.color = color;

        logs.add(log);

        if(execute){
            try {
                Binding binding = new Binding();
                binding.setProperty("Console", Console.class);
                binding.setProperty("File", File.class);
                binding.setProperty("Game", Game.class);
                binding.setProperty("Input", Input.class);
                binding.setProperty("Log", Log.class);
                binding.setProperty("Path", Path.class);
                binding.setProperty("Res", Res.class);
                binding.setProperty("Setting", Setting.class);
                binding.setProperty("Sound", Sound.class);
                binding.setProperty("Text", Text.class);
                binding.setProperty("UI", UI.class);
                binding.setProperty("Views", Views.class);

                GroovyShell shell = new GroovyShell(binding);
                Object result = shell.evaluate(text);

                send(ConsoleFromType.Executed, result == null ? "[null]" : result.toString());
            }catch (Exception e){
                send(ConsoleFromType.Executed, e.getMessage(), "ff0000", false);
            }
        }
    }


    public static class ConsoleLog{
        public ConsoleFromType from;
        public String text;
        public String time;
        public boolean execute;
        public String color;
    }


}
