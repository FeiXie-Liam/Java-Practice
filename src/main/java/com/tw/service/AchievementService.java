package com.tw.service;

import com.tw.domain.Achievement;
import com.tw.domain.AchievementReportDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class AchievementService {
    private static List<Achievement> achievements = new ArrayList<>();
    ;

    public AchievementService() {

    }

    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }


    public List<Achievement> getAchievementsByStudentId(String studentId) {
        return achievements.stream().filter(e -> e.getStudent().getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public Map<String, List<Achievement>> getAchievementsGroupedByStudentId() {
        return achievements.stream()
                .collect(groupingBy(e -> e.getStudent().getStudentId()));
    }

    List<AchievementReportDTO> getAchievementsReportsByStudentsId(String... studentsId) {
        List<AchievementReportDTO> results = new ArrayList<>();

        Map<String, List<Achievement>> groupedAchievements = achievements.stream()
                .collect(groupingBy(e -> e.getStudent().getStudentId()));

        Arrays.stream(studentsId).forEach(e -> {
            AchievementReportDTO achievementReportDTO = new AchievementReportDTO();
            List<Achievement> achievements = groupedAchievements.get(e);
            achievements.stream().forEach(a -> {
                achievementReportDTO.setStudentName(a.getStudent().getName());
                switch (a.getCourse()) {
                    case Math:
                        achievementReportDTO.setMathGrade(a.getScore());
                        break;
                }
            });
            achievementReportDTO.setAverageScore(achievements.stream().mapToDouble(Achievement::getScore).sum() / achievements.size());

        });
        return results;
    }

}