// 切换身份时要改变的
var targetElement = document.getElementById('teacher');
var targetElement2 = document.getElementById('counselor');
// 添加点击事件监听器
targetElement.addEventListener('click', function() {
  // 在点击事件中改变样式
  targetElement.style.backgroundColor = 'rgba(83, 157, 115, 1)';
  targetElement.style.color = 'rgba(255, 255, 255, 1)';
  targetElement2.style.backgroundColor = 'rgba(242, 247, 243, 1)';
  targetElement2.style.color = 'rgba(0, 0, 0, 0.5)';
});

targetElement2.addEventListener('click', function() { 
  // 在点击事件中改变样式
  targetElement.style.backgroundColor =  'rgba(242, 247, 243, 1)';
  targetElement.style.color = 'rgba(0, 0, 0, 0.5)';
  targetElement2.style.backgroundColor = 'rgba(83, 157, 115, 1)';
  targetElement2.style.color = 'rgba(255, 255, 255, 1)';
});
