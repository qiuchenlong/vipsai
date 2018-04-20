<img src="https://d3heg6bx5jbtwp.cloudfront.net/static/b807/site/img/cf_logo.png" alt="Ten Inc. logo" title="Ten" align="right" height="128" width="128" />


# 微赛小视频 vipsai



**Copyright(c) __vidsai.com__. All rights reserved.**


## Table of Contents
 
  * [Multi-channel deployment](#deploy)



## Deploy 执行多渠道打包脚本 : 
  
  - 开发环境 (可选参数 --info --debug)
  
      ```shell
        ./gradlew assembleDebug 
      ``` 
  
  
  
  - 生产环境 (可选参数 --info --debug)
  
      ```shell
        ./gradlew assembleRelease
      ``` 


adb shell monkey -p com.vs.vipsai -v 1000

./emulator -avd Nexus_5X_API_24 -use-system-libs
      
  
  

