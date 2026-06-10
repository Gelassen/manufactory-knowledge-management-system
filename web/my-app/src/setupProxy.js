const { createProxyMiddleware } = require('http-proxy-middleware');

console.log('PROXY LOADED');

module.exports = function(app) {

  console.log('PROXY REGISTERED');

  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://server:8080',
      changeOrigin: true,
    })
  );
};