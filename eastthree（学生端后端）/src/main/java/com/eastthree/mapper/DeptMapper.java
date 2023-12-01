//package com.itheima.mapper;
//
//import com.itheima.pojo.Dept;
//import org.apache.ibatis.annotations.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
////Mapper相当于dao层的接口,dao层的类不用我们编写
//@Mapper
//public interface DeptMapper {
//    //查询所有部门数据
//    @Results({@Result(column = "create_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")})
//    @Select("select id,name,create_time,update_time from dept")
//    List<Dept> list();
//    @Delete("delete from emp where id=#{id}")
//    void deleteById(Integer id);
//    @Insert("insert into dept (name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
//    void insertNewDept(String name, LocalDateTime createTime,LocalDateTime updateTime);
//    @Results({@Result(column = "create_time",property = "createTime"),
//            @Result(column = "update_time",property = "updateTime")})
//    @Select("select id,name,create_time,update_time from dept where id=#{id}")
//    Dept selectById(Integer id);
//    @Update("UPDATE dept SET name =#{name}  WHERE id = #{id}")
//    void modifyDeptName(Dept dept);
//}
