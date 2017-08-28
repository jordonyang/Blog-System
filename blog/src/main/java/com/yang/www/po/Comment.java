package com.yang.www.po;

import java.util.Date;
public class Comment {

    private Integer commentId;
    private String userIp;
    private String content;
    private Blog blog;
    private Date releaseDate;
    private Integer state;

    public Comment() {
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userIp='" + userIp + '\'' +
                ", content='" + content + '\'' +
                ", blog=" + blog +
                ", releaseDate=" + releaseDate +
                ", state=" + state +
                '}';
    }
}
