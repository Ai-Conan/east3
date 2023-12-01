// pages/leaveadd/leaveadd.js https://your-domain.com/addAttendanceAppeal
Page({
  data: {
    reason: '',
    leavePlace: '',
    appealBeginTime: '',
    appealEndTime: ''
  },
  
  updateReason(e) {
    this.setData({
      reason: e.detail.value
    });
  },

  updateLeavePlace(e) {
    this.setData({
      leavePlace: e.detail.value
    });
  },

  updateAppealBeginTime(e) {
    this.setData({
      appealBeginTime: e.detail.value
    });
  },

  updateAppealEndTime(e) {
    this.setData({
      appealEndTime: e.detail.value
    });
  },

  submitAppeal(e) {
      const { reason, leavePlace, appealBeginTime, appealEndTime } = this.data;
      console.log(leavePlace);
      wx.request({
          url: 'http://47.120.48.211:9090/addLeave', //请将此URL替换为实际的URL
          method: 'POST',
          header: { // 在请求头部中添加token
            'token': wx.getStorageSync('token')
          },
          data: {
              reason,
              leavePlace,
              appealBeginTime,
              appealEndTime
          },
          success(res) {
              if(res.data.code === 1) {
                  wx.showToast({
                      title: '申请成功',
                      icon: 'success'
                  });
              } else {
                wx.showModal({
                      title: "提交信息有误，未在课表中查到此课程，请认真填写课程开始和结束时间",
                      icon: 'error'
                  });
              }
          },
          fail() {
              wx.showToast({
                  title: '提交失败,请填写完整信息',
                  icon: 'error'
              });
          }
      });
  }
});
