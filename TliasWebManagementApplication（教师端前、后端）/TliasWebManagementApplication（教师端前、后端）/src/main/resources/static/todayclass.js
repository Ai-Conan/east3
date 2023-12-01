window.onload = function() {
   console.log('initinitinit');
   getCurrentSchedule();
   var selectWeek = document.getElementById("select_week");
   selectWeek.addEventListener('change', function() {
       getSelectSchedule(selectWeek.value);
       console.log('changechangechange');
       console.log(selectWeek.value)
     });
}

function getCurrentSchedule() {

   var no = sessionStorage.getItem('no');
   var apiUrl = `http://47.120.48.211:8080/${no}/schedule`;
   console.log(apiUrl)
   fetch(apiUrl)
   .then(response => response.json())
   .then(result => {
      console.log(result);
      if (result.code === 1) {
         var timetable = document.getElementById("timetable");
         var data = result.data;

         var selectWeek = document.getElementById("select_week");

         console.log(data);
         console.log(data.length);

         // 获取当前周数
         var currentWeek = data[0].week;
         // 设置下拉框的值为当前周数
         selectWeek.value = currentWeek;
         console.log(currentWeek);
         console.log(data.length);
         console.log(data);

         for (var i = 0; i < data.length; i++) {
            var course = data[i];
            var weekday = course.weekday;
            var section = course.section;
            var courseName = course.courseName;
            var coursePlace = course.coursePlace;

            var cellId = getCellId(weekday, section);
            var cell = document.getElementById(cellId);

            if (cell) {
               var spanName = document.createElement("span");
               spanName.className = "course-name";
               spanName.innerText = courseName;

               var spanLocation = document.createElement("span");
               spanLocation.className = "course-location";
               spanLocation.innerText = coursePlace;

               cell.innerHTML = "";
               cell.appendChild(spanName);
               cell.appendChild(document.createElement("br"));
               cell.appendChild(spanLocation);

               cell.className = get_color(i);
               // 在清空innerHTML之后添加class="has-course"
               cell.classList.add("has-course"); 

            }
         }
      }else {  // 如果结果为空，则清除所有课程信息
         var cells = document.querySelectorAll(".has-course");
         cells.forEach(function(cell) {
            cell.innerHTML = "";
            cell.classList.remove("has-course");
         });
      }
   })
   .catch(error => console.log('error', error));
}

function getSelectSchedule(week) {
   var no = sessionStorage.getItem('no');
   ///
   var apiUrl = `http://47.120.48.211:8080/${no}/scheduleWhat/ToBeOrNotToBe?week=`+week;
   console.log(apiUrl);
   fetch(apiUrl)
   .then(response => response.json())
   .then(result => {
      console.log(result);
      if (result.code === 1) {
         var timetable = document.getElementById("timetable");
         var data = result.data;

         //清除之前页面信息
         var cellts = document.querySelectorAll(".has-course");
         cellts.forEach(function(cellt) {
            cellt.innerHTML = "";
            cellt.classList.remove("has-course");
         });


         for (var i = 0; i < data.length; i++) {
            var course = data[i];
            var weekday = course.weekday;
            var section = course.section;
            var courseName = course.courseName;
            var coursePlace = course.coursePlace;

            var cellId = getCellId(weekday, section);
            var cell = document.getElementById(cellId);

            if (cell) {
               var spanName = document.createElement("span");
               spanName.className = "course-name";
               spanName.innerText = courseName;

               var spanLocation = document.createElement("span");
               spanLocation.className = "course-location";
               spanLocation.innerText = coursePlace;

               cell.innerHTML = "";
               cell.appendChild(spanName);
               cell.appendChild(document.createElement("br"));
               cell.appendChild(spanLocation);

               cell.className = get_color(i);
               // 在清空innerHTML之后添加class="has-course"
               cell.classList.add("has-course"); 

            }
         }
      }else {  // 如果结果为空，则清除所有课程信息
         var cells = document.querySelectorAll(".has-course");
         cells.forEach(function(cell) {
            cell.innerHTML = "";
            cell.classList.remove("has-course");
         });
      }
   })
   .catch(error => console.log('error', error));
}


// 辅助函数：根据星期和节次获取格子的 id
function getCellId(weekday, section) {
   var days = ["Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"];
   var sections = ["12", "34", "56", "78", "9"];

   var day = days[weekday - 1];
   var sectionIndex = Math.floor((parseInt(section) - 1) / 2);
   var sectionLabel = sections[sectionIndex];

   return day + "-" + sectionLabel;
}

// 辅助函数：根据索引获取相应的样式类
// 辅助函数：根据索引获取相应的样式类
function get_color(index) {
   var colors = ["course-style-1", "course-style-2", "course-style-3","course-style-4"];
   return colors[index % colors.length];
}

