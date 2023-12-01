// pages/mine/mine.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    switchRoleStatus: false,
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName') // 如需尝试获取用户信息可改为false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }

  },
  // 跳转到请假申请页面
  navigateToLeave: function() {
    wx.navigateTo({
      url: '/pages/leave/leave'
    })
  },

  // 跳转到考勤申诉页面
  navigateToAppeal: function() {
    wx.navigateTo({
      url: '/pages/appeal/appeal'
    })
  },
  // 跳转到考勤规则页面
  navigateToRule: function() {
    wx.navigateTo({
      url: '/pages/rule/rule'
    })
  },
  // 跳转到退出登录页面
  logout: function() {
    // 清除本地存储中的所有用户会话信息
    wx.removeStorageSync('token');
    wx.removeStorageSync('userInfo');  // 假设你存储了用户信息
  
    // 重定向到登录页面
    wx.redirectTo({
      url: '/pages/login/login'
    })
  },
  


  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息
    wx.getUserProfile({
      desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log(res)
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    })
  },
  getUserInfo(e) {
    // 不推荐使用getUserInfo获取用户信息
    console.log(e)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  switchRole() {
    const that = this; // 在外部保存 `this` 的引用
    this.setData({
      switchRoleStatus: !this.data.switchRoleStatus
    })
    if (this.data.switchRoleStatus) {
      wx.request({
        url: 'http://47.120.48.211:9090/isSupervisor',
        method: 'GET',
        header: { // 在请求头部中添加token
          'token': wx.getStorageSync('token')
        },
        success(res) {
          if (res.data.code == 1) {
            if (res.data.data==1) {
              // 如果用户是督导，那么跳转到督导页面
              wx.redirectTo({
                url: '/pages/supervisor/supervisor'
              })
            } else {
              // 如果用户不是督导，那么显示错误提示并重置滑块
              wx.showToast({
                title: '抱歉，您不是督导，无权切换。',
                icon: 'none',
                duration: 2000
              })
              that.setData({ // 使用保存的 `this` 引用
                switchRoleStatus: false
              })
            }
          }
        },
        fail(err) {
          console.log(err)
        }
      })
    }
  }
  
})
