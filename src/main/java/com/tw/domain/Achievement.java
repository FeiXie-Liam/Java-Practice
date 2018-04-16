package com.tw.domain;

public class Achievement {
    private Student student;
    private Course course;
    private double score;

    public Achievement(Student student, Course course, double score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double
    getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "student=" + student +
                ", course='" + course + '\'' +
                ", score=" + score +
                '}';
    }
}
