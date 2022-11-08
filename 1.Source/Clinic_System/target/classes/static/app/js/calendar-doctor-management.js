$(function () {
    $('[data-mask]').inputmask();
    $('#booking-date-admission-add').datetimepicker({
        format: 'L'
    });
  })

$( document ).ready(function() {
    genWeekList();
    $('#ctl00_mainContent_drpYear').change(function() {
        genWeekList();
    });
    $('#ctl00_mainContent_drpSelectWeek').change(function() {
        getCalendar();
    });


});

function genWeekList(){
    var year = $('#ctl00_mainContent_drpYear').val();
    var dateFirst = new Date(year, 0, 1);
    var dateLast = new Date(year, 11, 31);
    var weeksHtml = '';
    var weekHtml = '';
    for(var date = dateFirst; date <= dateLast; date.setDate(date.getDate() + 1)){
        if(date.getDay() == 1){
            weekHtml = '';
            weekHtml += '<option>' + date.getDate() + '/' + (date.getMonth() + 1) + ' to ';
        }
        if(date.getDay() == 5){
            weekHtml += date.getDate() + '/' + (date.getMonth() + 1) + '</option>';
            weeksHtml += weekHtml;
        }
    }
    $('#ctl00_mainContent_drpSelectWeek').html(weeksHtml);
    $('#ctl00_mainContent_drpSelectWeek').trigger('change');
}

function getCalendar(){
    var year = $('#ctl00_mainContent_drpYear').val();
    var week = $('#ctl00_mainContent_drpSelectWeek').val();
    var weekArr = week.split(' to ');
    var fromDate = new Date(year, weekArr[0].split('/')[1] -1, weekArr[0].split('/')[0]);
    var toDate = new Date(year, weekArr[1].split('/')[1] -1, weekArr[1].split('/')[0]);
    for(var date = fromDate; date <= toDate; date.setDate(date.getDate() + 1)){
        $('.data-calender-showDate-' + date.getDay()).html(date.getDate() + '/' + (date.getMonth() + 1));
        $('.data-calender-morning-' + date.getDay()).html('');
        $('.data-calender-afternoon-' + date.getDay()).html('');
        var dateSearch = date.getDate() + '/' + (date.getMonth() + 1) + '/' + year;
        $.ajax({
            type: "GET",
            url: "/api/admission-record/getCalenderDoctor?date=" + dateSearch,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    console.log('test 11');
                    var data = response.data;
                    var htmlMorning = '';
                    var htmlAfternoon = '';
                    if(data != null || data != undefined){
                        var num = 0;
                        for(var i = 0; i < data.length; i++){
                            if(data[i].hourAdmission == 'morning'){
                                htmlMorning += '<p>' + data[i].roomName + ': ' + data[i].fullName;
                                if($('.nut-tao-lich-truc').length > 0){
                                    htmlMorning += '<a onclick="deleteBooking(' + data[i].id + ')"' + '><span class="bi bi-trash"></span></a>';
                                }
                                htmlMorning += '</p>';
                            }else{
                                htmlAfternoon += '<p>' + data[i].roomName + ': ' + data[i].fullName;
                                if($('.nut-tao-lich-truc').length > 0){
                                    htmlAfternoon += '<a onclick="deleteBooking(' + data[i].id + ')"' + '><span class="bi bi-trash"></span></a>';
                                }
                                htmlAfternoon += '</p>';
                            }
                            num = data[i].num;
                        }
                        $('.data-calender-morning-' + num).html(htmlMorning);
                        $('.data-calender-afternoon-' + num).html(htmlAfternoon);
                    }


                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }

}

function getFormattedDate(dateInput) {
  var date = new Date(dateInput);
  var year = date.getFullYear();

  var month = (1 + date.getMonth()).toString();
  month = month.length > 1 ? month : '0' + month;

  var day = date.getDate().toString();
  day = day.length > 1 ? day : '0' + day;

  return day + '/' + month + '/' + year;
}

function setDataPageBooking(currentPage, totalRecord){
    $('.current-page').val(currentPage);
    var showPaging = 'Showing 0 to 0 of 0 entries';
    if(((currentPage - 1) * 10) < totalRecord){
        if((currentPage * 10) <= totalRecord){
            showPaging = 'Showing ' + ((currentPage - 1) * 10 + 1) + ' to ' + (currentPage * 10) + ' of ' + totalRecord + ' entries';
        }else{
            showPaging = 'Showing ' + ((currentPage - 1) * 10 + 1) + ' to ' + totalRecord + ' of ' + totalRecord + ' entries';
        }
    }
    $('.show-to-page').html(showPaging);
}

function eventPagingBooking(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-user').val();
        getUserByPage(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-user').val();
        getUserByPage(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-user').val();
        getUserByPage(dataSearch);
    });
}

function showBooking(id){
    location.href = '/appointmentBooking?id=' + id;
}

function createBooking(){
    location.href = '/appointmentBooking';
}

function createCalenderDoctor(){
    $.ajax({
      url: "/api/user/getAllDoctor",
      dataType: "json",
      headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
      success: function( data ) {
          if(data.responseCode == '00000'){
              var html = '';
              for(var i = 0; i < data.data.length; i++){
                  html += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
              }
              $('#search-name-doctor').html(html);
          }
      }
   });

   $.ajax({
       type: "GET",
       url: "/api/admission-record/getRoom?date=",
       dataType: 'json',
       headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
       success: function (response) {
           if(response.responseCode == '00000'){
               var html = '';
               for(var i = 0; i < response.data.length; i++){
                   html += '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
               }
               $('#booking-room-input').html(html);
           }
       },
       error: function (xhr, ajaxOptions, thrownError) {
           console.log(thrownError);
       }
   });
}

function addCalenderDoctorAction(){
    var data = {};
    data.date = $('#booking-date-admission-add').val();
    data.hourAdmission = $('#booking-hour-admission-add').val();
    data.doctorId = $('#search-name-doctor').val();
    data.roomId = $('#search-name-doctor').val();
    $.ajax({
        type: "POST",
        url: "/api/admission-record/addCalenderDoctor",
        dataType: 'json',
        data: data,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Add',
                    subtitle: '',
                    body: 'Edit successfully.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Add',
                    subtitle: '',
                    body: response.message
                });
            }
            location.reload();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function deleteBooking(id){
    $.ajax({
        type: "POST",
        url: "/api/admission-record/deleteCalenderDoctor?id=" +id,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Delete',
                    subtitle: '',
                    body: 'Delete successfully.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Delete',
                    subtitle: '',
                    body: response.message
                });
            }
            $('#ctl00_mainContent_drpSelectWeek').trigger('change');
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}