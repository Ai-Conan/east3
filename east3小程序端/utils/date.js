/**
 * 日期操作类
 */

/**
 * 计算给定日期自2023年8月28日起的周数
 */
export var getWeeksSinceBaseDate = function (date) {
  let baseDate = new Date(2023, 7, 28); // 2023年8月28日作为起始日期
  let currentDate = new Date(date);

  // 计算当前日期与起始日期之间的毫秒差
  let millisecondDiff = currentDate - baseDate;

  // 将毫秒差转换为天数
  let dayDiff = Math.ceil(millisecondDiff / (1000 * 60 * 60 * 24));

  // 计算周数，向上取整
  return Math.ceil(dayDiff / 7);
}


/**
 * 当天是当前月的第几周
 */
export var getMonthWeekth = function (date) {
  date = new Date(date)
  let day = date.getDate()
  let week = date.getDay()

  // 当前周的还有几天过完(不算今天)，加上当前天的和在除以7，就是当天是当前月份的第几周
  return Math.ceil((6 - week + day) / 7)
}

/**
 * 当天是本年的第几周
 */
export var getYearWeekth = function (date) {
  date = new Date(date); // 当前日期
  let date2 = new Date(date.getFullYear(), 0, 1); // 当年第一天
  let day = Math.round((date.valueOf() - date2.valueOf()) / 86400000); // 当前日期是今年第多少天

  // 用day加当前年的第一天的周差距的和在除以7就是本年第几周
  return Math.ceil((day + ((date2.getDay() + 1) - 1)) / 7);
}


/**
 * 计算相邻日期
 */
export var getDiffDate = function (date, num, changeSelf = true) {
  if (!changeSelf) {
    date = new Date(date)
  }
  date.setDate(date.getDate() + num)
  return date
}

/**
 * 根据当天日期获取本周第一天
 */
export var getWeekFirstDate = function (date) {
  date = new Date(date)
  return getDiffDate(date, -this.formatWeekOrder(date.getDay()))
}

/**
 * 格式化周日期顺序（0-6）
 */
export var formatWeekOrder = function (day) {
  return (day == 0 ? 6 : day - 1)
}
