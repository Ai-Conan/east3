let qqMapSdk = require("../../utils/qqmap.js");
let util = require('../../utils/util.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    flag:1,//flag=0表示已经按过该按钮
    studentInfo: {},  // 学生信息
    courseInfo: {},   // 课程信息
    studentId: null,
    courseId: null,
    courseName: '',
    courseStatus: '未签到',  // 未签到最后都会变成“已签到”或者“缺勤”
    courseendTime: '',
    coursePlace: '',
    status: 0,  // 0未打卡 1已打卡
    is_out: 2,  // 1在签到范围内 2不在
    is_intime: 0,  // 1不在规定签到时间内 0在
    now_time: '',  // 当前时间
    nowDate: '',  // 当前年月日
    nowDay: '',  // 星期几
    tip: '',  // 提示 上午好、下午好
    current_address: '',  // 当前定位地址
    latlng: [],  // 经纬度
    now_time_stop: '',  // 已打卡时间
    area: {},  // 考勤点多个
    record: [],  // 打卡记录                            
  },

  onLoad: function (options) {
    // 检查是否存在有效的用户会话
    const token = wx.getStorageSync('token');
    if (!token) {
      // 如果没有token，重定向到登录页面
      wx.redirectTo({
        url: '/pages/login/login'
      });
      return;
    }
    // 请求学生信息接口
    wx.request({
      url: 'http://47.120.48.211:9090/studentInformation',
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: res => {
        // 将获取到的学生信息传递给签到界面
        this.setData({
          studentInfo: res.data.data,
          studentId: res.data.data.id
        })
      }
    });

    // 请求课程信息
    this.updateCourseInfo();

    this.getCurrentTime();
    this.setData({
      now_time: this.getTime(),
      nowDate: util.formatTime(new Date()),
      nowDay: util.formatDay(new Date()),
      tip: util.formatSole(),
    })
  },

  updateCourseInfo: function () {
    wx.request({
      url: 'http://47.120.48.211:9090/course',
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.data != null) {
          const course = res.data.data;
          console.log("111",course)
          this.setData({
            courseId:course.id,
            courseName: course.courseName,
            courseStatus: course.status,
            courseendTime: course.endTime,
            coursePlace: course.coursePlace
          });
        }
      },
    });
  },

  onShow: function () {
    // 检查是否存在有效的用户会话
    const token = wx.getStorageSync('token');
    if (!token) {
      // 如果没有token，重定向到登录页面
      wx.redirectTo({
        url: '/pages/login/login'
      });
      return;
    }
    this.getLocation();
    this.updateCourseInfo();
    // this.setData({
    //   status: 0, // 未签到
    //   courseStatus: "未签到",
    //   current_address: '',
    // })
  },

  signTap: function () {
    var that = this;
    if (!that.data.current_address) {
      return wx.showToast({
        title: '未获取当前定位',
        icon: 'error'
      });
    }

    // 执行打卡操作的逻辑...
    // 进行业务处理
    var list = that.data.record.concat({ 'times': that.data.now_time, 'address': that.data.current_address });
    wx.vibrateLong();  // 手机震动提示
    that.getSignRecord();  // 处理打卡记录和判断打卡地点是否为督导发布打卡任务的地点。
    that.getisinTime();
    console.log(this.data.flag,this.data.is_out,this.data.is_intime)
    if (this.data.courseName!=''&&this.data.courseStatus=="未签到"&&this.data.flag&&this.data.is_out == 1 && this.data.is_intime == 0) {
      that.setData({
        // 更新签到状态
        flag:0,
        status: 1,  // 已打卡
        courseStatus: "已签到",  // 已签到
        record: list,
        now_time_stop: that.data.now_time,
      });
      console.log("已成功签到");
      
      console.log("2222",this.data.studentId,this.data.courseId,this.data.courseStatus)
      wx.request({  // 发送签到数据到服务器
        url: 'http://47.120.48.211:9090/Attendance',
        method: 'POST',
        header: {
          'token': wx.getStorageSync('token')
        },
        data: {
          studentId: this.data.studentId,
          courseId: this.data.courseId,
          status: this.data.courseStatus
        },
        success: function (res) {
          // console.log(res.data);  // 处理后端返回的数据
          console.log('签到数据上传成功：', res)
        },
        fail: function (res) {
          console.log('签到数据上传失败：', res)
        }
      })

      // 更新课程信息
      this.updateCourseInfo();
    }
  },

  getCurrentTime: function () {
    var flag=1;
    var time = setInterval(() => {
      this.setData({
        now_time: this.getTime()
      });

      // 定时执行判断是否缺勤的代码，判断是否缺勤以及上传post请求
      if (this.data.courseName != '') {  // 如果有要打卡的课程
        var that = this;
        that.getisinTime();
        if(flag==1){
          if (this.data.is_intime == 1) {  // 已经过了打卡时间
            if (this.data.courseStatus == "未签到") {  // 如果仍然是未签到
              that.setData({
                courseStatus: '缺勤'
              });
            } 
            wx.request({  // 发送签到数据到服务器
              url: 'http://47.120.48.211:9090/Attendance',
              method: 'POST',
              header: {
                'token': wx.getStorageSync('token')
              },
              data: {
                studentId: this.data.studentId,
                courseId: this.data.courseId,
                status: this.data.courseStatus
              },
              success: function (res) {
                console.log(res.data);  // 处理后端返回的数据
                console.log('签到数据上传成功：', res)
              },
              fail: function (res) {
                console.log('签到数据上传失败：', res)
              }
            })
          }
        }
        flag=0;
      }
    }, 1000)
  },


