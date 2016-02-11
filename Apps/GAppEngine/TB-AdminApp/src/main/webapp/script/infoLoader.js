/**
 * Created by Anthony on 21.01.2016.
 */
//http async request
function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(JSON.parse(xmlHttp.responseText));
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}

//met à jour le formulaire grace au json
function updateForm(jsonInfo){
    var titre = document.getElementById("titre");
    var description = document.getElementById("description");
    var img = document.getElementById("img");
    var key = document.getElementById("key");

    //parse Json
    var tmp;
    for (tmp in jsonInfo.data) {

        titre.value = jsonInfo.data[tmp].title;
        description.innerHTML = jsonInfo.data[tmp].description;
        img.value = jsonInfo.data[tmp].imgUrl;
        key.value = jsonInfo.data[tmp].id;
    }

}

//charge les informations
function parseUrl(){
    var url = window.location.href;
    var urlSplit = url.split("/");
    var idInfo = urlSplit[urlSplit.length-1];

    httpGetAsync("/api/info/" + idInfo, updateForm);

}

parseUrl();
