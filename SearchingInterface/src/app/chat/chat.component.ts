import { Component, OnInit, Renderer } from "@angular/core";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import * as $ from "jquery";
import { MatIconModule } from "@angular/material";
import { InfiniteScrollModule } from "ngx-infinite-scroll";

declare var speechObject: any;
declare function recordAndRecognize(): void;
declare function synthesizeSpeech(text): void;
declare function getResult(): string;
declare function clearResult();

@Component({
  selector: "app-chat",
  templateUrl: "./chat.component.html",
  styleUrls: ["./chat.component.css"]
})
export class ChatComponent implements OnInit {
  ngOnInit(): void {
    this.toggleVisbility = true;
    localStorage.clear();
  }
  greeting = "Minerva";
  toggleVisbility: any;
  value: string;
  private serverUrl = "http://minerva.stackroute.in:8081/socket";
  private title = "ChatBot Interface";
  private stompClient;
  private typedvalue;
  private sessionId;
  private ws;
  chatInterface = false;
  searchResult = true;
  result: any;
  leftValue = 0;
  rightValue = 5;
  recommendation: any;
  date = new Date();
  messages = [
    {
      text: "Hi! I am Moza! How can I help you?",
      self: false,
      time: this.date,
      result: []
    }
  ];

  resttingTheValue() {
    this.leftValue = 0;
    this.rightValue = 5;
  }

  toggleView() {}

  constructor(private render: Renderer) {
    this.initializeWebSocketConnection();
    synthesizeSpeech("Hi! I am Moza! How can I help you?");
  }

  fakeCallBack() {
    setTimeout(() => {
      const voiceRes = getResult();
      if (voiceRes === undefined || voiceRes === "") {
        this.fakeCallBack();
      } else {
        console.log(voiceRes);
        this.value = getResult();
        this.sendMessage(getResult(), null);
        clearResult();
      }
    }, 500);
  }

  initializeWebSocketConnection() {
    this.ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(this.ws);
    let that = this;
    this.stompClient.connect({}, function(frame) {
      this.sessionId = /\/([^\/]+)\/websocket/.exec(this.ws._transport.url)[1];
      localStorage.setItem(
        "sessionId",
        /\/([^\/]+)\/websocket/.exec(this.ws._transport.url)[1]
      );
      localStorage.setItem(localStorage.getItem("sessionId"), "none");
      that.stompClient.subscribe("/chat/" + this.sessionId, message => {
        if (message.body) {
          console.log(message);
          that.addMessage(message.body, false);
          synthesizeSpeech(message.body);
          console.log("session id " + this.sessionId);
        }
      });
      that.stompClient.subscribe("/search/" + this.sessionId, message => {
        console.log("message received", message);
        that.result = JSON.parse(message.body).response;
        console.log("final result is this", that.result);
        that.addResult(that.result);
        if (that.chatInterface == false) {
          that.toggleView();
          that.toggleVisbility = false;
        }
        that.resttingTheValue();
      });
      that.stompClient.subscribe(
        "/recommendation/" + this.sessionId,
        message => {
          that.recommendation = JSON.parse(message.body).intentRecommendations;
          console.log("final recommendation is this", that.recommendation);
        }
      );
    });
  }

  ngOnChanges() {
    this.checkForScroller(event);
  }
  activated = false;

  addMessage(text: string, self: boolean) {
    this.messages.reverse();
    this.date = new Date();
    this.messages = this.messages.concat([
      {
        text: text,
        self: self,
        time: this.date,
        result: []
      }
    ]);
    this.messages.reverse();
    this.scrollToTheBottom();
    if (text === "I can help you with that") {
      this.activated = true;
    }
    if (this.activated) this.activated = false;
  }

  addResult(result: any) {
    this.messages.reverse();
    this.messages = this.messages.concat([
      {
        text: null,
        self: false,
        time: this.date,
        result: result
      }
    ]);
    this.messages.reverse();
    this.scrollToTheBottom();
    this.activated = false;
  }

  public startRecP5(event: Event) {
    event.preventDefault();
    recordAndRecognize();
    console.log("this is called");
    setTimeout(() => {
      this.fakeCallBack();
      500;
    });
  }
  inputValue = "";

  sendMessage(message, event: Event) {
    if (message != "") {
      this.addMessage(message, true);
      if (event != null) {
        event.preventDefault();
      }
      this.stompClient.send(
        "/app/send/message",
        {},
        '{"message":' +
          '"' +
          message +
          '"' +
          ',"sessionId":' +
          '"' +
          /\/([^\/]+)\/websocket/.exec(this.ws._transport.url)[1] +
          '"' +
          "}"
      );
      $("input").val("");
      this.inputValue = "";
    }
    document.getElementById("input").focus();
  }

  goLeft() {
    if (this.leftValue > 0) {
      this.rightValue = this.leftValue;
      this.leftValue = this.leftValue - 5;
    }
  }

  goRight() {
    if (this.rightValue < 40) {
      this.leftValue = this.rightValue;
      this.rightValue = this.rightValue + 5;
    }
  }

  randomFun() {
    console.log("scrolled");
  }

  sendRecommendation(number, event: Event) {
    if (event != null) {
      event.preventDefault();
    }
    this.stompClient.send(
      "/app/send/recommendation",
      {},
      '{"message":' +
        '"' +
        this.recommendation[number].domainRecommend +
        " " +
        this.recommendation[number].conceptRecommend +
        '",' +
        '"intent": "' +
        this.recommendation[number].intentRecommend +
        '"' +
        ',"sessionId":' +
        '"' +
        /\/([^\/]+)\/websocket/.exec(this.ws._transport.url)[1] +
        '"' +
        "}"
    );
    $("#input").val("");
  }

  scrollToTheBottom() {
    let ele = document.getElementById("messageSpace");
    var scrollpercent =
      ele.scrollTop / (ele.scrollHeight - ele.clientHeight) * 100;
    ele.scrollTop = 10000000;
    ele.scrollLeft = 0;
  }
  showOrNot: boolean = false;
  checkForScroller(event: Event) {
    console.log("method called");
    let ele = document.getElementById("messageSpace");
    var scrollpercent =
      ele.scrollTop / (ele.scrollHeight - ele.clientHeight) * 100;
    if (scrollpercent > 1) {
      this.showOrNot = false;
    } else {
      this.showOrNot = true;
    }
    console.log(this.showOrNot);
  }
}
