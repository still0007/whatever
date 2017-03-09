var path = require('path');
var webpack = require('webpack');
var StatsPlugin = require('stats-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

var devServerPort = 3080;
var production = process.env.NODE_ENV === 'production';


var config = {
  entry: {
    'todos': __dirname + "/src/main/webapp/app-todo/index.js",
    'wecord': __dirname + "/src/main/webapp/app-wecord/index.js"
  },
  output: {
    path: path.join(__dirname, '/src/main/webapp/static/build'),
    publicPath: '/static/build/',
    filename: production ? '[name]-[hash].js' : '[name].js'
  },

  module: {
    loaders: [
      {
        test: /\.json$/,
        loader: "json"
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      },
      {
        test: /\.css$/,
        loader: ExtractTextPlugin.extract({ fallback: 'style-loader', use: 'css-loader'})
      }
    ]
  },

  plugins: [
    new ExtractTextPlugin( production ? '[name]-[hash].css' : '[name].css' ),
    new StatsPlugin('manifest.json', {
      chunkModules: false,
      source: false,
      chunks: false,
      modules: false,
      assets: true
    }),
    new webpack.BannerPlugin("Copyright Oracle SRM.")
  ],

  devServer: {
    port: devServerPort,
    headers: { 'Access-Control-Allow-Origin': '*' }
  }
};

if(!production) config.devtool = "source-map";

module.exports = config;
