package com.alex.blog.common.enums;

/**
 *description:  用户行为枚举
 *author:       alex
 *createDate:   2022/1/26 17:54
 *version:      1.0.0
 */
public enum EBehavior {

    /**
     * 用户行为
     */
    BLOG_TAG("点击标签", "blog_tag"),
    BLOG_SORT("点击博客分类", "blog_sort"),
    BLOG_CONTNET("点击博客", "blog_content"),
    BLOG_PRAISE("点赞", "blog_praise"),
    FRIENDSHIP_LINK("点击友情链接", "friendship_link"),
    BLOG_SEARCH("点击搜索", "blog_search"),
    STUDY_VIDEO("点击学习视频", "study_video"),
    VISIT_PAGE("访问页面", "visit_page"),
    VISIT_CLASSIFY("点击博客分类", "visit_classify"),
    VISIT_SORT("点击归档", "visit_sort"),
    BLOG_AUTHOR("点击作者", "blog_author"),
    PUBLISH_COMMENT("发表评论", "publish_comment"),
    DELETE_COMMENT("删除评论", "delete_comment"),
    REPORT_COMMENT("举报评论", "report_comment"),
    VISIT_TAG("点击博客标签页面", "visit_tag");

    EBehavior(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EBehavior getByCode(String behavior) {
        for (EBehavior i : values()) {
            if (i.getCode().equals(behavior)) {
                return i;
            }
        }
        return null;
    }
}
