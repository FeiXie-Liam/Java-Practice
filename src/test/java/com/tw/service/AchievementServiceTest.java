package com.tw.service;

import com.tw.domain.Achievement;
import com.tw.domain.Course;
import com.tw.domain.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AchievementServiceTest {

    private AchievementService achievementService;

    @Before
    public void setUp() throws Exception {
        achievementService = new AchievementService();
    }

    @Test
    public void should_addAchievementSuccessfully_when_inputValidAchievementInfo() {
        Achievement achievement = new Achievement(new Student("王培", "1"),
                Course.Chinese, 100);
        achievementService.addAchievement(achievement);
        assertThat(achievementService.getAchievements().get(0).getStudent().getStudentId(), is("1"));
    }

    @Test
    public void should_returnCorrespondingAchievements_when_inputSingleStudentId() {
        String studentId = "1";
        initAchievements();
        List<Achievement> result = achievementService.getAchievementsByStudentId(studentId);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getStudent().getName(), is("王培"));
    }

    private void initAchievements() {
        Achievement achievement1 = new Achievement(new Student("王培", "1"),
                Course.Chinese, 80);
        Achievement achievement2 = new Achievement(new Student("王培", "1"),
                Course.Math, 100);
        Achievement achievement3 = new Achievement(new Student("Test1", "2"),
                Course.Chinese, 100);
        achievementService.addAchievement(achievement1);
        achievementService.addAchievement(achievement2);
        achievementService.addAchievement(achievement3);
    }
}