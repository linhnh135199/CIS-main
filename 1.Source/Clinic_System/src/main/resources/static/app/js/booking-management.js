$( document ).ready(function() {
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = '';
    getCalendar(dataSearch);
});

function actionSearchBooking(){
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = $('.key-search-booking').val();
    getCalendar(dataSearch);
}

function sortTable(n) {
  var table,
    rows,
    switching,
    i,
    x,
    y,
    shouldSwitch,
    dir,
    switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  //Set the sorting direction to ascending:
  dir = "asc";
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.getElementsByTagName("TR");
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < rows.length - 1; i++) { //Change i=0 if you have the header th a separate table.
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /*check if the two rows should switch place,
      based on the direction, asc or desc:*/
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      //Each time a switch is done, increase this count by 1:
      switchcount++;
    } else {
      /*If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again.*/
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

function getCalendar(dataSearch){
    $.ajax({
        type: "GET",
        url: "/api/admission-record/getAllBooking" ,
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-booking').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html = '';
                var totalRecord = response.data.total;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageBooking(dataSearch.page, totalRecord);
                }
                var data = response.data.bookingDtos;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td>' + data[i].id + '</td>';
                        html += '<td>' + data[i].phone + '</td>';
                        html += '<td>' + data[i].fullName + '</td>';
                        html += '<td>' + getFormattedDate(data[i].admissionDate) + '</td>';
                        if(data[i].statusApprove == 'receive'){
                            html += '<td>Đã nhận</td>';
                        }else if(data[i].statusApprove == 'cancel'){
                            html += '<td>Đã hủy</td>';
                        } else if(data[i].statusApprove == 'waiting'){
                            html += '<td>Đang chờ</td>';
                        }else if(data[i].statusApprove == 'confirm'){
                            html += '<td>Đã xác nhận</td>';
                        }
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += ' onclick="showBooking(' + data[i].id
                        + ')"' + '><span class="bi bi-eye"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-booking').find('tbody').html(html);
                eventPagingBooking();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
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
    if(((currentPage - 1) * 6) < totalRecord){
        if((currentPage * 6) <= totalRecord){
            showPaging = 'Showing ' + ((currentPage - 1) * 6 + 1) + ' to ' + (currentPage * 6) + ' of ' + totalRecord + ' entries';
        }else{
            showPaging = 'Showing ' + ((currentPage - 1) * 6 + 1) + ' to ' + totalRecord + ' of ' + totalRecord + ' entries';
        }
    }
    $('.show-to-page').html(showPaging);
}

function eventPagingBooking(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-booking').val();
        getCalendar(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-booking').val();
        getCalendar(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-booking').val();
        getCalendar(dataSearch);
    });
}

function showBooking(id){
    location.href = '/appointmentBooking?id=' + id;
}

function createBooking(){
    location.href = '/appointmentBooking';
}

function actionSearchBooking(){
    var dataSearch = {};
    dataSearch.page = Number($('.current-page').val());
    dataSearch.role = location.pathname;
    dataSearch.key = $('.key-search-booking').val();
    getCalendar(dataSearch);
}