package com.tw;

import javafx.scene.media.SubtitleTrack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Student {
    private String name;
    private int id;
    private Map<String, Integer> subjects = null;

    public Student() {
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean addMsg(String msg) {
        String[] splitedMsg = msg.split(", ");
        String name;
        int id;
        Map<String, Integer> subjects = new HashMap<>();
        if (splitedMsg.length <= 2) {
            return false;
        } else {
            name = splitedMsg[0];
            if (!isInteger(splitedMsg[1]))
                return false;
            id = Integer.valueOf(splitedMsg[1]);
            for (int i = 2; i < splitedMsg.length; ++i) {
                String[] subjectMsg = splitedMsg[i].split(": ");
                if (subjectMsg.length != 2)
                    return false;
                else {
                    if (!isInteger(subjectMsg[1]))
                        return false;
                    subjects.put(subjectMsg[0], Integer.valueOf(subjectMsg[1]));
                }
            }
        }
        this.subjects = subjects;
        this.id = id;
        this.name = name;
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getSubjects() {
        return subjects;
    }
}
