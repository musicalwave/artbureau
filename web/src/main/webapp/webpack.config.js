var webpack = require('webpack');

module.exports = {
  entry: {
    client: './src/client.js',
    schedule: './src/schedule.js'
  },
  output: {
    filename: '[name].min.js',
    path: __dirname + '/resources/js'
  },
  module: {
    loaders: [{ 
      test: /\.jsx?$/,
      exclude: /node_modules/,
      loader: 'babel'
    }, {
      test: /\.css$/, loader: 'style-loader!css-loader'
    }]
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin({
      minimize: true,
      compress: {
        warnings: false
      }})
  ],
  resolve: {
    extensions: ['', '.js', '.jsx']
  }
};
