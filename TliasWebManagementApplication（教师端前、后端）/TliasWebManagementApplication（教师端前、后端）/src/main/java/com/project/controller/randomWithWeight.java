package com.project.controller;

import com.project.pojo.Stu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



public class randomWithWeight{
    public static double[] getStudentWeight(ArrayList<Stu> studentList) {
        double[] weightArr = new double[studentList.size()];
        double totalWeight = 0;
        int index = 0;
        // 计算权重之和
        for (Stu student : studentList)
        {
            totalWeight = totalWeight + student.getWeight();
        }
        // 归一化处理
        for (Stu student : studentList)
        {
            weightArr[index] = student.getWeight()/totalWeight;
            index++;
        }
        //4计算每一个权重占比范围
        for (int i = 1; i < weightArr.length; i++)
        {
            weightArr[i] = weightArr[i] + weightArr[i-1];
        }
        // 防止因浮点数计算误差而产生的错误
        weightArr[weightArr.length-1] = 1;
        return weightArr;
    }
    public static Stu getLuckyStudentName(){
        // 导入数据并计算权重
//		ArrayList<Stu> studentList = loadData(data,0.01);
        ArrayList<Stu> studentList = new ArrayList<Stu>();
        studentList.add(new Stu("zhangsan",0.5));
        studentList.add(new Stu("lisi",0.3));
        studentList.add(new Stu("wangwu",0.4));
        for(int i=1;i<=50;++i) {
            studentList.add(new Stu("student_" + i,0.01));
        }
        // 计算每位同学的占比范围
        double[] weightArr = getStudentWeight(studentList);

        // 随机抽取幸运儿
        Random rander = new Random();
        double rand = rander.nextDouble();
        // 二分查找幸运儿所在区间
        int i =  Arrays.binarySearch(weightArr,rand);
        int luckyIndex = -1;
        if(i >= 0)
            luckyIndex = i;
        else
            luckyIndex = -i -1;
        return studentList.get(luckyIndex);
    }
    public static void main(String [] args) {
        System.out.println("幸运儿是" + getLuckyStudentName().getName());
    }
}

