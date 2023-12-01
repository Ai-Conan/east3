/*
 * @Description: 
 */
// pages/appealadd/appealadd.js
Page({
  data: {
      reason: '',
      appealBeginTime: '',
      appealEndTime: ''
  },
  updateReason(e) {
    this.setData({
      reason: e.detail.value
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

  updateAppealEndTime(e) {
    this.setData({
      appealEndTime: e.detail.value
    });
  },

  submitAppeal(e) {
      const { reason, appealBeginTime, appealEndTime } = this.data;
      console.log( reason,appealBeginTime,appealEndTime);
      wx.request({
          url: 'http://47.120.48.211:9090/addAttendanceAppeal', //请将此URL替换为实际的URL
          method: 'POST',
          header: {
            'content-type': 'application/json',
            'token': wx.getStorageSync('token')
          },
          data: {
              reason,
              appealBeginTime,
              appealEndTime
          },
          
          success(res) {
              if(res.data.code === 1) {
                  wx.showToast({
                      title: '申诉成功',
                      icon: 'success'
                  });
              } else {
                  wx.showToast({
                      title: '提交信息有误',//，与数据库考勤信息不符
                      icon: 'error'
                  });
              }
          },
          fail() {
              wx.showToast({
                  title: '提交失败',
                  icon: 'error'
              });
          }
      });
  }
});
