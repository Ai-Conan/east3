// pages/superleave/superappeal/superappeal.js
Page({
  data: {
    appealList: []
  },

  onLoad: function () {
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/supervisionAttendanceAppeal', 
      method: 'GET',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        if (res.data.code === 1) {
          let data = res.data.data;
          data.forEach(item => {
            item.statusClass = that.getStatusClass(item.status);
          });
          that.setData({
            appealList: data
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
    var appealId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/appealdetail/appealdetail?id=' + appealId,
      fail: function(error) {
        console.log('wx.navigateTo failed with:', error);
      }
    });
  }
});
