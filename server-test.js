const http = require('http');

const hostname = '0.0.0.0';   // Важно! Слушаем на всех интерфейсах
const port = 3000;

const server = http.createServer((req, res) => {
  res.writeHead(200, { 'Content-Type': 'text/plain' });
  res.end('Hello from Node.js test server!\n');
});

server.listen(port, hostname, () => {
  console.log(`Test server running at http://${hostname}:${port}`);
  console.log('Доступен по IP машины, например: http://192.168.1.XXX:3000');
});