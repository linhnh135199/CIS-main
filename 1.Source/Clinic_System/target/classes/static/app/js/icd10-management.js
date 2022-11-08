$( document ).ready(function() {
var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = $('.key-search-icd').val();
    getICDByPage(dataSearch);
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

function getICDByPage(dataSearch){
    $.ajax({
        type: "GET",
        url: "/api/catalogIcd/getDataICD" ,
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-icd').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html = '';
                var totalRecord = response.data.total;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageICD(dataSearch.page, totalRecord);
                }
                var data = response.data.listICD;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td>' + (i+1) + '</td>';
                        html += '<td>' + data[i].chapter + '</td>';
                        html += '<td>' + data[i].chapterCode + '</td>';
                        html += '<td>' + data[i].chapterName + '</td>';
                        html += '<td>' + data[i].groupNumber + '</td>';
                        html += '<td>' + data[i].groupName + '</td>';
                        html += '<td>' + data[i].type + '</td>';
                        html += '<td>' + data[i].diseaseCode + '</td>';
                        html += '<td>' + data[i].diseaseName + '</td>';
                        html += '<td>' + data[i].reportCode + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-add-edit-icd" onclick="editICD(' + data[i].id
                        + ')"' + '><span class="bi bi-pencil"></span></button>';
                        html += '<button type="button" class="btn btn-block bg-gradient-danger btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-confirm-delete-catalog" onclick="deleteICD(' + data[i].id
                        + ')"' + '><span class="bi bi-trash"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-icd').find('tbody').html(html);
                eventPagingICD();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function actionSearchICD(){
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = $('.key-search-icd').val();
    getICDByPage(dataSearch);
}

function setDataPageICD(currentPage, totalRecord){
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

function eventPagingICD(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-icd').val();
        getICDByPage(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.key = $('.key-search-icd').val();
        getICDByPage(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.key = $('.key-search-icd').val();
        getICDByPage(dataSearch);
    });
}

function cleanData(){
    $('#id-icd').val('');
    $('#chapter').val('');
    $('#chapter-code').val('');
    $('#chapter-name').val('');
    $('#group-number').val('');
    $('#group-name').val('');
    $('#type').val('');
    $('#disease_code').val('');
    $('#disease_name').val('');
    $('#report_code').val('');
}

function editICD(id){
    cleanData();
    $.ajax({
        type: "GET",
        url: "/api/catalogIcd/disease_list_icd10/getDataById?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                var html = '';
                var data = response.data;
                if(data != null || data != undefined){
                    $('#id-icd').val(data.id);
                    $('#chapter').val(data.chapter);
                    $('#chapter-code').val(data.chapterCode);
                    $('#chapter-name').val(data.chapterName);
                    $('#group-number').val(data.groupNumber);
                    $('#group-name').val(data.groupName);
                    $('#type').val(data.type);
                    $('#disease_code').val(data.diseaseCode);
                    $('#disease_name').val(data.diseaseName);
                    $('#report_code').val(data.reportCode);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function addEditICDAction(){
    if(validateICD()){
        var dataSearch = {};
        dataSearch.id = $('#id-icd').val();
        dataSearch.chapter = $('#chapter').val();
        dataSearch.chapterCode = $('#chapter-code').val();
        dataSearch.chapterName = $('#chapter-name').val();
        dataSearch.groupNumber = $('#group-number').val();
        dataSearch.groupName = $('#group-name').val();
        dataSearch.type = $('#type').val();
        dataSearch.diseaseCode = $('#disease_code').val();
        dataSearch.diseaseName = $('#disease_name').val();
        dataSearch.reportCode = $('#report_code').val();
        dataSearch.role = location.pathname;
        $.ajax({
            type: "POST",
            url: "/api/catalogIcd/addEditICD" ,
            dataType: 'json',
            data: dataSearch,
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    if($('#id-icd').val() != undefined && $('#id-icd').val() != null && $('#id-icd').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Edit ICD',
                            subtitle: '',
                            body: 'Edit ICD successfully.'
                        });
                    }else{
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Add ICD',
                            subtitle: '',
                            body: 'Add ICD successfully.'
                        });
                    }
                    cleanData();
                    location.reload();
                }else{
                    if($('#id-icd').val() != undefined && $('#id-icd').val() != null && $('#id-icd').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-danger',
                            title: 'Edit ICD',
                            subtitle: '',
                            body: response.message
                        });
                    }else{
                         $(document).Toasts('create', {
                             class: 'bg-danger',
                             title: 'Add ICD',
                             subtitle: '',
                             body: response.message
                         });
                     }
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function validateICD(){
    $('.error-user').remove();
    var check = true;
    if($('#chapter').val().trim() == ''){
        $('#chapter').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Điện thoại không để trống</p>' ) );
        check = false;
    }
    if($('#chapter-code').val().trim() == ''){
        $('#chapter-code').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Họ và tên không để trống</p>' ) );
        check = false;
    }
    if($('#chapter-name').val() == null){
        $('#chapter-name').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
        check = false;
    }
    if($('#group-number').val() == null){
        $('#group-number').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
        check = false;
    }
    if($('#group-name').val() == null){
            $('#group-name').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
            check = false;
    }
    if($('#type').val() == null){
            $('#type').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
            check = false;
    }
    if($('#disease_code').val() == null){
            $('#disease_code').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
            check = false;
    }
    if($('#disease_name').val() == null){
            $('#disease_name').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
            check = false;
    }
    if($('#report_code').val() == null){
            $('#report_code').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
            check = false;
    }
//    if($('#id-icd').val() == undefined || $('#id-icd').val() == null || $('#id-icd').val() == ''){
//                $('#id-icd').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Id k đc trống</p>' ) );
//                check = false;
//        }
    return check;
}

function deleteICD(id){
    $('#id-catalog-delete').val(id);
}

function deleteCatalogAction(){
    var id = $('#id-catalog-delete').val();
    $.ajax({
        type: "DELETE",
        url: "/api/catalogIcd/deleteICD?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Delete Icd10',
                    subtitle: '',
                    body: 'Delete Icd10 successfully.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Delete Icd10',
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