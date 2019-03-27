# Convertigo Barcode Scanner Native Plugin #

This plugin allows for native Barcode scans 

### Install ###
Install this plugin like you would any other cordova plugin:
```
#!shell
cordova plugin add https://github.com/CharlesGrimont/cordova-plugin-barcode-convertigo.git#0.1.0
```
### Target Platforms ###
* Android (4.4+)

### Usage ###
The plugin creates a global variable in your app: 
```
#!javascript
  window["ConvScannerPlugin"]
```

This plugin allows one function:
```
#!javascript

scan: function(parameters, onSuccessCallback, onCancelCallback)

```
See the specs (and the Android application) to find out where to use which scans. 

Once called, these functions start their respective scan activities and returns either the scan result, or the fact that the user cancelled the scan.


```
#!javascript
let parameters = {
        laserColor: "#ff0000", laserEnabled: true, maskColor:"#eeeeee",
        textDown:"Mon super texte en bas", textUp: "Mon texte du haut (il est extraordinaire)",
        borderColor:"#ff0000", textUpColor:"#000000", textDownColor:"#000000", squareEnabled: true,
        maskOpacity: 0.5, restrict: true, imgPath:"icon/favicon.png"};

var onSuccess = function(result){
    console.log("Result type: " + result.type + " Value: " result.result); 
};

var onCancel = function(){
    console.log("User cancelled the barcode scan."); 
};

window["ConvScannerPlugin"].scan(parameters,onSuccess, onCancel);
```
