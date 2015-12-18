/**
 * Created by anthony on 18.12.2015.
 */
function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(JSON.parse(xmlHttp.responseText));
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}

function initPage(){
    //map
    httpGetAsync("/api?action=getStand", mapInit);

    setInterval(updatePage, 2000);
}

function updatePage(){
    //update marker
    httpGetAsync("/api?action=getStand", updateMarker);
}

initPage();