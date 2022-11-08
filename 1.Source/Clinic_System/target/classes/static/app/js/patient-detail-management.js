$( document ).ready(function() {
    cleanDataPatient();
    getPatientDetail();
    $("#search-name-first-diagnosis-patient").autocomplete({
          source :function( request, response ) {
            $.ajax({
               url: "/api/catalogIcd/getDataICD",
               dataType: "json",
               data: {
                   key: request.term,
                   page : 1
               },
               headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
               success: function( data ) {
                    $("#search-name-first-diagnosis-patient-value").val('');
                    if(data.responseCode == "00000"){
                        response($.map(data.data.listICD, function(item) {
                             return {
                                 label : item.diseaseName,
                                 value : item.id
                             };
                       }));
                    }
               }
            });
           },
           select: function (event, ui) {
                $("#search-name-first-diagnosis-patient").val(ui.item.label);
                $("#search-name-first-diagnosis-patient-value").val(ui.item.value);
                return false;
          },
          close: function() {
              if($("#search-name-first-diagnosis-patient-value").val() == ''){
                  $("#search-name-first-diagnosis-patient").val('');
                  $("#search-name-first-diagnosis-patient-value").val('');
              }
          }
       });

       $("#search-name-final-diagnosis-patient").autocomplete({
         source :function( request, response ) {
           $.ajax({
              url: "/api/catalogIcd/getDataICD",
              dataType: "json",
              data: {
                 key: request.term,
                 page : 1
              },
              headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
              success: function( data ) {
                   $("#search-name-final-diagnosis-patient-value").val('');
                   if(data.responseCode == "00000"){
                       response($.map(data.data.listICD, function(item) {
                            return {
                                label : item.diseaseName,
                                value : item.id
                            };
                      }));
                   }
              }
           });
          },
          select: function (event, ui) {
                 $("#search-name-final-diagnosis-patient").val(ui.item.label);
                 $("#search-name-final-diagnosis-patient-value").val(ui.item.value);
                 return false;
           },
           close: function() {
               if($("#search-name-final-diagnosis-patient-value").val() == ''){
                   $("#search-name-final-diagnosis-patient").val('');
                   $("#search-name-final-diagnosis-patient-value").val('');
               }
           }
      });

    $("#search-name-including-diseases-patient").autocomplete({
        minLength: 0,
        source :function( request, response ) {
          $.ajax({
             url: "/api/catalogIcd/getDataICD",
             dataType: "json",
             data: {
                 key: extractLast(request.term),
                 page : 1
             },
             headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
             success: function( data ) {
                  if(data.responseCode == "00000"){
                      response($.map(data.data.listICD, function(item) {
                           return {
                               label : item.diseaseName,
                               value : item.id
                           };
                     }));
                  }
             }
          });
         },
        focus: function() {
             return false;
        },
        select: function( event, ui ) {
            var terms = split( $("#search-name-including-diseases-patient").val());
            var termsId = split( $("#search-name-including-diseases-patient-value").val());
            terms.pop();
            terms.push( ui.item.label );
            terms.push( "" );
            $("#search-name-including-diseases-patient").val(terms.join( ", " ));

             termsId.pop();
             termsId.push( ui.item.value );
             termsId.push( "" );
             $("#search-name-including-diseases-patient-value").val(termsId.join( "," ));
            return false;
        },
     });

    $('#patient-detail-home-tab').unbind().click(function(){
        cleanDataPatient();
        getPatientDetail();
    });

    $('#prescription-detail-profile-tab').unbind().click(function(){
        getDonThuocPatient();
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
                              $(".huong-dan-prescription-text").val(data.data.huongDan);
                              $(".so-luong-prescription-text").val(data.data.soLuong);
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
    });

    $('#y-lenh-messages-tab').unbind().click(function(){
        getYLenhPatient();
        $("#search-name-specialized").autocomplete({
          source :function( request, response ) {
            $.ajax({
               url: "/specialized/getDataSearch",
               dataType: "json",
               data: {
                  key: request.term
               },
               success: function( data ) {
                   $("#search-name-specialized-value").val('');
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
               return false;
           },
           close: function() {
               if($("#search-name-specialized-value").val() == ''){
                   $("#search-name-specialized").val('');
                   $("#search-name-specialized-value").val('');
               }
           }
       });
    });


    $('.action-examining').unbind().click(function(){
        var id = null;
        if(location.search.split('id=').length > 1){
            id = location.search.split('id=')[1];
        }
        changeStatusPatient(id, 'examining');
    });

    $('.action-cancel-examining').unbind().click(function(){
        var id = null;
        if(location.search.split('id=').length > 1){
            id = location.search.split('id=')[1];
        }
        changeStatusPatient(id, 'waiting');
    });

    $('.action-examining-done').unbind().click(function(){
        var id = null;
        if(location.search.split('id=').length > 1){
            id = location.search.split('id=')[1];
        }
        changeStatusPatient(id, 'examined');
    });

    $('.action-patient-detail-save').unbind().click(function(){
            if(validate()){
            var data = {};
            var id = null;
            if(location.search.split('id=').length > 1){
                id = location.search.split('id=')[1];
            }
            data.id = id;
            data.symptom = $('.symptom-patient').val();
            data.firstDiagnosisId = $('#search-name-first-diagnosis-patient-value').val();
            data.finalDiagnosisId = $('#search-name-final-diagnosis-patient-value').val();
            data.includingDiseasesId = $('#search-name-including-diseases-patient-value').val();
            data.conclude = $('.conclude-patient').val();
            data.temperature = $('#temperature-patient').val();
            data.height = $('#height-patient').val();
            data.bloodPressure = $('#blood-pressure-patient').val();
            data.weight = $('#weight-patient').val();
            $.ajax({
                type: "POST",
                url: "/api/patient/updatePatient",
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
        });
    });

    function validate(){
        $('.error-user').remove();
        var check = true;
        var numberRegex = /^\d+$/;

        if($('#temperature-patient').val().trim() !== ''){
            if(numberRegex.test($('#temperature-patient').val()) == false){
                $('#temperature-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Nhiệt độ không đúng định dạng</div>' ) );
                check = false;
            }
        }else{
            $('#temperature-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Nhiệt độ không được để trống</div>' ) );
            check = false;
        }

        if($('#height-patient').val().trim() !== ''){
                if(numberRegex.test($('#height-patient').val()) == false){
                    $('#height-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Chiều cao không đúng định dạng</div>' ) );
                    check = false;
                }
            }else{
                $('#height-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Chiều cao không được để trống</div>' ) );
                check = false;
            }

       if($('#blood-pressure-patient').val().trim() !== ''){
               if(numberRegex.test($('#blood-pressure-patient').val()) == false){
                   $('#blood-pressure-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Huyết áp không đúng định dạng</div>' ) );
                   check = false;
               }
           }else{
               $('#blood-pressure-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Huyết áp không được để trống</div>' ) );
               check = false;
           }

       if($('#weight-patient').val().trim() !== ''){
               if(numberRegex.test($('#weight-patient').val()) == false){
                   $('#weight-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Cân nặng không đúng định dạng</div>' ) );
                   check = false;
               }
           }else{
               $('#weight-patient').parent().after( $( '<div class="error-user" style="color: red;font-size: 14px;">Cân nặng không được để trống</div>' ) );
               check = false;
           }

        return check;
    }

