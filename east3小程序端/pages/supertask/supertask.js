// pages/supertask/supertask.js
Page({
  data: {
    taskList: []
  },

  onLoad: function () {
    console.log("onLoad function called");
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/SupervisionTask', 
      method: 'GET',
      header: {
        'content-type': 'application/json' ,
        'token': wx.getStorageSync('token')
      },
      success(res) {
        console.log("Request successful. Response:", res.data);
        if (res.data.code === 1) {
          let data = res.data.data.rows;
          that.setData({
            taskList: data
          });
        }
      }
    });
  },

  gotoDetail: function(e) {
    console.log("gotoDetail function called");
    var courseId = e.currentTarget.dataset.id;
    console.log('gotoDetail is called with id:', courseId);
    wx.navigateTo({
      url: '/pages/courseAttendance/courseAttendance?courseId=' + courseId,
      fail: function(error) {
        console.log('wx.navigateTo failed with:', error);
      }
    });
  }
});
