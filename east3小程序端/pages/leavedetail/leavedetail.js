Page({
  data: {
    leaveDetail: {},
    leaveId: ''
  },

  onLoad: function (options) {
    var that = this;
    var leaveId = options.id;
    this.setData({ leaveId: leaveId });

    wx.request({
      url: 'http://47.120.48.211:9090/leaveDetail/' + leaveId, 
      method: 'GET',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        if (res.data.code === 1) {
          that.setData({
            leaveDetail: res.data.data
          });
        }
      }
    });
  },

  approveLeave: function() {
    this.updateLeaveStatus('通过');
  },

  denyLeave: function() {
    this.updateLeaveStatus('不通过');
  },

  updateLeaveStatus: function(status) {
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/judgeLeave/' + this.data.leaveId + '/' + status, 
      method: 'PUT',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        console.log(111);        if (res.data.code === 1) {
          wx.showToast({
            title: '审批状态已更新',
            icon: 'success',
            duration: 2000
          });
          that.data.leaveDetail.status = status;
          that.setData({ leaveDetail: that.data.leaveDetail });
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
})
