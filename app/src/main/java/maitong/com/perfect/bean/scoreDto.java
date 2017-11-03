package maitong.com.perfect.bean;

/**
 * Created by Bruce on 2017-10-12.
 */

public class scoreDto {

    /**
     * score : 86
     * courseName : ??
     * classroomAvgScore : 67.6
     * classroomMaxScore : 100
     * classroomMinScore : 10
     */

    private int score;
    private String courseName;
    private double classroomAvgScore;
    private int classroomMaxScore;
    private int classroomMinScore;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getClassroomAvgScore() {
        return classroomAvgScore;
    }

    public void setClassroomAvgScore(double classroomAvgScore) {
        this.classroomAvgScore = classroomAvgScore;
    }

    public int getClassroomMaxScore() {
        return classroomMaxScore;
    }

    public void setClassroomMaxScore(int classroomMaxScore) {
        this.classroomMaxScore = classroomMaxScore;
    }

    public int getClassroomMinScore() {
        return classroomMinScore;
    }

    public void setClassroomMinScore(int classroomMinScore) {
        this.classroomMinScore = classroomMinScore;
    }
}
