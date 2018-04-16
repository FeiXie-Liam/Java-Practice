package com.tw.command;

import com.tw.Console;
import com.tw.domain.Achievement;
import com.tw.service.AchievementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class GenerateStudentReportCommand implements Command {
    private AchievementService achievementService;

    public GenerateStudentReportCommand() {
        this.achievementService = new AchievementService();
    }

    @Override
    public void invoke() {
        Console.println("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
        String[] studentIds = null;
        do {
            String input = Console.getInput();
            try {
                studentIds = parseInput(input);
            } catch (Exception e) {
                Console.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
            }
        } while (studentIds == null);

        AchievementReport report = generateReport(studentIds);

        if (report != null) {
            formatOutput(report);
        }
    }

    private AchievementReport generateReport(String[] studentIds) {
        Map<String, List<Achievement>> groupedAchievements = achievementService.getAchievementsGroupedByStudentId();
        if (groupedAchievements.size() == 0) {
            return null;
        }
        AchievementReport report = new AchievementReport();

        Arrays.stream(studentIds).forEach(e -> {
            ReportItem reportItem = new ReportItem();
            List<Achievement> achievements = groupedAchievements.get(e);
            reportItem.name = achievements.get(0).getStudent().getName();
            reportItem.sumScore = achievements.stream().mapToDouble(Achievement::getScore).sum();
            reportItem.averageScore = reportItem.sumScore / achievements.size();
            reportItem.achievements = achievements;
            report.reportItems.add(reportItem);
        });

        report.averageScore = report.reportItems.stream().mapToDouble(e -> e.averageScore).sum();
        Double[] sortedSumScore = report.reportItems.stream().map(e -> e.sumScore).sorted().toArray(Double[]::new);
        if (sortedSumScore.length % 2 == 0) {
            report.medianScore = (sortedSumScore[sortedSumScore.length / 2 - 1] + sortedSumScore[sortedSumScore.length / 2]) / 2;
        } else {
            report.medianScore = sortedSumScore[sortedSumScore.length / 2];
        }
        return report;
    }

    private void formatOutput(AchievementReport report) {
        Console.println("成绩单");
        Console.println("姓名|数学|语文|英语|编程|平均分|总分");
        Console.println("========================");
        final String[] mathScore = new String[1];
        final String[] chineseScore = new String[1];
        final String[] englishScore = new String[1];
        final String[] programScore = new String[1];
        report.reportItems.forEach(e -> {
            for (Achievement achievement : e.achievements) {
                switch (achievement.getCourse()) {
                    case Math:
                        mathScore[0] = String.valueOf(achievement.getScore());
                        break;
                    case Coding:
                        programScore[0] = String.valueOf(achievement.getScore());
                        break;
                    case Chinese:
                        chineseScore[0] = String.valueOf(achievement.getScore());
                        break;
                    case English:
                        englishScore[0] = String.valueOf(achievement.getScore());
                        break;
                }
            }
            Console.println(format("%s|%s|%s|%s|%s|%s|%s", e.name, mathScore[0], chineseScore[0], englishScore[0], programScore[0], e.averageScore, e.sumScore));
        });
        Console.println(format("全班总分平均数：%s", report.averageScore));
        Console.println(format("全班总分中位数：%s", report.medianScore));
        Console.println("========================");
    }

    private String[] parseInput(String input) {
        String[] studentIds = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);
        Arrays.stream(studentIds).forEach(
                e -> {
                    if (!Pattern.matches("^[A-Za-z0-9]+$", e)) {
                        throw new IllegalArgumentException("input is illegal");
                    }
                }
        );
        if (studentIds.length == 0) {
            throw new IllegalArgumentException("input is illegal");
        }
        return studentIds;
    }

    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    class AchievementReport {
        double averageScore;
        double medianScore;
        List<ReportItem> reportItems = new ArrayList<>(

        );
    }

    class ReportItem {
        String name;
        List<Achievement> achievements;
        double averageScore;
        double sumScore;
    }
}
