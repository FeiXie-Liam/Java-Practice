package com.tw;

import java.sql.Struct;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Library {
    private boolean isExit = false;
    private Set<Student> students;
    private Scanner sc;

    public Library() {
        sc = new Scanner(System.in);
        students = new HashSet<>();
    }

    public void mainEntry() {
        while (true) {
            System.out.println("1. 添加学生\n2. 生成成绩单\n3.退出\n请输入你的选择（1~3）：");
            int choose = sc.nextInt();
            sc.nextLine();
            switch (choose) {
                case 1:
                    System.out.println("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：");
                    addStudent();
                    break;
                case 2:
                    System.out.println("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
                    generateTranscript();
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
                    break;
            }
            if (isExit)
                break;
        }
    }

    private void generateTranscript() {
        String msg = sc.nextLine();
        String[] splitedMsg = msg.split(", ");
        List<Student> printStudents = new ArrayList<>();
        for (int i = 0; i < splitedMsg.length; i++) {
            if (isInteger(splitedMsg[i])) {
                for (Student student : students) {
                    if (student.getId() == Integer.valueOf(splitedMsg[i])) {
                        printStudents.add(student);
                    }
                }
            } else {
                System.out.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
                generateTranscript();
                return;
            }
        }
        printTranscript(printStudents);
    }

    private void printTranscript(List<Student> students) {
        List<Double> average = students
                .stream()
                .map(student -> student.getSubjects().values().stream().mapToInt(x -> x).average().getAsDouble())
                .collect(Collectors.toList());
        List<Integer> totals = students
                .stream()
                .map(student -> student.getSubjects().values().stream().mapToInt(x -> x).sum())
                .collect(Collectors.toList());

        String subjectsName = students
                .get(0)
                .getSubjects()
                .keySet()
                .stream()
                .collect(Collectors.joining("|"));

        System.out.println("成绩单\n姓名|" + subjectsName + "|平均分|总分\n========================");
        for (int i = 0; i < students.size(); ++i) {
            String scores = students
                    .get(i)
                    .getSubjects()
                    .values()
                    .stream()
                    .map(x -> x.toString())
                    .collect(Collectors.joining("|"));
            System.out.println(students.get(i).getName() + "|" + scores + "|" + average.get(i) + "|" + totals.get(i));
        }

        System.out.println("========================\n全班总分平均数：" +
                totals.stream().mapToInt(x -> x).average().getAsDouble() +
                "\n全班总分中位数：" + getMedian(totals));
    }

    private int getMedian(List<Integer> totals) {
        int mid = totals.size() / 2;
        if (totals.size() % 2 == 0) {
            return (totals.get(mid - 1) + totals.get((mid))) / 2;
        } else
            return totals.get(mid);
    }

    public void addStudent() {
        String msg = sc.nextLine();

        Student student = new Student();
        boolean succeed = student.addMsg(msg);
        if (succeed) {
            students.add(student);
            System.out.println("学生" + student.getName() + "的成绩被添加");
        } else {
            System.out.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
            addStudent();
        }
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
