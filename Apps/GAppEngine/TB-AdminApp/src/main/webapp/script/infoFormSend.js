/**
 * Created by Anthony on 21.01.2016.
 */
//transforme les données du formulaire en json
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//gestion de l'envoi du formulaire via jquery
$('#infoForm').submit(function (event) {
    var form = $("#infoForm");
    var json = JSON.stringify(form.serializeObject());

    $.ajax({
        type: "POST",
        url: "/api/info",
        // The key needs to match your method's input parameter (case-sensitive).
        data: json,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            alert("SUCESS!");
        },
        failure: function (errMsg) {
            alert(errMsg);
        }
    });

    return false;
})