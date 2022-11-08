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
                            html += '<div class="col-12 col-sm-4">';
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