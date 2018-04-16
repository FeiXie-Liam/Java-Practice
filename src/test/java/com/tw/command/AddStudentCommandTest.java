package com.tw.command;

import com.tw.domain.Achievement;
import com.tw.Console;
import com.tw.service.AchievementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Console.class)
public class AddStudentCommandTest {

    @Mock
    AchievementService achievementService;
    private AddStudentCommand addStudentCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockStatic(Console.class);
        addStudentCommand = new AddStudentCommand();
    }

    @Test
    public void should_addAddStudentSuccess_when_inputlegalStudentInfo() {
        when(Console.getInput()).thenReturn("王培, 1, 数学:99");
        addStudentCommand.setAchievementService(achievementService);

        addStudentCommand.invoke();

        verify(achievementService, times(1)).addAchievement(any(Achievement.class));
    }

    @Test
    public void should_askInputAgain_when_inputIllegalFormatStudentInfo() {
        when(Console.getInput()).thenReturn("王培, 1. 数学:99").thenReturn("王培, 1, 数学:99");
        addStudentCommand.setAchievementService(achievementService);

        addStudentCommand.invoke();
        verifyStatic(Console.class);
        Console.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
        verify(achievementService, times(1)).addAchievement(any(Achievement.class));
    }

    @Test
    public void should_askInputAgain_when_inputIllegalFormatCourseInfo() {
        when(Console.getInput()).thenReturn("王培, 1, 数学,99").thenReturn("王培, 1, 数学:99");
        addStudentCommand.setAchievementService(achievementService);

        addStudentCommand.invoke();
        verifyStatic(Console.class);
        Console.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
        verify(achievementService, times(1)).addAchievement(any(Achievement.class));
    }

}