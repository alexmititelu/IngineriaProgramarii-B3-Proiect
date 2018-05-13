var http = require('http');
var host = '127.0.0.1';
var port = 8080;

http.createServer(function (req, res) {
    
    res.setHeader('Access-Control-Allow-Origin', 'http://127.0.0.1:5500');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);

    if (req.method === 'POST' && req.url === '/validateContestatie') {
        req.on('data', function (data) {
            var parser = data.toString();
            
            console.log(parser);
            
            setTimeout(function () {
                res.end('da');
            }, 1200);
        });
    }

}).listen(port, host, function(){
    console.log(`Server running at http://${host}:${port}`);
})
.on('error', (err) => console.log(err));
