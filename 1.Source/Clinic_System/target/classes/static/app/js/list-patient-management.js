$( document ).ready(function() {
    getListPatientCurDate();
});

function getListPatientCurDate(){
    $.ajax({
        type: "GET",
        url: "/api/admission-record/getAdmissionOfDoctor" ,
        dataType: 'json',
        headers: {"token": localStorage.getItem('token'),"userid": localStorage.getItem('userid')},
        success: function (response) {
            $('.data-table-patient-examining').find('tbody').empty();
            $('.data-table-patient-waiting').find('tbody').empty();
            $('.data-table-patient-waiting-test').find('tbody').empty();
            $('.data-table-patient-examined').find('tbody').empty();
            if(response.responseCode == '00000'){
                var html_patient_examining = '';
                var html_patient_waiting = '';
                var html_patient_waiting_test = '';
                var html_patient_examined = '';
                var data = response.data;
                if(data != null || data != undefined){
                    var count1 = 0;
                    var count2 = 0;
                    var count4 = 0;
                    var count5 = 0;
                    for(var i = 0; i < data.length; i++){
                        if(data[i].status == 'examining'){
                            html_patient_examining += '<tr>';
                            html_patient_examining += '<td>' + (count1+1) + '</td>';
                            html_patient_examining += '<td>' + data[i].name + '</td>';
                            html_patient_examining += '<td>' + (data[i].priority ? 'Yes' : 'No') + '</td>';
                            html_patient_examining += '<td><button type="button" class="btn btn-block btn-outline-info btn-xs" ';
                            html_patient_examining += ' onclick="viewPatient(' + data[i].idPatient + ')"' + '>Xem</button>';
                            html_patient_examining += '<button type="button" class="btn btn-block btn-outline-warning btn-xs" ';
                            html_patient_examining += ' onclick="changeStatusPatient(' + data[i].idPatient + ',\'waiting test\')"' + '>Chờ XN</button>';
                            html_patient_examining += '<button type="button" class="btn btn-block btn-outline-dark btn-xs" ';
                            html_patient_examining += ' onclick="changeStatusPatient(' + data[i].idPatient + ',\'examined\')"' + '>Đã khám</button>';
                            html_patient_examining += '</td>';
                            html_patient_examining += '</tr>';
                            count1++;
                        } else if(data[i].status == 'waiting'){
                           html_patient_waiting += '<tr>';
                           html_patient_waiting += '<td>' + (count2+1) + '</td>';
                           html_patient_waiting += '<td>' + data[i].name + '</td>';
                           html_patient_waiting += '<td>' + (data[i].priority ? 'Yes' : 'No') + '</td>';
                           html_patient_waiting += '<td><button type="button" class="btn btn-block btn-outline-info btn-xs" ';
                           html_patient_waiting += ' onclick="viewPatient(' + data[i].idPatient + ')"' + '>Xem</button>';
                           html_patient_waiting += '<button type="button" class="btn btn-block btn-outline-success btn-xs" ';
                           html_patient_waiting += ' onclick="changeStatusPatient(' + data[i].idPatient + ',\'examining\')"' + '>Khám</button>';
                           html_patient_waiting += '</td>';
                           html_patient_waiting += '</tr>';
                           count2++;
                       } else if(data[i].status == 'waiting test'){
                           html_patient_waiting_test += '<tr>';
                           html_patient_waiting_test += '<td>' + (count4+1) + '</td>';
                           html_patient_waiting_test += '<td>' + data[i].name + '</td>';
                           html_patient_waiting_test += '<td>' + (data[i].priority ? 'Yes' : 'No') + '</td>';
                           html_patient_waiting_test += '<td><button type="button" class="btn btn-block btn-outline-info btn-xs" ';
                           html_patient_waiting_test += ' onclick="viewPatient(' + data[i].idPatient + ')"' + '>Xem</button>';
                           html_patient_waiting_test += '<button type="button" class="btn btn-block btn-outline-dark btn-xs" ';
                           html_patient_waiting_test += ' onclick="changeStatusPatient(' + data[i].idPatient + ',\'examined\')"' + '>Đã khám</button>';
                           html_patient_waiting_test += '</td>';
                           html_patient_waiting_test += '</tr>';
                           count4++;
                       }
                        else if(data[i].status == 'examined'){
                          html_patient_examined += '<tr>';
                          html_patient_examined += '<td>' + (count5+1) + '</td>';
                          html_patient_examined += '<td>' + data[i].name + '</td>';
                          html_patient_examined += '<td>' + (data[i].priority ? 'Yes' : 'No') + '</td>';
                          html_patient_examined += '<td><button type="button" class="btn btn-block btn-outline-info btn-xs" ';
                          html_patient_examined += ' onclick="viewPatient(' + data[i].idPatient + ')"' + '>Xem</button>';
                          html_patient_examined += '</td>';
                          html_patient_examined += '</tr>';
                          count5++;
                      }


                    }
                }
                $('.data-table-patient-examining').find('tbody').html(html_patient_examining);
                $('.data-table-patient-waiting').find('tbody').html(html_patient_waiting);
                $('.data-table-patient-waiting-test').find('tbody').html(html_patient_waiting_test);
                $('.data-table-patient-examined').find('tbody').html(html_patient_examined);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(thrownError);
        }
    });
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

function viewPatient(id){
    location.href = '/patient/patient-detail-management?id=' + id;
}