$( document ).ready(function() {
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = '';
    getPaymentPatient(dataSearch);
});

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

function getPaymentPatient(dataSearch){
    $.ajax({
        type: "GET",
        url: "/api/patient/getPaymentPatientRecord",
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-payment-patient').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html = '';
                var totalRecord = response.data.total;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPagePayment(dataSearch.page, totalRecord);
                }
                var data = response.data.paymentDtos;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td>' + (i+1) + '</td>';
                        html += '<td>' + data[i].code + '</td>';
                        html += '<td>' + data[i].fullName + '</td>';
                        html += '<td>' + data[i].phone + '</td>';
                        html += '<td>' + getFormattedDate(data[i].admissionDate) + '</td>';
                        html += '<td>' + (data[i].statusPayment == null ? 'Chưa thanh toán' : data[i].statusPayment) + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-payment-detail" onclick="detailPaymentPatient(' + data[i].idPatient
                        + ')"' + '><span class="bi bi-eye"></span></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-payment-patient').find('tbody').html(html);
                eventPagingPaymentPatient();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function eventPagingPaymentPatient(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.key = $('.key-phone-search-user').val();
        getPaymentPatient(dataSearch);
    });

    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.key = $('.key-phone-search-user').val();
        getPaymentPatient(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.key = $('.key-phone-search-user').val();
        getPaymentPatient(dataSearch);
    });
}

function actionSearchPatientPayment(){
    var dataSearch = {};
    dataSearch.page = Number($('.current-page').val());
    dataSearch.key = $('.key-phone-search-user').val();
    getPaymentPatient(dataSearch);
}

function setDataPagePayment(currentPage, totalRecord){
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

function detailPaymentPatient(id){
    $.ajax({
        type: "GET",
        url: "/api/patient/getPaymentPatientRecordById?id=" + id,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                var data = response.data;
                $('.payment-detail-list').find('tbody').empty();
                if(data != null || data != undefined){
                    $('#id-payment-patient').val(data.idPatient);
                    $('.payment-full-name-patient').text(data.fullName);
                    $('.payment-old-patient').text(data.old);
                    $('.payment-gender-patient').text(data.gender);
                    $('.payment-address-patient').text(data.address);
                    var totalPrice = 0;
                    if(data.services != null || data.services != undefined){
                        var html = '';
                        for(var i = 0; i < data.services.length; i++){
                            html += '<tr>';
                            html += '<td>' + (i+1) + '</td>';
                            html += '<td>' + data.services[i].name + '</td>';
                            html += '<td>' + (data.services[i].soLuong != null ? data.services[i].soLuong : '') + '</td>';
                            html += '<td>' + (data.services[i].price != null ? data.services[i].price : '') + '</td>';
                            html += '<td>' + (data.services[i].price != null ? (Number(data.services[i].price * Number(data.services[i].soLuong))) : 0) + '</td>';
                            html += '</tr>';
                            totalPrice += (data.services[i].price != null ? (Number(data.services[i].price * Number(data.services[i].soLuong))) : 0);
                        }
                        html += '<tr>';
                        html += '<td></td>';
                        html += '<td colspan="3"><strong>Tổng chi phí (VNĐ)<strong></td>';
                        html += '<td>' + totalPrice + '</td>';
                        html += '</tr>';
                        $('.payment-detail-list').find('tbody').html(html);
                    }
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function paymentAction(){
    var id = $('#id-payment-patient').val();
    $.ajax({
        type: "POST",
        url: "/api/patient/getPaymentPatientChangeStatus?id=" + id,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Thanh toán',
                    subtitle: '',
                    body: 'Thanh toán!!!'
                });
                $('#modal-payment-detail').modal('toggle');
                actionSearchPatientPayment();
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Thanh toán',
                    subtitle: '',
                    body: response.message
                });
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function printPaymentAction(){
    var printContents = document.getElementById('content-print-payment').innerHTML;
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
     location.reload();
}