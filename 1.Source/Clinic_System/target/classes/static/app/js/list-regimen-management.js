$( document ).ready(function() {
    $("#search-name-specialized").autocomplete({
      source :function( request, response ) {
        $.ajax({
           url: "/specialized/getDataSearch",
           dataType: "json",
           data: {
              key: request.term
           },
           success: function( data ) {
               $("#search-name-specialized").val('');
               $("#search-name-specialized-value").val('');
               $("#search-name-service").val('');
               $("#search-name-service-value").val('');
               if(data.responseCode == "00000"){
                   response($.map(data.data, function(item) {
                        return {
                            label : item.groupName,
                            value : item.id
                        };
                  }));
               }
           }
        });
       },
       select: function (event, ui) {
             $("#search-name-specialized").val(ui.item.label);
             $("#search-name-specialized-value").val(ui.item.value);
             $("#search-name-service").val('');
             $("#search-name-service-value").val('');
             if($('.service-specialized-id').first().text() != '' && $("#search-name-specialized-value").val() != '' &&
                 $('.service-specialized-id').first().text() != $("#search-name-specialized-value").val()){
                 $('.data-table-regimen-service').find('tbody').empty();
             }
           return false;
       },
       close: function() {
           if($("#search-name-specialized-value").val() == ''){
               $("#search-name-specialized").val('');
               $("#search-name-specialized-value").val('');
               $("#search-name-service").val('');
               $("#search-name-service-value").val('');
           }
       }
   });

   $("#search-name-service").autocomplete({
     source :function( request, response ) {
       $.ajax({
          url: "/api/service/getDataSearch",
          dataType: "json",
          headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
          data: {
             key: request.term,
             specializedId: $("#search-name-specialized-value").val()
          },
          success: function( data ) {
              $("#search-name-service-value").val('');
              if(data.responseCode == "00000"){
                  response($.map(data.data, function(item) {
                       return {
                           label : item.serviceName,
                           value : item.id
                       };
                 }));
              }
          }
       });
      },
      select: function (event, ui) {
            $("#search-name-service").val(ui.item.label);
            $("#search-name-service-value").val(ui.item.value);
          return false;
      },
      close: function() {
          if($("#search-name-service-value").val() == ''){
              $("#search-name-service").val('');
              $("#search-name-service-value").val('');
          }
      }
  });

  $("#search-name-prescription").autocomplete({
    source :function( request, response ) {
      $.ajax({
         url: "/medicine/getDataSearch",
         dataType: "json",
         data: {
            key: request.term
         },
         success: function( data ) {
              $("#search-name-prescription-value").val('');
              if(data.responseCode == "00000"){
                  response($.map(data.data, function(item) {
                       return {
                           label : item.name,
                           value : item.id
                       };
                 }));
              }
         }
      });
     },
     select: function (event, ui) {
           $("#search-name-prescription").val(ui.item.label);
           $("#search-name-prescription-value").val(ui.item.value);
           $.ajax({
              url: "/medicine/getDataById?id=" + $("#search-name-prescription-value").val(),
              dataType: "json",
              success: function( data ) {
                   if(data.responseCode == "00000"){
                       $("#search-name-prescription-value").val(data.data.id);
                        $(".name-prescription-text").val(data.data.name);
                        $(".hoat-chat-prescription-text").val(data.data.hoatChat);
                        $(".ham-luong-prescription-text").val(data.data.hamLuong);
                        $(".duong-dung--prescription-text").val(data.data.duongDung);
                        $(".don-vi-prescription-text").val(data.data.donVi);
                   }
              }
           });
           return false;
     },
     close: function() {
         if($("#search-name-prescription-value").val() == ''){
             $("#search-name-prescription").val('');
             $("#search-name-prescription-value").val('');
         }
     }
 });

  if(location.pathname == '/doctor/list-route-management'){
    searchRegimen();
  }

   if(location.pathname == '/doctor/edit-regimen-service'){
        var id = location.search.split('id=')[1];
        var type = 'service';
       $.ajax({
           type: "GET",
           url: "/api/regimen/getRegimenById?id=" + id + '&type=' + type,
           dataType: 'json',
           headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
           success: function (response) {
               $('.data-table-regimen-service').find('tbody').empty();
               if(response.responseCode == '00000'){
                   $('#search-name-specialized-value').val(response.data.specializedId);
                   $('#search-name-specialized').val(response.data.specialized);
                   $('#search-name-regimen').val(response.data.name);
                   $('.status-regimen').val(response.data.status);
                   var data = response.data.services;
                   var html = '';
                   if(data != null || data != undefined){
                       for(var i = 0; i < data.length; i++){
                           html += '<tr>';
                           html += '<td class="stt">' + (i+1) + '</td>';
                           html += '<td style="display:none" class="service-add-id">' + data[i].id + '</td>';
                           html += '<td class="service-specialized-id" style="display:none">' + response.data.specializedId + '</td>';
                           html += '<td class="service-add-specialized">' + response.data.specialized + '</td>';
                           html += '<td class="service-add-name">' + data[i].name + '</td>';
                           html += '<td><a href="#" class="btn btn-danger delete-service-y-lenh"><i class="fas fa-trash"></i></a>';
                           html += '</td>';
                           html += '</tr>';
                       }
                   }
                   $('.data-table-regimen-service').find('tbody').html(html);
                   eventDeleteService();
               }
           },
           error: function (xhr, ajaxOptions, thrownError) {
               console.log(thrownError);
           }
       });
   }

   if(location.pathname == '/doctor/edit-regimen-medicine'){
       var id = location.search.split('id=')[1];
       var type = 'medicine';
      $.ajax({
          type: "GET",
          url: "/api/regimen/getRegimenById?id=" + id + '&type=' + type,
          dataType: 'json',
          headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
          success: function (response) {
              $('.data-table-regimen-medicine').find('tbody').empty();
              if(response.responseCode == '00000'){
                  $('#search-name-specialized-value').val(response.data.specializedId);
                  $('#search-name-specialized').val(response.data.specialized);
                  $('#search-name-regimen').val(response.data.name);
                  $('.status-regimen').val(response.data.status);
                  var data = response.data.medicines;
                  var html = '';
                  if(data != null || data != undefined){
                      for(var i = 0; i < data.length; i++){
                          html += '<tr>';
                          html += '<td>' + (i+1) + '</td>';
                          html += '<td style="display:none" class="medicine-add-id">' + data[i].id + '</td>';
                          html += '<td>' + data[i].name + '</td>';
                          html += '<td>' + (data[i].hoatChat + ' ' + data[i].hamLuong) + '</td>';
                          html += '<td>' + (data[i].duongDung != undefined ? data[i].duongDung : '') + '</td>';
                          html += '<td>' + (data[i].donVi != undefined ? data[i].donVi : '') + '</td>';
                          html += '<td><a href="#" class="btn btn-danger delete-prescription"><i class="fas fa-trash"></i></a>';
                          html += '</td>';
                          html += '</tr>';
                          eventDeleteMedicine();
                      }
                  }
                  $('.data-table-regimen-medicine').find('tbody').html(html);
                  eventDeleteService();
              }
          },
          error: function (xhr, ajaxOptions, thrownError) {
              console.log(thrownError);
          }
      });
  }

});