function getPatientDetail(){
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    if(id != null){
        $.ajax({
            type: "GET",
            url: "/api/patient/getPatientById?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    $('.code-patient').text(response.data.code);
                    $('.name-patient').text(response.data.name);
                    $('.phone-patient').text(response.data.phone);
                    $('.gender-patient').text(response.data.gender);
                    $('.birthday-patient').text(getFormattedDate(response.data.birthday));
                    $('.address-patient').text(response.data.address);
                    $('.temperature-patient').val(response.data.temperature);
                    $('.height-patient').val(response.data.height);
                    $('.blood-pressure-patient').val(response.data.bloodPressure);
                    $('.weight-patient').val(response.data.weight);
                    $('.symptom-patient').text(response.data.symptom);
                    $('#search-name-first-diagnosis-patient-value').text(response.data.firstDiagnosisId);
                    $('#search-name-final-diagnosis-patient-value').text(response.data.finalDiagnosisId);
                    $('#search-name-first-diagnosis-patient').val(response.data.firstDiagnosis);
                    $('#search-name-final-diagnosis-patient').val(response.data.finalDiagnosis);
                    $('#search-name-including-diseases-patient-value').text(response.data.includingDiseasesId);
                    $('#search-name-including-diseases-patient').val(response.data.includingDiseases != '' ? (response.data.includingDiseases + ', ') : '');
                    $('.conclude-patient').text(response.data.conclude);
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
}

function cleanDataPatient(){
    $('.code-patient').text('');
    $('.name-patient').text('');
    $('.phone-patient').text('');
    $('.gender-patient').text('');
    $('.birthday-patient').text('');
    $('.address-patient').text('');
    $('.temperature-patient').text('');
    $('.height-patient').text('');
    $('.blood-pressure-patient').text('');
    $('.weight-patient').text('');
    $('.symptom-patient').text('');
    $('.first-diagnosis-patient').text('');
    $('.final-diagnosis-patient').text('');
    $('.including-diseases-patient').text('');
    $('.conclude-patient').text('');
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

function changeStatusPatient(id, status){
    var data = {};
    data.id = id;
    data.status = status.trim();
    $.ajax({
        type: "POST",
        url: "/api/admission-record/updateStatusAdmissionOfDoctor",
        dataType: 'json',
        data: data,
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $(document).Toasts('create', {
                    class: 'bg-success',
                    title: 'Edit Status',
                    subtitle: '',
                    body: 'Edit successfully.'
                });
            }else{
                $(document).Toasts('create', {
                    class: 'bg-danger',
                    title: 'Edit Status',
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
