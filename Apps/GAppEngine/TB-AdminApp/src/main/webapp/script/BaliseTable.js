/**
 * Created by anthony on 18.12.2015.
 */

var oldTbody;

//met à jour le tableau des balises
function updateBaliseTable(jsonBalise){
    oldTbody = document.getElementById("baliseTable").getElementsByTagName('tbody')[0];
    var newTbody = document.createElement('tbody');

    var tmp;
    for (tmp in jsonBalise.data) {
        var row = newTbody.insertRow(tmp);

        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);

        cell1.innerHTML = jsonBalise.data[tmp].nom;
        cell2.innerHTML = jsonBalise.data[tmp].standId;
        cell3.innerHTML = jsonBalise.data[tmp].puissance;
    }

    oldTbody.parentNode.replaceChild(newTbody, oldTbody);
}