function getListSpecialized(dataSearch){
    $.ajax({
        type: "GET",
        url: "/api/regimen/getRegimen" ,
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-regimen').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html = '';
                var totalRecord = response.data.total;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageRegimen(dataSearch.page, totalRecord);
                }
                var data = response.data.regimens;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr class="row-regimen-' + data[i].id + '" >';
                        html += '<td>' + (i+1) + '</td>';
                        html += '<td class="row-regimen-specializedId" style="display:none">' + data[i].specializedId + '</td>';
                        html += '<td class="row-regimen-specialized">' + data[i].specialized + '</td>';
                        html += '<td class="row-regimen-type" style="display:none">' + data[i].type + '</td>';
                        html += '<td class="row-regimen-name">' + data[i].name + '</td>';
                        html += '<td class="row-regimen-status">' + data[i].status + '</td>';
                        html += '<td>' + data[i].createUser + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += 'onclick="editRegimen(' + data[i].id + ',\'' + data[i].type + '\')"' + '><span class="bi bi-pencil"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-regimen').find('tbody').html(html);
                eventPagingRegimen();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
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

function searchRegimen(){
    var dataSearch = {};
   dataSearch.type = $('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text();
   dataSearch.specializedId = $("#search-name-specialized-value").val();
   dataSearch.name = $('#search-name-regimen').val();
   dataSearch.status = $('.status-regimen').val();
   dataSearch.page = 1;
   getListSpecialized(dataSearch);
}

function setDataPageRegimen(currentPage, totalRecord){
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

function eventPagingRegimen(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
           dataSearch.type = $('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text();
           dataSearch.specializedId = $("#search-name-specialized-value").val();
           dataSearch.name = $('#search-name-regimen').val();
           dataSearch.status = $('.status-regimen').val();
           dataSearch.page = Number($('.current-page').val()) - 1;
       getListSpecialized(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
           dataSearch.type = $('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text();
           dataSearch.specializedId = $("#search-name-specialized-value").val();
           dataSearch.name = $('#search-name-regimen').val();
           dataSearch.status = $('.status-regimen').val();
           dataSearch.page = Number($('.current-page').val()) + 1;
       getListSpecialized(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
           dataSearch.type = $('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text();
           dataSearch.specializedId = $("#search-name-specialized-value").val();
           dataSearch.name = $('#search-name-regimen').val();
           dataSearch.status = $('.status-regimen').val();
           dataSearch.page = Number($('.current-page').val());
       getListSpecialized(dataSearch);
    });
}

function createRegimen(){
    if($('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text() == 'Thuốc'){
        location.href = '/doctor/add-regimen-medicine';
    }
    if($('input[name="typeRegimen"]:checked').parent().find('.form-check-label').text() == 'Dịch vụ kỹ thuật'){
        location.href = '/doctor/add-regimen-service';
    }

}

function validateSave(){
    var check = true;
    var id = $('#search-name-specialized-value').val();
    var name = $('#search-name-regimen').val();
    var status = $('.status-regimen').val();
    if(id == null || id == undefined || id == ''){
        check = false;
        alert('Bạn cần chọn loại chuyên ngành trước');
    }
    if(name == null || name == undefined || name == ''){
        check = false;
        alert('Bạn cần điền tên trước khi');
    }
    if(status == null || status == undefined || status == ''){
        check = false;
        alert('Bạn cần chọn trạng thái trước');
    }
    return check;
}

function saveRegimen(data){
    $.ajax({
        type: "POST",
        url: "/api/regimen/addEditRegimen" ,
        dataType: 'json',
        data: data,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-regimen').find('tbody').empty();
            if(response.responseCode == '00000'){
                if($('#id-regimen').val() != undefined && $('#id-regimen').val() != null && $('#id-regimen').val() != ''){
                    $(document).Toasts('create', {
                        class: 'bg-success',
                        title: 'Edit',
                        subtitle: '',
                        body: 'Edit successfully.'
                    });
                    $('.save-regimen-button').hide();
                    location.reload();
                }else{
                    $(document).Toasts('create', {
                        class: 'bg-success',
                        title: 'Add',
                        subtitle: '',
                        body: 'Add successfully.'
                    });
                }
                location.href = '/doctor/list-route-management'
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function editRegimen(id, type){
//    $('#id-regimen').val(id);
//    $('#search-name-specialized-value').val($('.row-regimen-' + id).find('.row-regimen-specializedId').text());
//    $('#search-name-specialized').val($('.row-regimen-' + id).find('.row-regimen-specialized').text());
//    $('#search-name-regimen').val($('.row-regimen-' + id).find('.row-regimen-name').text());
//    $(".status-regimen option[data-value='" + $('.row-regimen-' + id).find('.row-regimen-status').text() +"']").attr("selected","selected");
//    $( ".form-check-label:contains('" + $('.row-regimen-' + id).find('.row-regimen-type').text() + "')").parent().find('.form-check-input').prop('checked',true);
//    $('.save-regimen-button').show();

    if(type == 'Thuốc'){
        location.href = '/doctor/edit-regimen-medicine?id=' +  id;
    }
    if(type == 'Dịch vụ kỹ thuật'){
        location.href = '/doctor/edit-regimen-service?id=' +  id;
    }
}

function saveEditRegimen(){
    if(validateSave()){
        var id = $('#search-name-specialized-value').val();
        var data = {};
        if(location.pathname == '/doctor/add-regimen-service' || location.pathname == '/doctor/edit-regimen-service' ){
            data.type = 'service';
            var serviceList = [];
            $('.data-table-regimen-service').find('.service-add-id').each(function(){
                serviceList.push(Number($(this).text()));
            });
            data.ids = serviceList;
        }else
        if(location.pathname == '/doctor/add-regimen-medicine' || location.pathname == '/doctor/edit-regimen-medicine' ){
            data.type = 'medicine';
            var medicineList = [];
            $('.data-table-regimen-medicine').find('.medicine-add-id').each(function(){
                medicineList.push(Number($(this).text()));
            });
            data.ids = medicineList;
        }
        data.specializedId = id;
        data.name = $('#search-name-regimen').val();
        data.status = $('.status-regimen').val();
        if(location.search.split('id=').length > 1){
            data.id = location.search.split('id=')[1];
        }
        saveRegimen(data);
    }
}

function resetRegimen(){
    $('.data-table-regimen-service').find('tbody').empty();
}

function addYLenh(){
    if($('#search-name-service-value').val() != ''){
        var html = '';
            html += '<tr>';
            html += '<td class="stt"></td>';
            html += '<td style="display:none" class="service-add-id">' + $('#search-name-service-value').val() + '</td>';
            html += '<td class="service-specialized-id" style="display: none;">' + $('#search-name-specialized-value').val() + '</td>';
            html += '<td class="service-add-specialized">' + $('#search-name-specialized').val() + '</td>';
            html += '<td class="service-add-name">' + $('#search-name-service').val() + '</td>';
            html += '<td><a href="#" class="btn btn-danger delete-service-y-lenh"><i class="fas fa-trash"></i></a>';
            html += '</td>';
            html += '</tr>';
            $('.data-table-regimen-service').find('tbody').append(html);
            eventDeleteService();
            var stt = 1;
            $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
                $(this).parent().find('.stt').text(stt);
                stt++;
            });
    }
}

function eventDeleteService(){
    $('.delete-service-y-lenh').unbind().click(function(){
        $(this).parent().parent().remove();
    });
}

function addMedicineToList(){
    var id = $('#search-name-prescription-value').val();
    if(id == null || id == undefined || id == ''){
        alert('Bạn cần chọn thuốc trước khi thêm');
    }else{
        var html = '';
        html += '<tr>';
        html += '<td class="medicine-add-id">' + $("#search-name-prescription-value").val() + '</td>';
        html += '<td>' + $(".name-prescription-text").val() + '</td>';
        html += '<td>' + ($(".hoat-chat-prescription-text").val() + ' ' + $(".ham-luong-prescription-text").val()) + '</td>';
        html += '<td>' + $(".duong-dung--prescription-text").val() + '</td>';
        html += '<td>' + $(".don-vi-prescription-text").val() + '</td>';
        html += '<td><a href="#" class="btn btn-danger delete-prescription"><i class="fas fa-trash"></i></a>';
        html += '</td>';
        html += '</tr>';
        $('.data-table-regimen-medicine').find('tbody').append(html);
        eventDeleteMedicine();
    }
}

function eventDeleteMedicine(){
    $('.delete-prescription').unbind().click(function(){
        $(this).parent().parent().remove();
    });
}

function resetRegimenMedicine(){
     $('.data-table-regimen-medicine').find('tbody').empty();
}
