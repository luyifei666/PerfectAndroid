package maitong.com.perfect.bean;

/**
 * Created by Bruce on 2017-10-12.
 */

public class GradeBean {
    private String Subjec;//科目
    private String Grade;//分数
    private String Average;//平均分
    private String Highest;//最高分
    private String Minimum;//最低分

    public String getSubjec() {
        return Subjec;
    }

    public void setSubjec(String subjec) {
        Subjec = subjec;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getAverage() {
        return Average;
    }

    public void setAverage(String average) {
        Average = average;
    }

    public String getHighest() {
        return Highest;
    }

    public void setHighest(String highest) {
        Highest = highest;
    }

    public String getMinimum() {
        return Minimum;
    }

    public void setMinimum(String minimum) {
        Minimum = minimum;
    }
}
