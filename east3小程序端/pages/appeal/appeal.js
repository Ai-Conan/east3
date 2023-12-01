Page({
  data: {
    appeals: []
  },

  onLoad: function () {
    this.getAppeals();
  },

  getAppeals: function () {
    let that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/selectAttendanceAppeal',
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      data: {
        // 添加需要的参数
      },
      success: function (res) {
        if (res.data.code === 1) {
          let appeals = res.data.data;
          appeals.sort((a, b) => b.appealBeginTime - a.appealBeginTime);
          appeals.forEach((appeal) => {
            switch (appeal.status) {
              case "未通过":
                appeal.statusClass = "pending";
                break;
              case "通过":
                appeal.statusClass = "approved";
                break;
              case "驳回":
                appeal.statusClass = "rejected";
                break;
            }
          });
          that.setData({appeals});
        }
      }
    })
  },

  navigateToOtherPage: function () {
    wx.navigateTo({
      url: '/pages/appealadd/appealadd'
    })
  }
})
