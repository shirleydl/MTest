module.exports = {
  // 公共路径(必须有的)
  publicPath: "/mtest/",
  // 输出文件目录
  outputDir: "mtest",
  // 静态资源存放的文件夹(相对于ouputDir)
  assetsDir: "static",
  // eslint-loader 是否在保存的时候检查(果断不用，这玩意儿我都没装)
  lintOnSave:false,
  // 我用的only，打包后小些
  runtimeCompiler: false,
  productionSourceMap: true // 不需要生产环境的设置false可以减小dist文件大小，加速构建

}