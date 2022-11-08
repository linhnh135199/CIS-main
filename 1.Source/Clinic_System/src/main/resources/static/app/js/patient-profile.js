$( document ).ready(function() {
    getProfilePatient();
});

function getProfilePatient(){
    var id = null;
    if(location.search.split('id=').length > 1){
        id = location.search.split('id=')[1];
    }
    if(id != null){
        $.ajax({
            type: "GET",
            url: "/api/patient/getProfilePatientById?id=" + id,
            dataType: 'json',
            headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
            success: function (response) {
                if(response.responseCode == '00000'){
                    $('.code-patient').text(response.data.id);
                    $('.name-patient').text(response.data.name);
                    $('.phone-patient').text(response.data.phone);
                    $('.gender-patient').text(response.data.gender);
                    $('.birthday-patient').text(getFormattedDate(response.data.birthday));
                    $('.address-patient').text(response.data.address);
                    var patients = response.data.patients;
                    $('.list-profile-detail').empty();
                    if(patients.length > 0){
                        var html = '';
                        for(var i = 0; i < patients.length; i++){
                            html += '<div class="col-12 col-sm-4" ' + 'onclick="showProfileDetailPatient(' + patients[i].id +')">';
                                html += '<div class="info-box bg-light">';
                                    html += '<div class="info-box-content">';
                                        html += '<span class="info-box-text text-center text-muted">Ngày khám: ' + getFormattedDate(patients[i].dayExamination) + '</span>';
                                        html += '<span class="info-box-number text-center text-muted mb-0">Kết luận:</span>';
                                        html += '<span class="text-center">' + patients[i].conclude +'</span>';
                                    html += '</div>';
                                html += '</div>';
                            html += '</div>';
                        }
                        $('.list-profile-detail').html(html);
                    }
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError);
            }
        });
    }
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

function showProfileDetailPatient(id){
    $.ajax({
        type: "GET",
        url: "/api/patient/getProfilePatientDetailByPatientId?id=" + id,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            if(response.responseCode == '00000'){
                $('#modal-patient-detail-profile').modal('toggle');
                $('#temperature-patient').text(response.data.temperature);
                $('#height-patient').text(response.data.height);
                $('#blood-pressure-patient').text(response.data.bloodPressure);
                $('#weight-patient').text(response.data.weight);
                $('.symptom-patient').text(response.data.symptom);
                $('#search-name-first-diagnosis-patient-value').text(response.data.firstDiagnosis);
                $('#search-name-final-diagnosis-patient-value').text(response.data.finalDiagnosis);
                $('#search-name-including-diseases-patient-value').text(response.data.includingDiseases);
                $('.conclude-patient').text(response.data.conclude);
                $('.doctor-exam').text(response.data.doctorName);
                $('.date-exam').text(getFormattedDate(response.data.dayExamination));
                var prescriptionReqList = response.data.prescriptionReqList;
                $('#table-prescription-list').find('tbody').empty();
                if(prescriptionReqList.length > 0){
                    var html = '';
                    for(var i = 0; i < prescriptionReqList.length; i++){
                        html += '<tr>';
                        html += '<td class="stt">' + (i+1) + '</td>';
                        html += '<td>' + prescriptionReqList[i].name + '</td>';
                        html += '<td>' + (prescriptionReqList[i].hoatChat + ' ' + prescriptionReqList[i].hamLuong) + '</td>';
                        html += '<td class="huong-dan-prescription">' + (prescriptionReqList[i].huongDan != undefined ? prescriptionReqList[i].huongDan : '') + '</td>';
                        html += '<td>' + (prescriptionReqList[i].duongDung != undefined ? prescriptionReqList[i].duongDung : '') + '</td>';
                        html += '<td class="don-vi-prescription">' + (prescriptionReqList[i].donVi != undefined ? prescriptionReqList[i].donVi : '') + '</td>';
                        html += '<td class="so-luong-prescription">' + (prescriptionReqList[i].soLuong != undefined ? prescriptionReqList[i].soLuong : '') + '</td>';
                        html += '</tr>';
                    }
                    $('#table-prescription-list').find('tbody').html(html);
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
}