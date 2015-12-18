/**
 * Created by anthony on 18.12.2015.
 */

var oldTbody;

function updateBaliseTable(jsonBalise){
    oldTbody = document.getElementById("baliseTable").getElementsByTagName('tbody')[0];
    var newTbody = document.createElement('tbody');

    var tmp;
    for (tmp in jsonBalise.data) {
        var row = newTbody.insertRow(tmp);

        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        cell1.innerHTML = jsonBalise.data[tmp].id;
        cell2.innerHTML = jsonBalise.data[tmp].standName;
        cell3.innerHTML = "( " + jsonBalise.data[tmp].posX + " , " + jsonBalise.data[tmp].posY + " )";
        cell4.innerHTML = jsonBalise.data[tmp].puissance;
    }

    oldTbody.parentNode.replaceChild(newTbody, oldTbody);
}
