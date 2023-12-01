Page({
  data: {
    // imageUrl: "/pages/images/fzulogo.png",
    imageUrl: "/pages/images/logo2.jpg",
    studentId: "",
    studentNo: "",
    studentName: "",
    status: ""
  },

  onLoad: function (options) {
    // var studentNo = options.studentNo;
    // var studentId = options.studentId;
    // var courseId = options.courseId;
    // var studentName = options.studentName;
    // this.setData({
    //   studentNo: studentNo,
    //   studentId: studentId,
    //   courseId:courseId,
    //   studentName: studentName
    // });
    var that = this;
    var studentNo = options.studentNo;
    var courseId = options.courseId;
    this.setData({
      courseId:courseId,
    });
    wx.request({
      url: 'http://47.120.48.211:9090/getStudentIdByStudentNo/' + studentNo,
      method: 'GET',
      success: function (res) {
        if (res.data.code == 1) {
          that.setData({
            studentId: res.data.data,
            studentNo: studentNo,
          });
        } else {
          console.log("获取学生ID失败");
        }
      },
      fail: function (error) {
        console.log('获取学生ID请求失败:', error);
      }
    });
  },

  touchStart: function (e) {
    this.startX = e.touches[0].clientX;
  },

  touchMove: function (e) {
    var moveX = e.touches[0].clientX;
    var deltaX = moveX - this.startX;

    // Move the card horizontally
    var card = this.selectComponent("#card");
    card.setLeft(deltaX + "px");
  },

  touchEnd: function (e) {
    var moveX = e.changedTouches[0].clientX;
    var deltaX = moveX - this.startX;

    if (deltaX < -50) {
      this.updateAttendanceStatus("缺勤");
    } else if (deltaX > 50) {
      this.updateAttendanceStatus("已签到");
    }
  },

  updateAttendanceStatus: function (status) {
    var that = this;
    var studentId = this.data.studentId;
    var courseId = this.data.courseId;
    console.log(studentId,courseId);
    wx.request({
      url: 'http://47.120.48.211:9090/Attendance', 
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      data: {
        studentId: studentId,
        courseId: courseId,
        status: status
      },
      success: function (res) {
        console.log(studentId,courseId,status);
        console.log(res.data.code);
        if(res.data.code==1){
          console.log("Attendance status updated to: " + status);
          that.setData({
            status: status
          });
          wx.showToast({
            title: '当前状态已更新为' + status,
            icon: 'success',
            duration: 2000
          });
          that.getNextStudent(); // 添加该行，在签到状态更新成功后，调用获取下一个未签到学生的接口
        }else{
          wx.showToast({//showModal
            title: '请检查学号' ,
            icon: 'loading',
            duration: 2000
          });
        }
      },
      fail: function (error) {
        console.log('API request failed:', error);
      }
    });
  },
  // 新增一个获取下一个未签到学生的函数
  getNextStudent: function () {
    var that = this;
    var courseId = this.data.courseId;
    wx.request({
      url: 'http://47.120.48.211:9090/whoNoCheck/' + courseId,
      method: 'GET',
      success: function (res) {
        if (res.data.code == 1) {
          that.setData({
            studentId: res.data.data.id,
            studentNo: res.data.data.no,
            studentName: res.data.data.name,
            status: ""
          });
        } else {
          console.log("获取下一个未签到学生失败");
        }
      },
      fail: function (error) {
        console.log('获取下一个未签到学生请求失败:', error);
      }
    });
  }
});
