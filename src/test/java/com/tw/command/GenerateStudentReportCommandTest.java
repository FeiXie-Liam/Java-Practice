package com.tw.command;

import com.tw.Console;
import com.tw.domain.Achievement;
import com.tw.domain.Course;
import com.tw.domain.Student;
import com.tw.service.AchievementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Console.class)
public class GenerateStudentReportCommandTest {
    @Mock
    AchievementService achievementService;

    GenerateStudentReportCommand generateStudentReportCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockStatic(Console.class);
        generateStudentReportCommand = new GenerateStudentReportCommand();
        generateStudentReportCommand.setAchievementService(achievementService);
    }

    @Test
    public void should_printInputNotification_when_commandInvoked() {
        when(Console.getInput()).thenReturn("1,2,3");
        generateStudentReportCommand.invoke();

        PowerMockito.verifyStatic(Console.class);
        Console.println("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
    }

    @Test
    public void should_printInputNotIllegalNotification_when_inputIllegal() {
        when(Console.getInput()).thenReturn("1.2.3").thenReturn("1,2,3");

        generateStudentReportCommand.invoke();

        PowerMockito.verifyStatic(Console.class);
        Console.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
    }

    @Test
    public void should_printStudentsGrade_when_inputIleegalAndStudentsAchievementExist() {
        when(Console.getInput()).thenReturn("1,2");
        when(achievementService.getAchievementsGroupedByStudentId()).thenReturn(createGroupedAchievements());

        generateStudentReportCommand.invoke();

        PowerMockito.verifyStatic(Console.class);
        Console.println("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
        PowerMockito.verifyStatic(Console.class);
        Console.println("成绩单");
        PowerMockito.verifyStatic(Console.class);
        Console.println("姓名|数学|语文|英语|编程|平均分|总分");
        PowerMockito.verifyStatic(Console.class, times(2));
        Console.println("========================");
        PowerMockito.verifyStatic(Console.class);
        Console.println("张三|75.0|95.0|80.0|80.0|82.5|330.0");
        PowerMockito.verifyStatic(Console.class);
        Console.println("李四|85.0|80.0|70.0|90.0|81.25|325.0");
        PowerMockito.verifyStatic(Console.class);
        Console.println("全班总分平均数：163.75");
        PowerMockito.verifyStatic(Console.class);
        Console.println("全班总分中位数：327.5");

    }

    private Map<String, List<Achievement>> createGroupedAchievements() {
        Map<String, List<Achievement>> groupedAchievement = new HashMap<>();
        List<Achievement> achievements1 = new ArrayList<>();
        achievements1.add(new Achievement(new Student("张三", "1"), Course.Math, 75));
        achievements1.add(new Achievement(new Student("张三", "1"), Course.Chinese, 95));
        achievements1.add(new Achievement(new Student("张三", "1"), Course.English, 80));
        achievements1.add(new Achievement(new Student("张三", "1"), Course.Coding, 80));

        List<Achievement> achievements2 = new ArrayList<>();
        achievements2.add(new Achievement(new Student("李四", "2"), Course.Math, 85));
        achievements2.add(new Achievement(new Student("李四", "2"), Course.Chinese, 80));
        achievements2.add(new Achievement(new Student("李四", "2"), Course.English, 70));
        achievements2.add(new Achievement(new Student("李四", "2"), Course.Coding, 90));
        groupedAchievement.put("1", achievements1);
        groupedAchievement.put("2", achievements2);
        return groupedAchievement;
    }
}
