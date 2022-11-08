function getDonThuocPatient(){
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    if(id != null){
        $.ajax({
            type: "GET",
            url: "/api/prescription/getPrescriptionOfPatientById?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                $('.data-table-prescription').find('tbody').empty();
                if(response.responseCode == '00000'){
                    var html = '';
                    var data = response.data;
                    if(data != null || data != undefined){
                        for(var i = 0; i < data.length; i++){
                            html += '<tr>';
                            html += '<td class="stt">' + (i+1) + '</td>';
                            html += '<td style="display:none" class="prescription-add-id">' + data[i].id + '</td>';
                            html += '<td>' + data[i].name + '</td>';
                            html += '<td>' + (data[i].hoatChat + ' ' + data[i].hamLuong) + '</td>';
                            html += '<td class="huong-dan-prescription">' + (data[i].huongDan != undefined ? data[i].huongDan : '') + '</td>';
                            html += '<td>' + (data[i].duongDung != undefined ? data[i].duongDung : '') + '</td>';
                            html += '<td class="don-vi-prescription">' + (data[i].donVi != undefined ? data[i].donVi : '') + '</td>';
                            html += '<td class="so-luong-prescription">' + (data[i].soLuong != undefined ? data[i].soLuong : '') + '</td>';
                            html += '<td><a href="#" class="btn btn-danger delete-prescription"><i class="fas fa-trash"></i></a>';
                            html += '</td>';
                            html += '</tr>';
                        }
                    }
                    $('.data-table-prescription').find('tbody').html(html);
                    eventDeletePrescription();
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
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

function resetPrescription(){
    $('.data-table-prescription').find('tbody').empty();
}

function printPrescription(){
    $('#search-name-prescription').parent().parent().parent().hide();
    $('.name-prescription-text').parent().parent().parent().parent().hide();
    $('.hoat-chat-prescription-text').parent().parent().parent().parent().hide();
    $('.ham-luong-prescription-text').parent().parent().parent().parent().hide();
    var printContents = document.getElementById('view-print-don-thuoc').innerHTML;
    var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
     $('#search-name-prescription').parent().parent().parent().show();
     $('.name-prescription-text').parent().parent().parent().parent().show();
     $('.hoat-chat-prescription-text').parent().parent().parent().parent().show();
     $('.ham-luong-prescription-text').parent().parent().parent().parent().show();
}

function addPrescriptionToList(){
    var id = $('#search-name-prescription-value').val();
    if(id == null || id == undefined || id == ''){
        alert('Bạn cần chọn thuốc trước khi thêm');
    }else{
        $('.data-table-prescription').find('.prescription-add-id').each(function(){
            if(Number($(this).text()) == Number($("#search-name-prescription-value").val())){
                $(this).parent().remove();
            }
        });
        var html = '';
        html += '<tr>';
        html += '<td class="stt"></td>';
        html += '<td style="display:none" class="prescription-add-id">' + $("#search-name-prescription-value").val() + '</td>';
        html += '<td>' + $(".name-prescription-text").val() + '</td>';
        html += '<td>' + ($(".hoat-chat-prescription-text").val() + ' ' + $(".ham-luong-prescription-text").val()) + '</td>';
        html += '<td class="huong-dan-prescription">' + $(".huong-dan-prescription-text").val() + '</td>';
        html += '<td>' + $(".duong-dung--prescription-text").val() + '</td>';
        html += '<td class="don-vi-prescription">' + $(".don-vi-prescription-text").val() + '</td>';
        html += '<td class="so-luong-prescription">' + $(".so-luong-prescription-text").val() + '</td>';
        html += '<td><a href="#" class="btn btn-danger delete-prescription"><i class="fas fa-trash"></i></a>';
        html += '</td>';
        html += '</tr>';
        $('.data-table-prescription').find('tbody').append(html);
        eventDeletePrescription();
        var stt = 1;
        $('.data-table-prescription').find('.prescription-add-id').each(function(){
            $(this).parent().find('.stt').text(stt);
            stt++;
        });
    }
}

function savePrescription(){
    var data = {};
    var ids = '';
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    var prescriptionReqList = [];
    $('.data-table-prescription').find('.prescription-add-id').each(function(){
        var prescriptionReq = {};
        prescriptionReq.id = $(this).text();
        prescriptionReq.huongDan = $(this).parent().find('.huong-dan-prescription').text();
        prescriptionReq.donVi = $(this).parent().find('.don-vi-prescription').text();
        prescriptionReq.soLuong = $(this).parent().find('.so-luong-prescription').text();
        prescriptionReqList.push(prescriptionReq);
    });
    data.prescriptionReqList = prescriptionReqList;
    data.idPatient = id;
    if(id != ''){
        $.ajax({
            type: "POST",
            url: "/api/prescription/updatePrescriptionToPatient",
            dataType: 'json',
            data: JSON.stringify(data),
            headers: {
            "token": localStorage.getItem('token'),
            "userid": localStorage.getItem('userid'),
            'Content-Type': 'application/json'
            },
            success: function (response) {
                if(response.responseCode == '00000'){
                    $(document).Toasts('create', {
                        class: 'bg-success',
                        title: 'Update',
                        subtitle: '',
                        body: 'Update successfully.'
                    });
                    cleanDataPatient();
                    getPatientDetail();
                }else{
                    $(document).Toasts('create', {
                        class: 'bg-danger',
                        title: 'Update',
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
}

function eventDeletePrescription(){
    $('.delete-prescription').unbind().click(function(){
        $(this).parent().parent().remove();
    });
}

function chosePhacDoPrescription(){
    $.ajax({
        type: "GET",
        url: "/api/regimen/getRegimenAllPrescription",
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                var html = '';
                var data = response.data;
                if(data != null || data != undefined){
                    var html = '';
                   for(var i = 0; i < response.data.length; i++){
                       html += '<option value="' + response.data[i].id + '">' + response.data[i].name + '</option>';
                   }
                   $('#phac-do-prescrition-add').html(html);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function addPhacDopRescritionAction(){
    var id = $('#phac-do-prescrition-add').val();
    if(id != ''){
        $.ajax({
            type: "GET",
            url: "/api/prescription/getPrescriptionByRegimen?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){

                    var data = response.data;
                    if(data != null || data != undefined){
                        for(var i = 0; i < data.length; i++){
                            $('.data-table-prescription').find('.prescription-add-id').each(function(){
                                if(Number($(this).text()) == Number(data[i].id)){
                                    $(this).parent().remove();
                                }
                            });
                            var html = '';
                            html += '<tr>';
                            html += '<td class="stt"></td>';
                            html += '<td style="display:none" class="prescription-add-id">' + data[i].id + '</td>';
                            html += '<td>' + data[i].name + '</td>';
                            html += '<td>' + (data[i].hoatChat + ' ' + data[i].hamLuong) + '</td>';
                            html += '<td class="huong-dan-prescription">' + (data[i].huongDan != undefined ? data[i].huongDan : '') + '</td>';
                            html += '<td>' + (data[i].duongDung != undefined ? data[i].duongDung : '') + '</td>';
                            html += '<td class="don-vi-prescription">' + (data[i].donVi != undefined ? data[i].donVi : '') + '</td>';
                            html += '<td class="so-luong-prescription">' + (data[i].soLuong != undefined ? data[i].soLuong : '') + '</td>';
                            html += '<td><a href="#" class="btn btn-danger delete-prescription"><i class="fas fa-trash"></i></a>';
                            html += '</td>';
                            html += '</tr>';
                            $('.data-table-prescription').find('tbody').append(html);
                            eventDeletePrescription();
                            var stt = 1;
                            $('.data-table-prescription').find('.prescription-add-id').each(function(){
                                $(this).parent().find('.stt').text(stt);
                                stt++;
                            });
                        }
                    }
                }
                $('#modal-add-phac-do-prescrition').modal('toggle');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}