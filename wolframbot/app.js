/**
 * Created by David on 2/11/2017.
 */
var twitter = require('twit')
var config = require('./config.js')
var T = new twitter(config)
var Wolfclient = require('node-wolfram')
var waID = 'H3GTVL-755YPJH4P2' //Wolfram Alpha AppID
var Wolfram = new Wolfclient(waID)

var errorReply = "I'm sorry, I couldn't find the answer for that. Rephrasing the question might help";

//Main method
function start() {
    console.log("IntelliBot activated")
    var stream = T.stream('user');
    stream.on('tweet', function(tweet) {
        if (tweet.in_reply_to_screen_name === 'Intellibot5') {
            var tweetID = tweet.id_str
            var userName = tweet.user.screen_name
            var length = 138 - userName.length //length of each tweet
            var question = tweet.text.toLowerCase().replace("@intellibot5 ", "")
            wolfram(question, function(text) {
                if (text == "error") {
                    text = errorReply
                }
                replyText = '@' + userName + ' ' + text
                var params; //parameters
                if (replyText.length > 140) {
                    var numTweets = Math.ceil(replyText.length / 140)
                    var index = 0
                    for (i = 0; i < numTweets; i++) {
                        try {
                            replyText = text.substr(index, length)
                        }
                        catch (err) {
                            replyText = text.substr(index)
                        }
                        index = index + length;
                        params = {
                            status: '@' + userName + ' ' + replyText,
                            in_reply_to_status_id: tweetID
                        }
                        tweetBack(params)
                    }
                }
                else {
                    params = {
                        status: replyText,
                        in_reply_to_status_id: tweetID
                    }
                    tweetBack(params)
                }
            })

        }
    })
}

//Function that tweets back to the user
function tweetBack(params) {
    T.post('statuses/update', params, function(err, data, response) {
        if (err) {
            console.log("Error replying :(\n")
        }
        else {
            console.log("Successfully replied!!\n")
        }
    });
}

//Function that gets the answer to the question asked and returns the answer
function wolfram(question, callback) {
    try {
        console.log("QUESTION: " + question + "\n")
        Wolfram.query(question, function(err, result) {
            if (err) {
                console.log("error in query:" + err)
                callback("error")
            }
            else
            {
                if (result != null && result.queryresult.pod != null) {
                    var pod = result.queryresult.pod[1];
                    var subpod = pod.subpod[0];
                    var text = subpod.plaintext[0]; //the plaintext answer
                    console.log(text + "\n")
                    callback(text)
                }
                else {
                    console.log("error getting pod")
                    callback("error")
                }
            }
        });
    }
    catch (err) {
        console.log("error in try: " + err)
        callback("error")
    }
}

start(); //start the program