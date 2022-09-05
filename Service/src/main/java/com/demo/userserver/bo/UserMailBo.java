package com.demo.userserver.bo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "UserMailBo", description = "用户BO")
public class UserMailBo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容")
    private String content;

    /**
     * 发送状态
     */
    @ApiModelProperty(value = "发送状态")
    private String state;
    
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

}