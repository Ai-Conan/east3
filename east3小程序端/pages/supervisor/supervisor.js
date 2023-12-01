// pages/supervisor/supervisor.js
Page({
    /**
   * 页面的初始数据
   */
  data: {
    switchRoleStatus: true,  // 将 switchRoleStatus 的默认值改为 true，即督导身份
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
  // 跳转到请假审核页面
  navigateToLeave: function() {
    wx.navigateTo({
      url: '/pages/superleave/superleave'
    })
  },

  // 跳转到考勤审核页面
  navigateToAppeal: function() {
    wx.navigateTo({
      url: '/pages/superappeal/superappeal'
    })
  },
  // 跳转到督导规则页面
  navigateToRule: function() {
    wx.navigateTo({
      url: '/pages/superrule/superrule'
    })
  },
  // 跳转到退出登录页面
  logout: function() {
    wx.redirectTo({
      url: '/pages/login/login'
    })({
      url: '/pages/login/login'
    })
  },
  // 跳转到任务页面
  navigateToTask: function() {
    wx.navigateTo({//wx.redirectTo
      url: '/pages/supertask/supertask'
    })
  },

  // 跳转到我的页面
  // navigateToMine: function() {
  //   wx.navigateTo({
  //     url: '/pages/mine/mine'
  //   })
  // },



  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
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
    // 不推荐使用getUserInfo获取用户信息，预计自2021年4月13日起，getUserInfo将不再弹出弹窗，并直接返回匿名的用户个人信息
    console.log(e)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  
  switchRole() {
    this.setData({
      switchRoleStatus: !this.data.switchRoleStatus
    })
    if (!this.data.switchRoleStatus) {  // 将判断条件改为 this.data.switchRoleStatus == false
      // 不再请求 '/isSupervisor'，直接跳转到 'mine' 页面
      wx.switchTab({
        url:'/pages/mine/mine'
      })
    }
  }
})