getTime() {
    let dateTime = '';
    let hh = new Date().getHours();
    let mf = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes();
    let ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds();
    dateTime = hh + ':' + mf + ':' + ss;
    return dateTime;
  },

  // 请求获取定位授权，提示用户打开手机定位。
  getUserAuth: function () {
    return new Promise((resolve, reject) => {
      wx.authorize({
        scope: 'scope.userLocation'
      }).then(() => {
        resolve()
      }).catch(() => {
        let that = this;
        wx.getSetting({
          success: (res) => {
            if (res.authSetting['scope.userLocation'] != undefined && res.authSetting['scope.userLocation'] != true) {
              wx.showModal({
                title: '请求授权当前位置',
                content: '需要获取您的地理位置，请确认授权',
                success: function (res) {
                  if (res.cancel) {
                    wx.showToast({
                      title: '拒绝授权',
                      icon: 'none',
                      duration: 1000
                    })
                  } else if (res.confirm) {
                    wx.openSetting({
                      success: function (dataAu) {
                        if (dataAu.authSetting["scope.userLocation"] == true) {
                          // 再次授权，调用wx.getLocation的API
                          that.getLocation();
                        } else {
                          wx.showToast({
                            title: '授权失败',
                            icon: 'none',
                            duration: 1000
                          })
                        }
                      }
                    })
                  }
                }
              })
            } else if (res.authSetting['scope.userLocation'] == undefined) {
              that.getLocation();
            } else {
              that.getLocation();
            }
          }
        })
      })
    })
  },

  getLocation: function () {
    const that = this;
    // 实例化腾讯地图API核心类
    const QQMapWX = new qqMapSdk({
      // key: 请输入您自己的key
    });
    // 获取当前位置
    wx.getLocation({
      type: 'gcj02',
      success: function (res) {
        that.latitude = res.latitude;
        that.longitude = res.longitude;
        QQMapWX.reverseGeocoder({  // 使用腾讯地图 API 的 QQMapWX.reverseGeocoder() 方法将经纬度转换为详细的地理位置信息
          location: {
            latitude: res.latitude,
            longitude: res.longitude
          },
          success: function (res) {
            let address = res.result.address + res.result.formatted_addresses.recommend;
            that.setData({
              current_address: address,
              latlng: [res.result.location.lat, res.result.location.lng]
            });
            // 在成功获取定位后调用 getSignRecord
            that.getSignRecord();
          },
          fail: function (res) {
            // 处理定位失败的情况
            console.error("定位失败: ", res);
            that.getUserAuth();
          }
        });
      },
      fail: function (res) {
        // 处理获取位置失败的情况
        console.error("获取位置失败: ", res);
        that.getUserAuth();
      }
    });
  },
  // 刷新定位
  refreshAdd() {
    this.getLocation();
    this.getSignRecord();
  },

  // // 处理打卡记录及判断打卡位置是否办课程所在教室的位置
  // getSignRecord: function () {
  //   var that = this;
  //   console.log("latlng:", that.data.latlng);
  //   if (that.data.latlng && that.data.latlng.length == 2) {
  //     var distance = that.getDistance(that.data.latlng[0], that.data.latlng[1], 26.07421, 119.29647);
  //     console.log("您当前距离指定签到的位置距离为：" + distance + "米");
  //   } else {
  //     console.log("无效的经纬度数据");
  //   }
  // },
  // 处理打卡记录及判断打卡位置是否办课程所在教室的位置
  getSignRecord: function () {
    var that = this;
    console.log("latlng:", that.data.latlng);
    
    // 可以多个打卡地点判断，自行获取遍历即可
    var distance = that.getDistance(that.data.latlng[0], that.data.latlng[1], 26.15024, 119.13139);
    console.log("您当前距离指定签到的位置距离为："+distance+"米");
    if (distance <100) {  // 如果同学签到地点与督导距离超过100米，则签到不成功
      that.setData({
        is_out: 1,
      })
      console.log("您在定位范围内");
    }else{
      console.log("您不在定位范围内");
    }
  },


  // 判断当前是否在规定的打卡时间内
  getisinTime: function () {
    var that = this;
    // 将签到结束时间转换为Date对象
    const dateA = new Date(this.data.courseendTime);
    // 获取当前时间
    const currentDate = new Date();
    if (currentDate > dateA) {
      console.log("当前打卡时间不在规定签到时间内");
      that.setData({
        is_intime: 1,
      })
    }
  },

  // 经纬度距离计算
  getDistance: function (lat1, lng1, lat2, lng2, unit = false) {
    var radLat1 = lat1 * Math.PI / 180.0;
    var radLat2 = lat2 * Math.PI / 180.0;
    var a = radLat1 - radLat2;
    var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
      Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * 6378.137;  // EARTH_RADIUS
    s = Math.round(s * 10000) / 10000;  // 输出为公里
    if (0) {  // 是否返回带单位
      if (s < 1) {  // 如果距离小于1km返回m
        s = s.toFixed(3);
        s = s * 1000 + "m";
      } else {
        s = s.toFixed(2);
        s = s + "km";
      }
    } else {
      s = s.toFixed(3);
      s = s * 1000;
    }
    return s;
  },
})
