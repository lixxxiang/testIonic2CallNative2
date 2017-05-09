

function callJs(content){
var head = document.getElementsByTagName('head');
var testScript = document.createElement('script');
testScript.src = 'build/main.js';
testScript.type = 'text/javascript';
head[0].appendChild(testScript);

    console.log('callJS' + content);
//    about();

    TT();
}