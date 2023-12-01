function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  return year + "年" + month + "月" + day + "日";
}
const formatDay = dates => {
  let _day = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六');
  let date = new Date(dates);
  date.setDate(date.getDate());
  let day = date.getDay();
  return _day[day];
}
const formatSole = () => {
  let timeNow = new Date();
  let hours = timeNow.getHours();
  let text = ``;
  if (hours >= 0 && hours <= 6) {
    text = `深夜了,快睡觉!>.<`;
  } else if (hours > 6 && hours <= 8) {
    text = `早上好`;
  } else if (hours > 8 && hours <= 10) {
    text = `上午好`;
  } else if (hours > 10 && hours <= 13) {
    text = `中午好`;
  } else if (hours > 13 && hours <= 17) {
    text = `下午好`;
  } else if (hours > 17 && hours <= 23) {
    text = `晚上好`;
  }
  return text;
}
module.exports = {
  formatTime: formatTime,
  formatDay: formatDay,
  formatSole: formatSole
}
/**
 * 根据某一项值获取当前项所在数组索引，-1说明未查到
 */
export function indexOfArray(key, value, target) {
  for (let index in target) {
    let item = target[index];
    if (item[key] == value) {
      return parseInt(index)
    }
  }

  return -1;
}