function getYLenhPatient(){
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    if(id != null){
        $.ajax({
            type: "GET",
            url: "/api/service/getServiceById?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                $('.data-table-service-y-lenh').find('tbody').empty();
                if(response.responseCode == '00000'){
                    var html = '';
                    var data = response.data;
                    if(data != null || data != undefined){
                        for(var i = 0; i < data.length; i++){
                            html += '<tr>';
                            html += '<td class="stt">' + (i+1) + '</td>';
                            html += '<td style="display:none" class="service-add-id">' + data[i].id + '</td>';
                            html += '<td class="service-add-type">' + data[i].specialized + '</td>';
                            html += '<td class="service-add-name">' + data[i].name + '</td>';
                            html += '<td><a href="#" class="btn btn-danger delete-service-y-lenh"><i class="fas fa-trash"></i></a>';
                            html += '</td>';
                            html += '</tr>';
                        }
                    }
                    $('.data-table-service-y-lenh').find('tbody').html(html);
                    eventDeleteService();
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function getYLenhByChuyenNganh(){
    var id = $('#search-name-specialized-value').val();
    if(id == null || id == undefined || id == ''){
        alert('Bạn cần chọn loại chuyên ngành trước khi tìm kiếm');
    }else{
        $.ajax({
            type: "GET",
            url: "/api/service/getServiceByChuyenNganhId?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                $('.data-table-service-add').find('tbody').empty();
                $('.modal-add-server-title').text('Y lệnh');
                $('.modal-label-service').text('Tên chuyên khoa: ' + $('#search-name-specialized').val());
                $('.row-table-service-add').text('Tên dịch vụ kỹ thuật');
                if(response.responseCode == '00000'){
                    var data = response.data;
                    if(data != null || data != undefined){
                        var html = '';
                        for(var i = 0; i < data.length; i++){
                            html += '<tr>';
                            html += '<td class="stt">' + (i+1) + '</td>';
                            html += '<td style="display:none" class="service-add-id" style="display:none">' + data[i].id + '</td>';
                            html += '<td class="service-add-type" style="display:none">' + data[i].specialized + '</td>';
                            html += '<td class="service-add-name">' + data[i].name + '</td>';
                            html += '<td><input type="checkbox" class="checkbox-add-y-lenh">';
                            html += '</td>';
                            html += '</tr>';
                        }
                        $('.data-table-service-add').find('tbody').html(html);
                    }
                    eventDeleteService();
                    $('#modal-add-service').modal('toggle');
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function getPhacDoByChuyenNganh(){
    var id = $('#search-name-specialized-value').val();
    if(id == null || id == undefined || id == ''){
        alert('Bạn cần chọn loại chuyên ngành trước khi tìm kiếm');
    }else{
        $.ajax({
            type: "GET",
            url: "/api/service/getPhacDoByChuyenNganhId?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                $('.data-table-service-add').find('tbody').empty();
                $('.modal-add-server-title').text('Phác đồ');
                $('.modal-label-service').text('Tên chuyên khoa: ' + $('#search-name-specialized').val());
                $('.row-table-service-add').text('Tên phác đồ dịch vụ kĩ thuật');
                if(response.responseCode == '00000'){
                    var data = response.data;
                    var html = '';
                    if(data != null || data != undefined){
                        for(var i = 0; i < data.length; i++){
                            html += '<tr>';
                            html += '<td class="service-add-id" style="display:none">' + data[i].id + '</td>';
                            html += '<td style="display:none">' + data[i].specialized + '</td>';
                            html += '<td>' + data[i].name + '</td>';
                            html += '<td><input type="checkbox" class="checkbox-add-phacdo">';
                            html += '</td>';
                            html += '</tr>';
                        }
                    }
                    $('.data-table-service-add').find('tbody').html(html);
                    eventDeleteService();
                    $('#modal-add-service').modal('toggle');
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function eventDeleteService(){
    $('.delete-service-y-lenh').unbind().click(function(){
        $(this).parent().parent().remove();
    });
}

function addServiceOrRegimen(){
    if($('.modal-add-server-title').text() == 'Y lệnh'){

        $('.checkbox-add-y-lenh').each(function(){
            if($(this).prop("checked") == true){
                var id = $(this).parent().parent().find('.service-add-id').text();
                $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
                    if(Number($(this).text()) == Number(id)){
                        $(this).parent().remove();
                    }
                });
                var html = '';
                html += '<tr>';
                html += '<td class="stt"></td>';
                html += '<td style="display:none" class="service-add-id">' + id + '</td>';
                html += '<td class="service-add-type">' + $(this).parent().parent().find('.service-add-type').text() + '</td>';
                html += '<td class="service-add-name">' + $(this).parent().parent().find('.service-add-name').text() + '</td>';
                html += '<td><a href="#" class="btn btn-danger delete-service-y-lenh"><i class="fas fa-trash"></i></a>';
                html += '</td>';
                html += '</tr>';

                $('.data-table-service-y-lenh').find('tbody').append(html);
                eventDeleteService();
                var stt = 1;
                $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
                    $(this).parent().find('.stt').text(stt);
                    stt++;
                });
            }
        });
        $('#modal-add-service').modal('toggle');
    }else if($('.modal-add-server-title').text() == 'Phác đồ'){
        var ids = '';
        $('.data-table-service-add').find('.service-add-id').each(function(){
            if(ids == ''){
                ids = $(this).text();
            }else{
                ids += ',' + $(this).text();
            }
        });
        if(ids != ''){
            $.ajax({
                type: "GET",
                url: "/api/service/getServiceByPhacDoIds?ids=" + ids,
                dataType: 'json',
                headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
                success: function (response) {
                    if(response.responseCode == '00000'){
                        var data = response.data;
                        if(data != null || data != undefined){
                            for(var i = 0; i < data.length; i++){
                                $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
                                    if(Number($(this).text()) == Number(data[i].id)){
                                        $(this).parent().remove();
                                    }
                                });
                                var html = '';
                                html += '<tr>';
                                html += '<td class="stt"></td>';
                                html += '<td style="display:none" class="service-add-id">' + data[i].id + '</td>';
                                html += '<td>' + data[i].specialized + '</td>';
                                html += '<td>' + data[i].name + '</td>';
                                html += '<td><a href="#" class="btn btn-danger delete-service-y-lenh"><i class="fas fa-trash"></i></a>';
                                html += '</td>';
                                html += '</tr>';
                                $('.data-table-service-y-lenh').find('tbody').append(html);
                            }
                        }
                        eventDeleteService();
                        var stt = 1;
                        $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
                            $(this).parent().find('.stt').text(stt);
                            stt++;
                        });
                        $('#modal-add-service').modal('toggle');
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(thrownError);
                }
            });
        }
    }
}

function resetYLenh(){
    $('.data-table-service-y-lenh').find('tbody').empty();
}

function printYLenh() {
    $('#search-name-specialized').parent().parent().parent().parent().hide();
     var printContents = document.getElementById('view-print-y-lenh').innerHTML;
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
     $('#search-name-specialized').parent().parent().parent().parent().show();
}

function saveYLenh(){
    var data = {};
    var ids = '';
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    $('.data-table-service-y-lenh').find('.service-add-id').each(function(){
        if(ids == ''){
            ids = $(this).text();
        }else{
            ids += ',' + $(this).text();
        }
    });
    data.idServices = ids;
    data.idPatient = id;
    if(ids != ''){
        $.ajax({
            type: "POST",
            url: "/api/service/updateServiceToPatient",
            dataType: 'json',
            data: data,
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
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