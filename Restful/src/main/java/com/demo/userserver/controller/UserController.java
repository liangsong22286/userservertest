package com.demo.userserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.commonutil.controller.CommonController;
import com.demo.commonutil.dto.ObjectRestResponse;
import com.demo.commonutil.dto.TableResultResponse;
import com.demo.commonutil.security.IgnoreUserToken;
import com.demo.userserver.bo.UserBo;
import com.demo.userserver.bo.UserMailBo;
import com.demo.userserver.entity.User;
import com.demo.userserver.entity.UserMail;
import com.demo.userserver.service.UserMailService;
import com.demo.userserver.service.UserService;
import com.demo.userserver.vo.UserInfo;
import com.github.pagehelper.PageHelper;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 */
@RestControllerAdvice
@RequestMapping("v1")
public class UserController extends CommonController<UserService,User>{
    @Autowired
    private UserService userService;
    @Autowired
    private UserMailService userMailService;
    
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @IgnoreUserToken
    @ResponseBody
    public ObjectRestResponse<UserBo> add(@RequestBody UserBo tempBo){
    	tempBo.setStatus("1");
    	userService.insert(tempBo);
    	userMailService.sendEmail(tempBo.getId(),tempBo.getEmail());
        return new ObjectRestResponse<UserBo>(1,true,"成功");
    }
    
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<UserBo> get(@PathVariable int id){
    	UserBo entityBo = userService.selectById(id);
        return new ObjectRestResponse<UserBo>(1,true,"成功").data(entityBo);
    }

    @RequestMapping(value = "/user/updateList",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<UserBo> updateList(@RequestBody UserInfo userInfo) throws Exception{
    	List<Long> updateIds = userInfo.getUpdateIds();
    	if(updateIds!=null && updateIds.size()>0) {
        	userService.updateUsers(userInfo.getUserInfo(), userInfo.getUpdateIds());
    	}
        return new ObjectRestResponse<UserBo>(1,true,"成功");
    }
    

    @RequestMapping(value = "/user/softDeleteList",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<UserBo> softDeleteList(@RequestBody List<Long> deleteIds) throws Exception{
    	if(deleteIds!=null && deleteIds.size()>0) {
        	userService.deleteUsers(deleteIds);
    	}
        return new ObjectRestResponse<UserBo>(1,true,"成功");
    }

    @RequestMapping(value = "/user/listMail",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<UserMailBo> listMail(@RequestParam(defaultValue = "10") int limit, 
    		@RequestParam(defaultValue = "1")int page) throws Exception{
        Example example = new Example(UserMail.class);
        int count = userMailService.selectCountByExample(example);
        PageHelper.startPage(page, limit);
        return new TableResultResponse<UserMailBo>(count,userMailService.selectByExample(example));
    }
    
    @RequestMapping(value = "/user/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<UserBo> page(@RequestParam(defaultValue = "10") int limit, 
    		@RequestParam(defaultValue = "1")int page, @RequestParam(name="name",required=false) String name){
        Example example = new Example(User.class);
        if(StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
            example.createCriteria().andLike("username", "%" + name + "%");
        }
        
        //只查询没有删除的
        example.createCriteria().andEqualTo("status", "1");
        int count = userService.selectCountByExample(example);
        PageHelper.startPage(page, limit);
        return new TableResultResponse<UserBo>(count,userService.selectByExample(example));
    }
    
    
}
