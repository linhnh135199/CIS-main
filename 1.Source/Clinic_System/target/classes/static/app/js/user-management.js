
$(function () {
    $('[data-mask]').inputmask()
    $('#birthday-user').datetimepicker({
        format: 'L'
    });
  })

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
    console.log( "user management" );
    if(location.href.split('/update-profile-user').length > 1){
        editUserLogin();
    }else{
        var dataSearch = {};
        dataSearch.page = 1;
        dataSearch.role = location.pathname;
        dataSearch.key = '';
        getUserByPage(dataSearch);
    }

    $('#birthday-user').datetimepicker({
        format: 'L'
    });
});

    var today = new Date();

function getUserByPage(dataSearch){
    $.ajax({
        type: "GET",
        url: "/api/user/getData" ,
        dataType: 'json',
        data: dataSearch,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-user').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html = '';
                var totalRecord = response.data.total;
                if(totalRecord != null && totalRecord != undefined){
                    setDataPageUser(dataSearch.page, totalRecord);
                }
                var data = response.data.listUser;
                if(data != null || data != undefined){
                    for(var i = 0; i < data.length; i++){
                        html += '<tr>';
                        html += '<td>' + (i+1) + '</td>';
                        html += '<td>' + data[i].id + '</td>';
                        html += '<td>' + data[i].name + '</td>';
                        html += '<td>' + (data[i].email == null ? '' : data[i].email) + '</td>';
                        html += '<td>' + data[i].phoneNumber + '</td>';
                        html += '<td>' + getFormattedDate(data[i].birthday) + '</td>';
                        html += '<td>' + data[i].gender + '</td>';
                        html += '<td><button type="button" class="btn btn-block bg-gradient-warning btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-add-edit-user" onclick="editUser(' + data[i].id
                        + ')"' + '><span class="bi bi-pencil"></span></button>';
                        html += '<button type="button" class="btn btn-block bg-gradient-danger btn-sm" ';
                        html += 'data-toggle="modal" data-target="#modal-confirm-delete-user" onclick="deleteUser(' + data[i].id
                        + ')"' + '><span class="bi bi-trash"></span></button>';
                        html += '</td>';
                        html += '</tr>';
                    }
                }
                $('.data-table-user').find('tbody').html(html);
                eventPagingUser();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function editUser(id){
    cleanData();
    if(location.href.split('/user/patient-management').length == 1){
        $('#pass-user').parent().show();
        $('#repass-user').parent().show();
    }
    $.ajax({
        type: "GET",
        url: "/api/user/getDataById?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                var html = '';
                var data = response.data;
                if(data != null || data != undefined){
                    $('#id-user').val(data.id);
                    $('#phone-user').val(data.phoneNumber);
                    $('#full-name-user').val(data.name);
                    $('#sex-user').val(data.gender);
                    $('#birthday-user').val(getFormattedDate(data.birthday));
                    $('#email-user').val(data.email);
                    $('#address-user').val(data.address);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function editUserLogin(){
    cleanData();
    $.ajax({
        type: "GET",
        url: "/api/user/getDataUserLogin" ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                var html = '';
                var data = response.data;
                if(data != null || data != undefined){
                    $('#id-user').val(data.id);
                    $('#phone-user').val(data.phoneNumber);
                    $('#full-name-user').val(data.name);
                    $('#sex-user').val(data.gender);
                    $('#birthday-user').val(getFormattedDate(data.birthday));
                    $('#address-user').val(data.address);
                    $('#email-user').val(data.email);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function deleteUser(id){
    $('#id-user-delete').val(id);
}

function cleanData(){
    $('.error-user').remove();
    $('#id-user').val('');
    $('#phone-user').val('');
    $('#full-name-user').val('');
    $('#sex-user').val('');
    $('#birthday-user').val('');
    $('#address-user').val('');
    $('#pass-user').val('');
    $('#repass-user').val('');
    $('#email-user').val('');
}

function deleteUserAction(){
    var id = $('#id-user-delete').val();
    $.ajax({
        type: "DELETE",
        url: "/api/user/deleteUser?id=" + id ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Delete User',
                    subtitle: '',
                    body: 'Delete User successfully.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Delete User',
                    subtitle: '',
                    body: response.message
                });
            }
            $('#id-user-delete').val('');
            location.reload();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}

function addEditUserAction(){
    if(validateUser()){
        var dataSearch = {};
        dataSearch.id = $('#id-user').val();
        dataSearch.phone = $('#phone-user').val();
        dataSearch.fullName = $('#full-name-user').val();
        dataSearch.gender = $('#sex-user').val();
        dataSearch.birthOfDate = $('#birthday-user').val();
        dataSearch.address = $('#address-user').val();
        dataSearch.email = $('#email-user').val();
        dataSearch.password = $('#pass-user').val();
        dataSearch.role = location.pathname;
        $.ajax({
            type: "POST",
            url: "/api/user/addEditUser" ,
            dataType: 'json',
            data: dataSearch,
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    if($('#id-user').val() != undefined && $('#id-user').val() != null && $('#id-user').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Edit User',
                            subtitle: '',
                            body: 'Edit User successfully.'
                        });
                    }else{
                        $(document).Toasts('create', {
                            class: 'bg-success',
                            title: 'Add User',
                            subtitle: '',
                            body: 'Add User successfully.'
                        });
                    }
                    cleanData();
                    location.reload();
                }else{
                    if($('#id-user').val() != undefined && $('#id-user').val() != null && $('#id-user').val() != ''){
                        $(document).Toasts('create', {
                            class: 'bg-danger',
                            title: 'Edit User',
                            subtitle: '',
                            body: response.message
                        });
                    }else{
                         $(document).Toasts('create', {
                             class: 'bg-danger',
                             title: 'Add User',
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

function validateUser(){
    $('.error-user').remove();
    var check = true;
    var nameRegex = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s\W|_]{2,}$/;
    var phoneRegex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
    var passwordRegex = /([a-zA-Z0-9]{8,})/g;
    var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var today = new Date();
    if($('#phone-user').val().trim() !== ''){
        if(phoneRegex.test($('#phone-user').val()) == false){
            $('#phone-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Số điện thoại không đúng định dạng</p>' ) );
            check = false;
        }
    }else{
        $('#phone-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Số điện thoại không được để trống</p>' ) );
        check = false;
    }
    if($('#full-name-user').val().trim() !== ''){
        if(nameRegex.test($('#full-name-user').val()) == false){
            $('#full-name-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Tên không đúng định dạng</p>' ) );
            check = false;
            }
    }else{
        $('#full-name-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Họ và tên không để trống</p>' ) );
            check = false;
    }
    if($('#sex-user').val() == null){
        $('#sex-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Giới tính không để trống</p>' ) );
        check = false;
    }
//    if($('#email-user').val().trim() !== ''){
//        if(emailRegex.test($('#email-user').val()) == false){
//            $('#email-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Email không đúng định dạng</p>' ) );
//            check = false;
//        }
//    }else{
//        $('#email-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Email không được để trống</p>' ) );
//        check = false;
//    }
    if($('#birthday-user').val().trim() == ''){
        $('#birthday-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Ngày sinh không để trống</p>' ) );
        check = false;
    }else{
        if( getFormattedDate1($('#birthday-user').val().trim()) > getFormattedDate1(today)){
             $('#birthday-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Ngày sinh không được lớn hơn ngày hiện tại</p>' ) );
             check = false;
        }
        if(isNaN($('#birthday-user').val().replaceAll('/',''))){
            $('#birthday-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Ngày sinh không để trống</p>' ) );
            check = false;
        }
    }

    if(location.href.split('/user/patient-management').length == 1){
        if($('#id-user').val() == undefined || $('#id-user').val() == null || $('#id-user').val() == ''){
            if($('#pass-user').val().trim() !== ''){
                if(passwordRegex.test($('#pass-user').val()) == false){
                    $('#pass-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Mật khẩu phải có ít nhất 8 kí tự</p>' ) );
                        check = false;
                }
            }else{
                $('#pass-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Mật khẩu không để trống</p>' ) );
                check = false;
            }
            if($('#repass-user').val().trim() == ''){
                $('#repass-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Nhập lại mật khẩu không để trống</p>' ) );
                check = false;
            }
            if($('#pass-user').val().trim() != '' && $('#repass-user').val().trim() != '' && $('#pass-user').val().trim() != $('#repass-user').val().trim()){
                $('#repass-user').parent().after( $( '<p class="error-user" style="color: red;font-size: 14px;">Mật khẩu không khớp</p>' ) );
                check = false;
            }
        }
    }
    return check;
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

function getFormattedDate1(dateInput) {
  var date = new Date(dateInput);
  var year = date.getFullYear();
  var month = (1 + date.getMonth()).toString();
  month = month.length > 1 ? month : '0' + month;
  var day = date.getDate().toString();
  day = day.length > 1 ? day : '0' + day;
  return year + '/' + month + '/' + day;
}

function actionSearchUser(){
    var dataSearch = {};
    dataSearch.page = 1;
    dataSearch.role = location.pathname;
    dataSearch.key = $('.key-search-user').val();
    getUserByPage(dataSearch);
}

function setDataPageUser(currentPage, totalRecord){
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

function eventPagingUser(){
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

function addUser(){
    if(location.href.split('/user/patient-management').length == 1){
        $('#pass-user').parent().show();
        $('#repass-user').parent().show();
    }
}