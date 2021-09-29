package com.example.myapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by laihm on 2021/9/29
 */
public class WXArticleBean {

    @SerializedName("data")
    private List<DataBean> data;
    @SerializedName("errorCode")
    private Integer errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        @SerializedName("children")
        private List<?> children;
        @SerializedName("courseId")
        private Integer courseId;
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("order")
        private Integer order;
        @SerializedName("parentChapterId")
        private Integer parentChapterId;
        @SerializedName("userControlSetTop")
        private Boolean userControlSetTop;
        @SerializedName("visible")
        private Integer visible;

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Integer getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(Integer parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public Boolean getUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(Boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public Integer getVisible() {
            return visible;
        }

        public void setVisible(Integer visible) {
            this.visible = visible;
        }
    }
}
