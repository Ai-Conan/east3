//获取响应，并生成课程容器
function init() {
    var no = sessionStorage.getItem("no");
    //const no = '00000003';
    const url = `http://47.120.48.211:8080/${no}/course`;
    //alert(url)
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("GET", url)

    //xhr.setRequestHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
    //xhr.setRequestHeader("Accept", "*/*");
    //xhr.setRequestHeader("Host", "127.0.0.1:4523");

    xhr.setRequestHeader("Connection", "keep-alive");
    xhr.send();

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            console.log(data); // 在控制台中打印 data 变量

            var dataCount = data.data.length; // 获取课程数量
            var rowCount = Math.ceil(dataCount / 3); // 计算行数
            var containerWrapper = document.querySelector('.midall');

            //设置滚动条是否出现
            if (rowCount < 3) {
                var rightContainer = document.querySelector('.rightcontainer');
                rightContainer.classList.add('hideOverflowY');
            }
            else {
                var rightContainer = document.querySelector('.rightcontainer');
                rightContainer.classList.add('OverflowY');
            }

            //生成容器
            for (var i = 0; i < rowCount; i++) {
                var containerCount = i === rowCount - 1 ? dataCount % 3 : 3; // 判断是否是最后一行，若是则设置实际容器数量
                for (var j = 0; j < containerCount; j++) {
                    var colorClass = (i + j) % 2 === 0 ? 'blue' : 'green';
                    var container = document.createElement('div');
                    container.className = 'midcontainer ' + 'actstop ' + colorClass;
                    container.onclick = getdetail; // 添加点击事件

                    // 创建并添加新元素
                    var title = document.createElement('p');
                    title.className = 'containertitle';
                    title.textContent = data.data[i * 3 + j].course_name;
                    sessionStorage.setItem("title", data.data[i * 3 + j].course_name);
                    container.appendChild(title);

                    var memberImg = document.createElement('img');
                    memberImg.className = 'containermemberimg';
                    memberImg.src = 'https://pic.imgdb.cn/item/6566e394c458853aefe74ac8.png';
                    container.appendChild(memberImg);

                    var moreImg = document.createElement('img');
                    moreImg.className = 'more';
                    moreImg.src = 'https://pic.imgdb.cn/item/6566e394c458853aefe74ae8.png';
                    container.appendChild(moreImg);

                    var memberCount = document.createElement('p');
                    memberCount.className = 'containermember';
                    memberCount.textContent = data.data[i * 3 + j].count;
                    container.appendChild(memberCount);

                    var shape = document.createElement('div');
                    shape.className = (i + j) % 2 === 0 ? 'circle' : 'square';
                    container.appendChild(shape);


                    containerWrapper.appendChild(container);
                }
            }
        }
    }
};
//课程的点击事件
function getdetail() {
    var title = this.querySelector('.containertitle').textContent;
    sessionStorage.setItem("title", title);
    window.location.href = "attendancerecordee.html";
};

//弹出提示框
var alertTimeout;
function showAlert(message) {
    var alertBox = document.getElementById('customAlert');
    var alertMessage = document.getElementById('alertMessage');
    alertMessage.innerText = message;
    alertBox.classList.add('show');

    clearTimeout(alertTimeout); // 取消之前的隐藏定时器

    alertTimeout = setTimeout(function() {
        alertBox.classList.remove('show');
        // 提示框0.8秒后跳转页面
        setTimeout(function() {
            window.location.href = "./attendancerecorde.html";
        }, 0);
    }, 700);
}

// 获取搜索框和表单元素
var searchInput = document.getElementById('searchInput');
var searchForm = document.querySelector('.search form');

// 添加回车键监听事件
searchInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') { // 判断是否按下回车键
        event.preventDefault(); // 阻止默认行为，避免提交表单
        search(); // 执行搜索操作
    }
});

// 搜索函数
function search() {
    var input = document.querySelector('.searchtext').value.toLowerCase().trim(); // 获取搜索框中的输入并转换为小写
    var containers = document.querySelectorAll('.midcontainer'); // 获取所有容器

    //如果输入内容为空，则跳转回一级页面
    if (input === '') {
        window.location.href = "./attendancerecorde.html";
        return;
    }

    for (var i = 0; i < containers.length; i++) {
        var title = containers[i].querySelector('.containertitle').textContent.toLowerCase(); // 获取容器标题并转换为小写
        if (title.includes(input)) { // 判断容器标题是否包含搜索关键字
            containers[i].style.display = 'inline-block'; // 显示该容器
        } else {
            containers[i].style.display = 'none'; // 隐藏该容器
        }
    }

    var visibleContainers = document.querySelectorAll('.midcontainer[style*="display: inline-block"]'); // 获取所有可见的容器

    if (visibleContainers.length === 0) {
        // 如果没有匹配到任何课程容器
        showAlert("不存在包含'" + input + "'的课程，请重新输入！");
    }

    var rowCount = Math.ceil(visibleContainers.length / 3); // 计算行数

    //调整容器位置
    for (var j = 0; j < visibleContainers.length; j++) {
        var index = Array.prototype.indexOf.call(visibleContainers, visibleContainers[j]); // 获取该容器在所有可见容器中的索引
        var row = Math.floor(index / 3); // 计算所在行数
        var col = index % 3; // 计算所在列数
        visibleContainers[j].style.top = row * 0 + '%'; // 设置容器纵向位置
        visibleContainers[j].style.left = col * 0 + '%'; // 设置容器横向位置
    }

    // 设置滚动条是否出现
    if (rowCount < 3) {
        var rightContainer = document.querySelector('.rightcontainer');
        rightContainer.style.overflowY = 'hidden';
    }

}
