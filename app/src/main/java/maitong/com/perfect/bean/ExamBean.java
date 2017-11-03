package maitong.com.perfect.bean;

import java.util.List;

/**
 * Created by Bruce on 2017-10-11.
 */

public class ExamBean {
    private String exam;
    private List<ExamlistBean> exmaList;

    public String getExmaType() {
        return exam;
    }

    public void setExmaType(String exmaType) {
        this.exam = exmaType;
    }

    public List<ExamlistBean> getExmaList() {
        return exmaList;
    }

    public void setExmaList(List<ExamlistBean> exmaList) {
        this.exmaList = exmaList;
    }

}
