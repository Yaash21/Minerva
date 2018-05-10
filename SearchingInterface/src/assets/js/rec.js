var simpleWebkitRec = (function() {
    return {
        webkitSpeechInit = function() {
            window.onload = function init() {
                try {
                  if ('webkitSpeechRecognition' in window) {
                    var rec = new webkitSpeechRecognition();
                    rec.onresult = function(e) {
                        console.log(e);
                    }
                    rec.start();
                  }
      
                } catch (e) {
                  alert('No web audio support in this browser!');
                  console.log("Unable to start media");
                }
      
                // navigator.getUserMedia({audio: true}, startUserMedia, function(e) {
                //   __log('No live audio input: ' + e);
                // });
            };
        }
    }
})(simpleWebkitRec || {})