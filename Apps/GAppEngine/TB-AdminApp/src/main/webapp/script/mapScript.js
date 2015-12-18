/**
 * Created by anthony on 17.12.2015.
 */
var map;
var vectorLayer;

function mapInit(jsonStand) {
    var mapExtent = [0.000000, -924.000000, 876.000000, 0.000000];
    var mapMinZoom = 0;
    var mapMaxZoom = 2;
    var mapMaxResolution = 1.000000;
    var tileExtent = [0.000000, -924.000000, 876.000000, 0.000000];

    var mapResolutions = [];
    for (var z = 0; z <= mapMaxZoom; z++) {
        mapResolutions.push(Math.pow(2, mapMaxZoom - z) * mapMaxResolution);
    }

    var mapTileGrid = new ol.tilegrid.TileGrid({
        extent: tileExtent,
        minZoom: mapMinZoom,
        resolutions: mapResolutions
    });

    //marker part
    var vectorSource = new ol.source.Vector();

    var tmp;
    for (tmp in jsonStand.data) {
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point([jsonStand.data[tmp].posX, -1 * jsonStand.data[tmp].posY]),
            name: jsonStand.data[tmp].standName,
            population: 1,
            rainfall: 1
        });
        vectorSource.addFeature(iconFeature);
    }

    var iconStyle = new ol.style.Style({
        image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
            anchor: [17, 25],
            anchorXUnits: 'pixels',
            anchorYUnits: 'pixels',
            opacity: 0.75,
            src: '/img/map-marker-iconRedim.png'
        }))
    });

    vectorLayer = new ol.layer.Vector({
        source: vectorSource,
        style: iconStyle
    });

    map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.XYZ({
                    projection: 'PIXELS',
                    tileGrid: mapTileGrid,
                    url: "/img/mapZoom/{z}/{x}/{y}.png"
                })
            }),
            vectorLayer
        ],
        view: new ol.View({
            projection: ol.proj.get('PIXELS'),
            extent: mapExtent,
            maxResolution: mapTileGrid.getResolution(mapMinZoom),
            center: [0, 0],
            zoom: 1
        })
    });
    map.getView().fit(mapExtent, map.getSize());
}

function updateMarker(jsonStand){
    var vectorSource = new ol.source.Vector();

    var tmp;
    for (tmp in jsonStand.data) {
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point([jsonStand.data[tmp].posX, -1 * jsonStand.data[tmp].posY]),
            name: jsonStand.data[tmp].standName,
            population: 1,
            rainfall: 1
        });
        vectorSource.addFeature(iconFeature);
    }

    var iconStyle = new ol.style.Style({
        image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
            anchor: [17, 25],
            anchorXUnits: 'pixels',
            anchorYUnits: 'pixels',
            opacity: 0.75,
            src: '/img/map-marker-iconRedim.png'
        }))
    });

    vectorLayer.setSource(vectorSource);
}