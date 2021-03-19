const path = require("path");
const htmlWebpackPlugin = require("html-webpack-plugin");
const vueLoaderPlugin = require("vue-loader/lib/plugin");
const copyWebpackPlugin = require("copy-webpack-plugin");

module.exports = {
    entry: {
        index: "./src/index.js"
	},

    output: {
        path: path.resolve("build"),
        filename: "js/[name].[hash].js"
    },

    mode: "development",

    module: {
        rules: [
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.(ttf|eot|svg|woff|woff2)$/,
                use: ["url-loader"]
            },
            {
                test: /\.vue$/,
                use: ["vue-loader"]
            },
            {
                test: /\.png$/,
                use: "url-loader?esModule=false"
            }
        ]
    },

    plugins: [
        new htmlWebpackPlugin({
            filename: "index.html",
            template: "./src/index.html",
            chunks: ["index"]
        }),
        new vueLoaderPlugin(),
        new copyWebpackPlugin({
            patterns: [
                {
                    from: "./src/favicon.ico",
                    to: "favicon.ico"
                }
            ]
        })
    ],

    devServer: {
        port: 9003,
        disableHostCheck: true
    },

    performance: {
        hints: false
    }
};