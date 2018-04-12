<img src="https://d3heg6bx5jbtwp.cloudfront.net/static/b807/site/img/cf_logo.png" alt="Ten Inc. logo" title="Ten" align="right" height="128" width="128" />


# 微赛小视频 vipsai



**Copyright(c) __vidsai.com__. All rights reserved.**


## Table of Contents
 
  * [Multi-channel deployment](#deploy)
  * [React Native](#react native)


## Deploy 执行多渠道打包脚本 : 
  
  - 开发环境 (可选参数 --info --debug)
  
      ```shell
        ./gradlew assembleDebug 
      ``` 
  
  
  
  - 生产环境 (可选参数 --info --debug)
  
      ```shell
        ./gradlew assembleRelease
      ``` 
      
     
## React Native

  - exec
  
    ```shell
        react-native bundle --entry-file index.js --bundle-output ./app/src/main/assets/index.android.bundle --platform android --assets-dest ./app/src/main/res/ --dev false
    ```
  
  

