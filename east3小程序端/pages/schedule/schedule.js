import * as dateUtil from '../../utils/date'
import { getInArray, indexOfArray } from '../../utils/util'
import service from '../../utils/service'

let app = getApp()
let today = new Date()
let curFirstWeekDate // 当前周的第一天
let weekInfoList // 周信息列表
let curWeekIndex // 当前周索引
let weekCourseList // 周课程列表

let formatWeekDate = function (date) {
  return date.getDate() + '日'
}

Page({
  /**
   * 页面的初始数据
   */
  data: {
    todayIndex: -1, // 当天索引0-6，-1表示不在当前周
    curMonth: '', // 当前月份
    curWeek: '', // 当前周数
    curTitle: '', // 当前周标题
    courseColors: ['#FFE699','#B4C7E7','#C5E0B4'], // '#ffca7f', '#91d7fd', '#96a4f9'0缺勤 1已签到 2未签到
    weekLabels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    weekDates: [], // 周日期列表
    tasklist: [] // 课程列表
  },

  /**
   * 课程详情
   */
  showCardView: function (event) {
    let ele = event.currentTarget
    let type = ele.dataset.type
    let index = ele.dataset.index
    app.globalData.currentCourse = weekCourseList[index]

    if (type == 2) {
      wx.switchTab({
        url: '../queryCourse/queryCourse',
      })
    } else {
      wx.navigateTo({
        url: '../editCourse/editCourse',
      })
    }
  },

  /**
   * 新建课程
   */
  addCourseHandler: function (event) {
    wx.switchTab({
      url: '../addCourse/addCourse',
    })
  },

  /**
   * 上一周
   */
  prevWeekHandler: function (event) {
    this.updateWeeks(dateUtil.getDiffDate(curFirstWeekDate, -7))
  },

  /**
   * 下一周
   */
  nextWeekHandler: function (event) {
    this.updateWeeks(dateUtil.getDiffDate(curFirstWeekDate, 7))
  },

  /**
   * 反馈
   */
  feedbackHandler: function (event) {
    wx.navigateTo({
      url: '../feedback/feedback',
    })
  },

  /**
   * 从状态获取类型值
   */
  getTypeFromStatus: function (status) {
    switch (status) {
      case '缺勤':
        return 0;
      case '已签到':
        return 1;
      case '未签到':
        return 2;
      case '请假':
        return 3;
      default:
        return -1; // 未知状态，可以根据需求进行处理
    }
  },

  /**
   * 更新课程信息
   */
  updateWeeks: function (date) {
    wx.showLoading({
      title: '加载中',
    })
    // 判断是否当前天
    let todayIndex = -1
    if (date.getFullYear() == today.getFullYear() && date.getMonth() == today.getMonth() && dateUtil.getMonthWeekth(date) == dateUtil.getMonthWeekth(today)) {
      todayIndex = dateUtil.formatWeekOrder(today.getDay())
    }

    // 获取当前周日期
    curFirstWeekDate = dateUtil.getWeekFirstDate(date)
    let curMonth = curFirstWeekDate.getMonth() + 1
    let weekDates = []
    let tmpFirstDate = new Date(curFirstWeekDate)
    for (var i = 0; i < 7; i++) {
      if (i == 0) {
        weekDates.push(formatWeekDate(curFirstWeekDate))
      } else {
        weekDates.push(formatWeekDate(dateUtil.getDiffDate(tmpFirstDate, 1)))
      }
    }
  
    let curTitle = '23-24学年秋季'
    // 将日期转换为API请求所需的格式（使用 getYearWeekth 获取全年的周数）
    let week = dateUtil.getYearWeekth(date)
    // 使用新的周数计算方法
    let currentWeekNumber = dateUtil.getWeeksSinceBaseDate(date);
    let curWeek = '第' + currentWeekNumber + '周';
    // 发起API请求，获取指定周的课程数据
    wx.request({
      url: 'http://47.120.48.211:9090/courses',
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      data: {
        week: currentWeekNumber.toString(),
      },
      success: (res) => {
        const { code, data } = res.data
        console.log(data);
        if (code === 1) {
          // 处理接收到的课程数据
          let tasklist = data.map(course => ({
            id: course.id,
            type: this.getTypeFromStatus(course.status),
            day: parseInt(course.weekday) - 1,
            start: parseInt(course.section.split('-')[0]),
            sections: parseInt(course.section.split('-')[1]) - parseInt(course.section.split('-')[0]) + 1,
            course: course.courseName,
            teacher: course.teacherName,
            place: course.coursePlace,
            color: '#FFFFFF',
            textColor: course.status == "缺勤" ? '#dc3545' : course.status == "已签到"||course.status=="请假" ? '#28a745' : '#A7D3E8' // 根据type值确定字体颜色
          }))
          console.log("本周的信息为：" + tasklist)
          // 使用获取的课程信息更新页面数据
          this.setData({
            todayIndex,
            curMonth,
            curWeek,
            weekDates,
            curTitle,
            tasklist
          })
        }
      },
      complete: () => {
        wx.hideLoading()
      }
    })
  

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('schedule page onLoad')
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    console.log('schedule page onReady')
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log('schedule page onShow')

    wx.showLoading({
      title: '加载中',
    })

    // FIXME for demo
    this.updateWeeks(today)
  },
})
