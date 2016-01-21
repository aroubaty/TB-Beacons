<%--
  Created by IntelliJ IDEA.
  User: Anthony
  Date: 21.01.2016
  Time: 09:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information</title>

    <script src="/script/jquery-2.2.0.min.js" type="text/javascript"></script>

    <!-- bootstrap's stuff -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
          integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
            integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
            crossorigin="anonymous"></script>

    <!-- Custom styles for this template -->
    <!-- base example http://getbootstrap.com/examples/navbar-fixed-top/ -->
    <link href="/stylesheets/navbar-fixed-top.css" rel="stylesheet">

</head>
<body>

<div class="container">

    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Mise à jour des informations</h1>

        <form class="form-horizontal" name="infoForm" id="infoForm">
            <div class="form-group">
                <label class="control-label col-sm-2" for="titre">Titre:</label>
                <div class="col-sm-10">
                    <input name="title" type="text" class="form-control" id="titre" placeholder="Titre">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="img">Choisir une image:</label>
                <div class="col-sm-10">
                    <input name="imgUrl" id="img" type="text" class="form-control" placeholder="URL uniquement" >
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="description">Description:</label>
                <div class="col-sm-10">
                    <textarea name="description" class="form-control" rows="5" id="description"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Mettre à jour</button>
                </div>
            </div>

            <input id="key" type="hidden" name="key" value="noKey">
        </form>

    </div>

</div> <!-- /container -->

<!-- script part -->
<script src="/script/infoLoader.js" type="text/javascript"></script>
<script src="/script/infoFormSend.js" type="text/javascript"></script>

</body>
</html>
