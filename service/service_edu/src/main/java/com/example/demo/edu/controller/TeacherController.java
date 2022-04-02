package com.example.demo.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commonutils.R;
import com.example.demo.edu.entity.Teacher;
import com.example.demo.edu.entity.vo.TeacherQuery;
import com.example.demo.edu.service.TeacherService;
import com.example.demo.servicebase.exception.handler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@Api(tags = "讲师列表")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public R list() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }


    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //分页查询方法
    //current 当前页
    //limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {

        //创建Page对象
        Page<Teacher> page=new Page<>(current,limit);

        try {
            //int i=10/0;//手动异常
        }catch (Exception e){
            //执行自定义异常
            throw new MyException(2000,"执行了自定义异常");
        }

        //调用方法实现分页
        teacherService.page(page,null);
        long total = page.getTotal();//总记录数
        List<Teacher> records = page.getRecords();//数据list集合
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);

       // return R.ok().data("total",total).data("rows",records);

    }
    @PostMapping("pageTeacherCondition/{current}/{limit}")

    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit
                                    , @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<Teacher> pageTeacher =new Page<>(current,limit);

        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        //动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(StringUtils.hasText(name)){
            wrapper.like("name",name);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        if(StringUtils.hasText(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(StringUtils.hasText(end)){
            wrapper.le("gmt_modified",end);
        }

        wrapper.orderByDesc("gmt_create");



        teacherService.page(pageTeacher,wrapper);


        long total=pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return  R.ok().data("total",total).data("rows",records);
    }
    //添加讲师接口
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){

        boolean save = teacherService.save(teacher);
        if(save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }
    //(根据讲师id查询)
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable Long id){
        Teacher byId = teacherService.getById(id);
        return R.ok().data("teacher",byId);

    }
    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if(b){
            return R.ok();
        }
        else {
            return R.error();
        }
    }


}

