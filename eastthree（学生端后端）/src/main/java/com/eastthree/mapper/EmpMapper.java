//package com.itheima.mapper;
//
//import com.itheima.pojo.Emp;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
//@Mapper
//public interface EmpMapper {
//    //获取总记录数,表里总的有几行数据
//    @Select("select count(*) from emp")
//    public Long count();
//    //获取当前页的结果列表
//    @Select("select * from emp limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);
//    //获取当前页面的结果列表
////    @Select("select * from emp")
////    public List<Emp> page(Integer start,Integer pageSize);
////    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);
//    //根据id批量删除
//    public void deleteByIds(List<Integer> ids);
//    //添加新职工
//    public void addNewEmp(Emp emp);
//    public void modifyEmp(Emp emp);
//    @Select("select id,username,password,name,gender,image,job,entrydate,dept_id,create_time,update_time " +
//            "from emp " +
//            "where username=#{username} and password=#{password}")
//    Emp getByUsernameAndPassword(Emp emp);
//}
