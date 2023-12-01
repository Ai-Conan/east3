// pages/courseAttendance/courseAttendance.js
Page({
  data: {
    searchValue: '',
    courseId: 0,
    attendanceList: []
  },

  onLoad: function (options) {
    console.log("onLoad function called with options:", options);
    var courseId = options.courseId;
    this.setData({
      courseId: courseId
    });
    this.getAttendanceData(courseId);
  },

  getAttendanceData: function (courseId) {
    var that = this;
    wx.request({
      url: 'http://47.120.48.211:9090/courseAttendance/' + courseId,
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success(res) {
        console.log(courseId);
        console.log("Request successful. Response:", res.data);
        if (res.data.code == 1) {
          let data = res.data.data;
          data.forEach(item => {
            item.statusClass = that.getStatusClass(item.status);
          });
          that.setData({
            attendanceList: data
          });
        }
      }
    });
  },

  getStatusClass: function (status) {
    switch (status) {
      case '缺勤':
        return 'absent';
      case '未签到':
        return 'absent';
      case '已签到':
        return 'present';
      case '请假':
        return 'leave';
      default:
        return '';
    }
  },
  onInput: function (event) {
    // 更新搜索内容
    this.setData({
      searchValue: event.detail.value
    });
  },

  onSearch: function () {
    // 执行搜索操作
    var studentId = this.data.searchValue;
    console.log("搜索内容:", studentId, "课程号:", this.data.courseId);
    wx.navigateTo({
      url: '/pages/studentcheck/studentcheck?studentNo=' + studentId + '&courseId=' + this.data.courseId,
      fail: function (error) {
        console.log('wx.navigateTo failed with:', error);
      }
    });
  },

  getAccessToken: function () {
    return new Promise((resolve, reject) => {

    });
  },

  takepicbtn: function () {
    // 调用拍照功能
    wx.authorize({
      scope: 'scope.camera',
      success() {
        wx.chooseMedia({
          count: 1, // 最多可选择的图片数量
          mediaType: ['image'],
          sizeType: ['original'], // 图片的尺寸类型，可以指定原图和压缩图
          sourceType: ['camera'], // 图片的来源，可以指定从相册选择或者使用相机拍摄
          camera: 'back',
          success: function (res) {
            // 1. 获取照片的临时文件路径
            //console.log(res)
            const tempFilePath = res.tempFiles[0].tempFilePath

            // 2. 使用wx.getFileSystemManager()获取文件系统管理器
            const fs = wx.getFileSystemManager();

            // 3. 将照片转换为Base64编码
            const fileData = fs.readFileSync(tempFilePath, 'base64');

            // 4. 构造请求参数
            const AK = "tCINei9dcijhGVtSsNWFSaUH";
            const SK = "8cWqQRalxAelOfaKGLRHEOqB45W4gnGz";
            const Url = `https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=${AK}&client_secret=${SK}`;
            wx.request({
              url: Url,
              method: 'POST',
              success: function (res) {
                //console.log(res.data.access_token);
                const accessToken = res.data.access_token;
                const apiUrl = `https://aip.baidubce.com/rest/2.0/image-classify/v1/body_num?access_token=${accessToken}`;
                const params = {
                  image: fileData,
                  show: true,
                };

                // 5. 发起网络请求
                wx.request({
                  url: apiUrl,
                  method: 'POST',
                  data: params,
                  header: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json'
                  },
                  success: function (res) {
                    // 6. 处理返回结果
                    console.log(res);
                    const result = res.data;
                    console.log(result);

                    // 提取人数信息
                    const personNum = result.person_num;
                    const image = result.image;
                    const filePath = wx.env.USER_DATA_PATH + '/temp_image' + Date.now() + '.jpg';
                    wx.getFileSystemManager().writeFile({
                      filePath: filePath,
                      data: image,
                      encoding: 'base64',
                      success: function () {
                      wx.previewImage({
                      current: filePath,  // 当前显示图片的链接，不填则默认为 urls 的第一张
                      urls: [filePath]  // 需要预览的图片链接列表
                    })
                  }
                })
                    // 弹出提示框显示人数
                    wx.showModal({
                      //title: '人流量统计',
                      content: '当前人数：' + personNum,
                      showCancel: false,
                      confirmText: '确定',
                      success: function (res) {
                        if (res.confirm) {
                          // 用户点击了关闭按钮
                          console.log('提示框已关闭');
                        }
                      }
                    });
                  },
                  fail: function (error) {
                    console.error(error);
                  }
                });
              }
            });
          },
          fail() {
            console.error(error)
          }
        })
      },
      fail: function (error) {
        reject(error);
      }
    });
  }
});