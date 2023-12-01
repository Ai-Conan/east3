Page({
  data: {
    no: '',        // 用于保存用户名
    password: '',  // 用于保存密码
  },
  
  onLoad() {
    wx.hideHomeButton(); //避免“mine”界面退出登录跳转到该界面时，左上角出现home键
  },
  
  // 处理用户名输入事件
  handleInputNo(event) {
    this.setData({
      no: event.detail.value,
    });
  },

  // 处理密码输入事件
  handleInputPassword(event) {
    this.setData({
      password: event.detail.value,
    });
  },

  handleLogin(event) {
    // 使用 this.data.no 和 this.data.password 来获取输入的值
    console.log(this.data.no, this.data.password);  

    if (!this.data.no || !this.data.password) {
      wx.showToast({ title: '用户名或密码不能为空', icon: 'none' });
      return;
    }

    wx.showLoading({
      title: '正在登录...',
    });

    // 发送登录请求到服务器
    wx.request({
      url: 'http://47.120.48.211:9090/loginStudent',
      method: 'POST',
      data: { 
        no: this.data.no, 
        password: this.data.password 
      },
      header: {
        'content-type': 'application/json', // 默认值
      },
      success: function (res) {
        wx.hideLoading();

        if (res.data.code === 1) {
          // 登录成功，将JWT令牌保存到本地存储
          wx.setStorageSync('token', res.data.data);

          // 跳转到主页面
          wx.switchTab({
            url: '/pages/sign/sign',
          });
        } else {
          // 登录失败，弹出错误提示框
          wx.showModal({
            title: '错误提示',
            content: '账号或密码错误', /*res.data.msg*/
            showCancel: false,
          });
        }
      },
      fail: function () {
        wx.hideLoading();

        wx.showModal({
          title: '错误提示',
          content: '网络出现了问题，请稍后重试！',
          showCancel: false,
        });
      },
      complete: function () {},
    });
  },
});
