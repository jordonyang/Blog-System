package com.yang.www.po;

public class BlogType {
    private Integer typeId;
    private String typeName;
    private Integer ordinal;
    private Integer blogCount;

    public BlogType() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    @Override
    public String toString() {
        return "BlogType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", ordinal=" + ordinal +
                ", blogCount=" + blogCount +
                '}';
    }
}
