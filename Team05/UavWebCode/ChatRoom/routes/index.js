
var axios = require('axios');
axios.defaults.withCredentials = true;

const express = require('express');
const _ = require('underscore');
const { createProxyMiddleware } = require('http-proxy-middleware'); //introduce the proxy module

const app = express();
var http = require('http').createServer(app);
var io = require("socket.io")(http);

app.use(express.static('public'))

app.get('/', function(req, res){
	res.sendFile(__dirname+'/index.html');  
});

var userList = [];
io.on("connection",function(socket){
	console.log('a user connected');
	socket.on('login',function(user){
		user.id = socket.id;
		userList.push(user);

		socket.broadcast.emit('addUser',user);

		var toSocket = _.findWhere(io.sockets.sockets,{id:user.id});
		toSocket.emit('userList',userList);

		socket.emit('userObj',user);
		socket.broadcast.emit('online', user.name+" join the room");
	});

	socket.on("sendToAll",function(msgObj){
		console.log(msgObj.from.name+":"+msgObj.msg);
		socket.broadcast.emit("sendToAll",msgObj.msg);		//Send a message to all connected clients
	});

	socket.on("sendToOne",function(msgObj){

		console.log(msgObj.from.name+":"+msgObj.msg);

		if(msgObj.to.id != msgObj.from.id){				//Avoid texting yourself
			var toSocket = _.findWhere(io.sockets.sockets,{id:msgObj.to.id});
			if(toSocket != undefined){
				toSocket.emit("toOne",msgObj);
			}
		}
	
		socket.emit("fromMe",msgObj);
	});


	socket.on('disconnect', function(){		//disconnect
    	console.log('user disconnected');
    	for (var key in userList) {
    		if (userList[key].id == socket.id) {
    			io.emit('offline',userList[key].name+" leave the room");
    			io.emit('deleteUser',userList[key]);
      			userList.splice(key, 1);
    		}
  		}
  	});

});	// listen for connection events

http.listen(3000, function(){
  console.log('listening on *:3000');
});
