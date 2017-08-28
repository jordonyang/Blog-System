package com.yang.www.po;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Blog {

    private Integer blogId;
    private String title;
    private String summary;
    private String content;
    private Date releaseDate;
    private Integer clicks;
    private Integer commentCount;
    private String keyword;
    private BlogType blogType;
    private String releaseDateStr;
    private Integer blogCount;
    private List<String> imageList=new LinkedList<String>();
    private String contentWithoutTags;

    public Blog() {
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BlogType getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogType blogType) {
        this.blogType = blogType;
    }

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getContentWithoutTags() {
        return contentWithoutTags;
    }

    public void setContentWithoutTags(String contentWithoutTags) {
        this.contentWithoutTags = contentWithoutTags;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", releaseDate=" + releaseDate +
                ", clicks=" + clicks +
                ", commentCount=" + commentCount +
                ", keyword='" + keyword + '\'' +
                ", blogType=" + blogType +
                ", releaseDateStr='" + releaseDateStr + '\'' +
                ", blogCount=" + blogCount +
                ", imageList=" + imageList +
                ", contentWithoutTags='" + contentWithoutTags + '\'' +
                '}';
    }
}

