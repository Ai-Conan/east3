// pages/appealdetail/appealdetail.js
Page({
  data: {
    appealDetail: {},
    appealId: ''
  },

  onLoad: function (options) {
    var that = this;
    var appealId = options.id;
    this.setData({ appealId: appealId });

    wx.request({
      url: 'http://47.120.48.211:9090/attendanceAppealDetail/' + appealId, 
      method: 'GET',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        if (res.data.code === 1) {
          that.setData({
            appealDetail: res.data.data
          });
        }
      }
    });
  },

  approveAppeal: function() {
    this.updateAppealStatus('通过');
  },

  denyAppeal: function() {
    this.updateAppealStatus('不通过');
  },

  updateAppealStatus: function(status) {
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/judgeAttendanceAppeal/' + this.data.appealId + '/' + status, 
      method: 'PUT',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        if (res.data.code == 1) {
          wx.showToast({
            title: '审批状态已更新',
            icon: 'success',
            duration: 2000
          });
          that.data.appealDetail.status = status;
          that.setData({ appealDetail: that.data.appealDetail });
        } else {
          wx.showToast({
            title: '更新失败请重试',
            icon: 'error',
            duration: 2000
          });
        }
      }
    });
  }
});
