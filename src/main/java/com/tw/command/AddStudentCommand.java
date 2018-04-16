package com.tw.command;


import com.tw.*;
import com.tw.domain.Achievement;
import com.tw.domain.Course;
import com.tw.domain.Student;
import com.tw.service.AchievementService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddStudentCommand implements Command {
    private AchievementService achievementService;
    private Map<String, Course> courseMap = new HashMap<>();

    public AddStudentCommand() {
        this.achievementService = new AchievementService();
        Arrays.stream(Course.values()).forEach(e -> courseMap.put(e.getDesc(), e));
    }

    public void invoke() {
        Console.println("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：");
        String input = Console.getInput();
        Achievement achievement = null;
        for (; ; ) {
            try {
                achievement = ParseAchievement(input);
            } catch (Exception e) {
                Console.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
                input = Console.getInput();
            }
            if (achievement != null) {
                break;
            }
        }
        achievementService.addAchievement(achievement);
    }

    private Achievement ParseAchievement(String input) throws Exception {
        String[] achievementInfo = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);
        if (achievementInfo.length != 3) {
            throw new IllegalArgumentException("input is not illegal");
        }
        String[] courseInfo = new String[0];
        try {
            courseInfo = Arrays.stream(achievementInfo[2].split(":")).map(String::trim).toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (courseInfo.length != 2) {
            throw new IllegalArgumentException("input is not illegal");
        }
        return new Achievement(new Student(achievementInfo[0], achievementInfo[1]),
                courseMap.get(courseInfo[0]), Double.parseDouble(courseInfo[1]));
    }

    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }
}
