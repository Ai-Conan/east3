// pages/superleave/superleave.js
Page({
  data: {
    leaveList: []
  },

  onLoad: function () {
    console.log("onLoad function called");
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/supervisionLeave', 
      method: 'GET',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        console.log("Request successful. Response:", res.data);//检查请求是否成功：在onLoad函数中的success回调函数内添加该代码，以便检查请求是否成功
        if (res.data.code === 1) {
          let data = res.data.data;
          data.forEach(item => {
            item.statusClass = that.getStatusClass(item.status);
          });
          that.setData({
            leaveList: data
          });
        }
      }
    });
  },

  getStatusClass: function(status) {
    switch(status) {
      case '未通过':
        return 'pending';
      case '通过':
        return 'approved';
      case '不通过':
        return 'denied';
      default:
        return '';
    }
  },

  gotoDetail: function(e) {
    console.log("gotoDetail function called");
    var leaveId = e.currentTarget.dataset.id;
    console.log('gotoDetail is called with id:', leaveId);
    wx.redirectTo({
      url: '/pages/leavedetail/leavedetail?id=' + leaveId,
      fail: function(error) {
        console.log('wx.navigateTo failed with:', error);
      }
    });
  }
  
  
})
