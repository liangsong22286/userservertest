package com.demo.userserver.entity;

import java.util.Date;
import javax.persistence.*;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

@Table(name = "user_mail")
public class UserMail {
    /**
     * 主键
     */
    @Id
    @KeySql(sql = "SELECT if(max(id),max(id)+1,1) from user_mail", order = ORDER.BEFORE)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 发送状态
     */
    private String state;

    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private Date crtTime;

    /**
     * 创建人
     */
    @Column(name = "crt_user")
    private String crtUser;

    /**
     * 创建人姓名
     */
    @Column(name = "crt_name")
    private String crtName;

    /**
     * 创建人ip
     */
    @Column(name = "crt_host")
    private String crtHost;

    /**
     * 修改时间
     */
    @Column(name = "upd_time")
    private Date updTime;

    /**
     * 修改人
     */
    @Column(name = "upd_user")
    private String updUser;

    /**
     * 修改人姓名
     */
    @Column(name = "upd_name")
    private String updName;

    /**
     * 修改人ip
     */
    @Column(name = "upd_host")
    private String updHost;

    /**
     * 备注1
     */
    private String attr1;

    /**
     * 备注2
     */
    private String attr2;

    /**
     * 备注3
     */
    private String attr3;

    /**
     * 备注4
     */
    private String attr4;

    /**
     * 备注5
     */
    private String attr5;

    /**
     * 备注6
     */
    private String attr6;

    /**
     * 备注7
     */
    private String attr7;

    /**
     * 备注8
     */
    private String attr8;

    /**
     * 备注9
     */
    private String attr9;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取邮件内容
     *
     * @return content - 邮件内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置邮件内容
     *
     * @param content 邮件内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取发送状态
     *
     * @return state - 发送状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置发送状态
     *
     * @param state 发送状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return crt_time - 创建时间
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * 设置创建时间
     *
     * @param crtTime 创建时间
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * 获取创建人
     *
     * @return crt_user - 创建人
     */
    public String getCrtUser() {
        return crtUser;
    }

    /**
     * 设置创建人
     *
     * @param crtUser 创建人
     */
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    /**
     * 获取创建人姓名
     *
     * @return crt_name - 创建人姓名
     */
    public String getCrtName() {
        return crtName;
    }

    /**
     * 设置创建人姓名
     *
     * @param crtName 创建人姓名
     */
    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    /**
     * 获取创建人ip
     *
     * @return crt_host - 创建人ip
     */
    public String getCrtHost() {
        return crtHost;
    }

    /**
     * 设置创建人ip
     *
     * @param crtHost 创建人ip
     */
    public void setCrtHost(String crtHost) {
        this.crtHost = crtHost;
    }

    /**
     * 获取修改时间
     *
     * @return upd_time - 修改时间
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * 设置修改时间
     *
     * @param updTime 修改时间
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * 获取修改人
     *
     * @return upd_user - 修改人
     */
    public String getUpdUser() {
        return updUser;
    }

    /**
     * 设置修改人
     *
     * @param updUser 修改人
     */
    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    /**
     * 获取修改人姓名
     *
     * @return upd_name - 修改人姓名
     */
    public String getUpdName() {
        return updName;
    }

    /**
     * 设置修改人姓名
     *
     * @param updName 修改人姓名
     */
    public void setUpdName(String updName) {
        this.updName = updName;
    }

    /**
     * 获取修改人ip
     *
     * @return upd_host - 修改人ip
     */
    public String getUpdHost() {
        return updHost;
    }

    /**
     * 设置修改人ip
     *
     * @param updHost 修改人ip
     */
    public void setUpdHost(String updHost) {
        this.updHost = updHost;
    }

    /**
     * 获取备注1
     *
     * @return attr1 - 备注1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * 设置备注1
     *
     * @param attr1 备注1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * 获取备注2
     *
     * @return attr2 - 备注2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * 设置备注2
     *
     * @param attr2 备注2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * 获取备注3
     *
     * @return attr3 - 备注3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * 设置备注3
     *
     * @param attr3 备注3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * 获取备注4
     *
     * @return attr4 - 备注4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * 设置备注4
     *
     * @param attr4 备注4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    /**
     * 获取备注5
     *
     * @return attr5 - 备注5
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * 设置备注5
     *
     * @param attr5 备注5
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * 获取备注6
     *
     * @return attr6 - 备注6
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * 设置备注6
     *
     * @param attr6 备注6
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    /**
     * 获取备注7
     *
     * @return attr7 - 备注7
     */
    public String getAttr7() {
        return attr7;
    }

    /**
     * 设置备注7
     *
     * @param attr7 备注7
     */
    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    /**
     * 获取备注8
     *
     * @return attr8 - 备注8
     */
    public String getAttr8() {
        return attr8;
    }

    /**
     * 设置备注8
     *
     * @param attr8 备注8
     */
    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    /**
     * 获取备注9
     *
     * @return attr9 - 备注9
     */
    public String getAttr9() {
        return attr9;
    }

    /**
     * 设置备注9
     *
     * @param attr9 备注9
     */
    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }
}