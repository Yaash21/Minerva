
var recog;
var myVoice;
var result = '';

function startUserMedia(stream) {

  // Uncomment if you want the audio to feedback directly
  //input.connect(audio_context.destination);
  //__log('Input connected to audio context destination.');

  myVoice = new p5.Speech();
  recog = new p5.SpeechRec();
  console.log('Speech Initialised');

}

function synthesizeSpeech(text) {
  if (typeof (text) !== 'string') {
    console.log('Parameters passed are not string');
  }
  else {
    this.myVoice = new p5.Speech();
    this.myVoice.speak(text);
  }
}

function recordAndRecognize() {
  recog = new p5.SpeechRec();
  recog.onResult = showResult; 	  // bind callback function to trigger when speech is recognized
  recog.start();			           // start listening
  console.log("this is recordAndRecognize")
}

function showResult() {
  console.log(recog.resultString); // log the result
  this.result = recog.resultString;
}

function getResult() {
  return recog.resultString;
}

function clearResult() {
  recog.resultString = '';
}

var speechObject = (function () {
  return {
    speechClient: function () {
      (function ($) {
        'use strict';
        window.onload = function init() {
          try {
            // navigator.getUserMedia  = navigator.getUserMedia ||
            // navigator.webkitGetUserMedia ||
            // navigator.mozGetUserMedia ||
            // navigator.msGetUserMedia;

            window.AudioContext = window.AudioContext || window.webkitAudioContext;
            navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
            window.URL = window.URL || window.webkitURL;

            // audio_context = new AudioContext;

            myVoice = new p5.Speech();
            recog = new p5.SpeechRec();

          } catch (e) {
            alert('No web audio support in this browser!');
            console.log("Unable to start media");
          }

          // navigator.getUserMedia({audio: true}, startUserMedia, function(e) {
          //   __log('No live audio input: ' + e);
          // });
        };
      })(window.jQuery);
    }
  }
})(speechObject || {})
