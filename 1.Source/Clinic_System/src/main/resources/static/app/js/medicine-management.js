$( document ).ready(function() {
var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = "";
    getMedicineByPage(dataSearch);
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

function getMedicineByPage(dataSearch){
    console.log("key: ",dataSearch.key);
    var keyword = dataSearch.key
    $.ajax({
        type: "GET",
        url: '/api/catalogMedicine/getDataMedicine',
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-medicine').find('tbody').empty();
            if(response.responseCode == '2000'){
            console.log('response:::', response);
                var html = '';
                var totalRecord = response.data.totalItems;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageMedicine(dataSearch.page, totalRecord);
                }
                var data = response.data.items;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td>' + (i+1) + '</td>';
                        html += '<td>' + data[i].name + '</td>';
                        html += '<td>' + data[i].unit + '</td>';
                        html += '<td>' + data[i].routeName + '</td>';
                        html += '<td>' + data[i].genericName + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-add-edit-medicine" onclick="editMedicine(' + data[i].id
                        + ')"' + '><span class="bi bi-pencil"></span></button>';
                        html += '<button type="button" class="btn btn-block bg-gradient-danger btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-confirm-delete-catalog" onclick="deleteMedicine(' + data[i].id
                        + ')"' + '><span class="bi bi-trash"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-medicine').find('tbody').html(html);
                eventPagingMedicine();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}
function deleteMedicine(id){
    $('#id-catalog-delete').val(id);
}

function deleteCatalogAction(){
    var id = $('#id-catalog-delete').val();
    $.ajax({
        type: "DELETE",
        url: "/api/catalogMedicine/delete/medicine?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Delete Medicine',
                    subtitle: '',
                    body: 'Xóa thành công.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Delete Medicine',
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
function actionSearchMedicine(){
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.key = $('#text-search').val();
    getMedicineByPage(dataSearch);
}

function setDataPageMedicine(currentPage, totalRecord){
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

function eventPagingMedicine(){
    $('.previous-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) - 1;
        dataSearch.role = location.pathname;
        dataSearch.key = $('.key-search-medicine').val();
        getMedicineByPage(dataSearch);
    });
    $('.next-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val()) + 1;
        dataSearch.key = $('.key-search-medicine').val();
        getMedicineByPage(dataSearch);
    });
    $('.go-to-page').unbind().click(function(){
        var dataSearch = {};
        dataSearch.page = Number($('.current-page').val());
        dataSearch.key = $('.key-search-medicine').val();
        getMedicineByPage(dataSearch);
    });
}

function addEditMedicineAction(){
    if(validateMedicine()){
        var dataSearch = {};
        dataSearch.id = $('#id-medicine').val();
        dataSearch.soDangKy = $('#input-so-dang-ky').val();
        dataSearch.name = $('#input-ten-thuoc').val();
        dataSearch.hoatChat = $('#input-hoat-chat').val();
        dataSearch.hamLuong = $('#input-ham-luong').val();
        dataSearch.duongDung = $('#input-duong-dung').val();
        dataSearch.dongGoi = $('#imput-dong-goi').val();
        dataSearch.hangSX = $('#input-hang-sx').val();
        dataSearch.nuocSX = $('#input-nuoc-sx').val();
        dataSearch.price = $('#input-price').val();
        dataSearch.maHoatChat = $('#input-ma-hoat-chat').val();
        dataSearch.maDuongDung = $('#input-ma-duong-dung').val();
        $.ajax({
            type: "POST",
            url: "/api/catalogMedicine/addEditMedicine" ,
            dataType: 'json',
            data: dataSearch,
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    if($('#id-medicine').val() != undefined && $('#id-medicine').val() != null && $('#id-medicine').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Edit Medicine',
                            subtitle: '',
                            body: 'Cập nhật thành công.'
                        });
                    }else{
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Add Medicine',
                            subtitle: '',
                            body: 'Thêm thành công.'
                        });
                    }
                    cleanData();
                    location.reload();
                }else{
                    if($('#id-medicine').val() != undefined && $('#id-medicine').val() != null && $('#id-medicine').val() != ''){
                        $(document).Toasts('error', {
                            class: 'bg-success',
                            title: 'Edit Medicine',
                            subtitle: '',
                            body: 'Lỗi!!!'
                        });
                    }else{
                        $(document).Toasts('error', {
                            class: 'bg-success',
                            title: 'Add Medicine',
                            subtitle: '',
                            body: 'Lỗi!!!'
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

function validateMedicine(){
    var check = true;

    return check;
}

function cleanData(){
    $('#id-medicine').val('');
    $('#input-so-dang-ky').val('');
    $('#input-ten-thuoc').val('');
    $('#input-hoat-chat').val('');
    $('#input-ham-luong').val('');
    $('#input-duong-dung').val('');
    $('#imput-dong-goi').val('');
    $('#input-hang-sx').val('');
    $('#input-nuoc-sx').val('');
    $('#input-price').val('');
    $('#input-ma-hoat-chat').val('');
    $('#input-ma-duong-dung').val('');
}

function editMedicine(id){
    $.ajax({
        type: "GET",
        url: "/api/catalogMedicine/getDataMedicineById?id=" + id,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $('#id-medicine').val(response.data.id);
                $('#input-so-dang-ky').val(response.data.id);
                $('#input-ten-thuoc').val(response.data.name);
                $('#input-hoat-chat').val(response.data.hoatChat);
                $('#input-ham-luong').val(response.data.hamLuong);
                $('#input-duong-dung').val(response.data.duongDung);
                $('#imput-dong-goi').val(response.data.dongGoi);
                $('#input-hang-sx').val(response.data.hangSX);
                $('#input-nuoc-sx').val(response.data.nuocSX);
                $('#input-price').val(response.data.price);
                $('#input-ma-hoat-chat').val(response.data.maHoatChat);
                $('#input-ma-duong-dung').val(response.data.maDuongDung);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}