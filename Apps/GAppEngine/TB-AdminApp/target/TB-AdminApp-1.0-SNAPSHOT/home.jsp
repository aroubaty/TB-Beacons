
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HomePage</title>
    <link href="/stylesheets/main.css" rel="stylesheet">

    <!-- bootstrap's stuff -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

    <!-- Custom styles for this template -->
    <!-- base example http://getbootstrap.com/examples/navbar-fixed-top/ -->
    <link href="/stylesheets/navbar-fixed-top.css" rel="stylesheet">

    <!-- map lib http://build-failed.blogspot.ch/2012/11/zoomable-image-with-leaflet.html -->
    <link rel="stylesheet" href="/stylesheets/ol.css" type="text/css">
    <script src="/script/ol.js" type="text/javascript"></script>
    <!-- <script src="/script/ol-debug.js" type="text/javascript"></script> -->


</head>
<body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">TB Beacon</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#about">Options</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <!--<li><a href="../navbar/">Default</a></li>
                    <li><a href="../navbar-static-top/">Static top</a></li>
                    <li class="active"><a href="./">Fixed top <span class="sr-only">(current)</span></a></li>-->
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>

    <div class="container">

        <!-- Main component for a primary marketing message or call to action -->
        <div class="jumbotron">
            <h1>Carte de l'exposition</h1>

            <div id="map"></div>

        </div>

    </div> <!-- /container -->

    <div class="container">
        <div class="jumbotron">
            <div class="col-xs-6">
                <h3>Balises</h3>
                <p></p>
            </div>

            <div class="col-xs-6">
                <h3>Flux de personnes</h3>
                <p></p>
            </div>
        </div>
    </div>

    <!-- script part -->
    <script src="/script/mapScript.js" type="text/javascript"></script>
    <script src="/script/AsyncLoader.js" type="text/javascript"></script>

</body>
</html>
