function getServiceByPage(dataSearch){
    console.log("key: ",dataSearch.key);
    var keyword = dataSearch.key
    $.ajax({
        type: "GET",
        url: '/api/catalogService/getDataService' ,
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-service').find('tbody').empty();
            if(response.responseCode == '2000'){
            console.log('response:::', response);
                var html = '';
                var totalRecord = response.data.totalItems;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageService(dataSearch.page, totalRecord);
                }
                var data = response.data.items;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td >' + (i+1) + '</td>';
                        html += '<td style="display: none;" class="specializedId-view">' + data[i].groupId + '</td>';
                        html += '<td style="display: none;" class="id-view">' + data[i].id + '</td>';
                        html += '<td class="specialized-view">' + data[i].groupName + '</td>';
                        html += '<td class="name-view">' + data[i].serviceName + '</td>';
                        html += '<td class="id-code">' + data[i].code + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm edit-Service-action" ';
                        html += 'data-toggle="modal" data-target="#modal-add-edit-service" ' + '><span class="bi bi-pencil"></span></button>';
                        html += '<button type="button" class="btn btn-block bg-gradient-danger btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-confirm-delete-catalog" onclick="deleteService(' + data[i].id
                        + ')"' + '><span class="bi bi-trash"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-service').find('tbody').html(html);
                editService();
                eventPagingService();
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

$( document ).ready(function() {
var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = "";
    getServiceByPage(dataSearch);
    });


function actionSearchService(){
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = $('.key-search-service').val();
    getServiceByPage(dataSearch);
}

function setDataPageService(currentPage, totalRecord){
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

function eventPagingService(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.key = $('.key-search-service').val();
        getServiceByPage(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.key = $('.key-search-service').val();
        getServiceByPage(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.key = $('.key-search-service').val();
        getServiceByPage(dataSearch);
    });
}
function deleteService(id){
    $('#id-catalog-delete').val(id);
}

function deleteCatalogAction(){
    var id = $('#id-catalog-delete').val();
    $.ajax({
        type: "DELETE",
        url: "/api/catalogService/delete/service?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Delete Technical Service',
                    subtitle: '',
                    body: 'Đã xóa.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Lỗi!!!',
                    subtitle: '',
                    body: response.message
                });
            }
            $('#id-catalog-delete').val('');
            location.reload();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function editService(){
    $('.edit-Service-action').click(function(){
        loadAllSpecializedById($(this).parent().parent().find('.specializedId-view').text());
        $('#id-service-input').val($(this).parent().parent().find('.id-view').text());
        $('#service-input').val($(this).parent().parent().find('.name-view').text());
        $('#service-code-input').val($(this).parent().parent().find('.id-code').text());
    })
}

function loadAllSpecialized(){
    $.ajax({
       url: "/specialized/getAllSpecialized",
       dataType: "json",
       success: function( response ) {
           if(response.responseCode == "00000"){
               var html = '';
               for(var i = 0; i < response.data.length; i++){
                   html += '<option value="' + response.data[i].id + '">' + response.data[i].groupName + '</option>';
               }
               $('#specialized-input').html(html);
           }
       }
    });
}

function loadAllSpecializedById(id){
    $.ajax({
       url: "/specialized/getAllSpecialized",
       dataType: "json",
       success: function( response ) {
           if(response.responseCode == "00000"){
               var html = '';
               for(var i = 0; i < response.data.length; i++){
                   if(Number(id) == Number(response.data[i].id)){
                        html += '<option value="' + response.data[i].id + '" selected>' + response.data[i].groupName + '</option>';
                   }else{
                        html += '<option value="' + response.data[i].id + '">' + response.data[i].groupName + '</option>';
                   }
               }
               $('#specialized-input').html(html);
           }
       }
    });
}

function addEditServiceAction(){
    if(validateService()){
        var dataSearch = {};
        dataSearch.id = $('#id-service-input').val();
        dataSearch.specializedId = $('#specialized-input').val();
        dataSearch.name = $('#service-input').val();
        dataSearch.code = $('#service-code-input').val();
        $.ajax({
            type: "POST",
            url: "/api/catalogService/addEditService" ,
            dataType: 'json',
            data: dataSearch,
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    if($('#id-service-input').val() != undefined && $('#id-service-input').val() != null && $('#id-service-input').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Edit service',
                            subtitle: '',
                            body: 'Sửa thành công.'
                        });
                    }else{
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Add service',
                            subtitle: '',
                            body: 'Thêm thành công.'
                        });
                    }
                    cleanData();
                    location.reload();
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function validateService(){
    var check = true;

    return check;
}
function cleanData(){
    $('#id-service-input').val('');
    $('#specialized-input').val('');
    $('#service-input').val('');
}