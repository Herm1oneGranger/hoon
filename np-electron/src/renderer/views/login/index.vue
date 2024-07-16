<template>
  <div class="box" @dblclick="handleOpen" v-loading="loading" :style="{
    background: this.background
  }">
    <!-- <el-tooltip class="item" effect="dark" content="查看上传记录" placement="top-start"> -->
    <img src="@/assets/qrCode.png" alt="">
    <!-- </el-tooltip> -->
  </div>
</template>

<script>

const defaultConfigStr = `DIRECTORY_PATH=\\\\wujsrv01\\IC\\Nameplate_anti-fake
BASE_URL=https://etrace.rexrothlive.cn/api
USER_NAME=printer
USER_PASSWORD=123456
PAGE_URL=https://etrace.rexrothlive.cn`
import axios from 'axios'
const fs = require('fs');
const path = require('path');
const { shell } = require("electron")
import { app, ipcRenderer } from "electron";


// 指定目录路径
const DIRECTORY_PATH = 'D:/np-test/'

// 执行保存的文件
const BASE_URL = 'https://etrace.rexrothlive.cn/api'

const USER_NAME = 'printer'
const USER_PASSWORD = '123456'

const PAGE_URL = `https://etrace.rexrothlive.cn`
const configFileName = "config"

function parseConfig (configStr) {
  // 将数据按行分割
  const lines = configStr.split('\n');

  console.log("lines", lines)
  // 创建一个空对象
  const result = {};

  // 遍历每一行数据
  lines.forEach(line => {
    // 按等号分割键值对
    const [key, value] = line.split('=');


    // 去除键和值两端的空格，并将值转为数字类型
    result[key.trim()] = value.trim();
  });

  return result;
}

export default {
  data () {
    return {
      timer: null,
      config: {
        DIRECTORY_PATH,
        BASE_URL,
        USER_NAME,
        USER_PASSWORD,
        PAGE_URL,
      },
      background: '#0cf',
      loading: false
    }
  },
  methods: {
    async initConfig (homeDir) {
      let configObj = {}
      const configStr = await this.readFileToString(path.join(homeDir, configFileName))

      const defaultConfig = parseConfig(defaultConfigStr)

      if (!configStr) {
        await this.saveFileWithDir(homeDir, configFileName, Buffer.from(defaultConfigStr))
      }

      if (configStr) {
        configObj = parseConfig(configStr)
      }

      this.config = { ...defaultConfig, ...configObj }
      this.onStart()
    },
    handleOpen () {
      const OPEN_URL = `${this.config.PAGE_URL}/user/auto-login?username=${this.config.USER_NAME}&password=${this.config.USER_PASSWORD}`

      shell.openExternal(OPEN_URL)
    },
    async getToken () {
      const response = await axios.post(`${this.config.BASE_URL}/login`, {
        password: this.config.USER_PASSWORD,
        rememberMe: false,
        username: this.config.USER_NAME
      });
      console.log("response", response)
      return response.data.token
    },

    // 读取文件
    readFileToString (filePath) {
      return new Promise((resolve, reject) => {
        if (!fs.existsSync(filePath)) {
          resolve('');
          return
        }

        fs.readFile(filePath, (error, data) => {
          if (error) {
            reject(error);
            return;
          }

          resolve(data.toString('utf-8'));
        });
      });
    },

    readFileToBuffer (filePath) {
      return new Promise((resolve, reject) => {
        fs.readFile(filePath, (error, data) => {
          if (error) {
            console.log("readFileToBuffer", error)
            reject(error);
            return;
          }

          resolve(data);
        });
      })

    },
    // 保存文件
    saveFileWithDir (dirPath, filename, buffer) {
      if (!Buffer.isBuffer(buffer)) {
        throw (400, 'The buffer is incorrect');
      }

      if (!fs.existsSync(dirPath)) {
        fs.mkdirSync(dirPath, { recursive: true });
      }

      const filePath = path.join(dirPath, filename);

      return new Promise((resolve, reject) => {
        fs.writeFile(filePath, buffer, error => {
          if (error) {
            reject(error);
          }
          resolve(filePath);
        });
      });
    },

    async initUpload(initialFiles){
      console.log("initialFiles",initialFiles)
      for (let index = 0; index < initialFiles.length; index++) {
        const filename = initialFiles[index];

      if ( filename.indexOf('.XML') === -1 && filename.indexOf('.xml') === -1) {
            return
        }

        const filePath = path.join(this.config.DIRECTORY_PATH, filename)
        if (!fs.existsSync(filePath)) {
              return
        }

        const bufferData = await this.readFileToBuffer(filePath);
        const formData = new FormData();
        const contentBlob = new Blob([bufferData], { type: 'application/octet-stream' })
        formData.append('file', new File([contentBlob], filename))

        await this.uploadFile(formData,filePath)

      }
    },

    onStart () {
      fs.readdir(this.config.DIRECTORY_PATH, (err, files) => {
        if (err) {
          console.error(err);
          return;
        }

        this.initUpload(files)
        
        const initialFiles = new Set(files);

        // 监听文件夹的变化
        fs.watch(this.config.DIRECTORY_PATH, async (eventType, filename) => {
          if ( filename.indexOf('.XML') === -1 && filename.indexOf('.xml') === -1) {
            return
          }

          if (eventType === 'rename') {
            const filePath = path.join(this.config.DIRECTORY_PATH, filename)

            if (!fs.existsSync(filePath)) {
              return
            }

            // 判断是否为新增文件
            if (!initialFiles.has(filename)) {

              setTimeout(async ()=>{
                const bufferData = await this.readFileToBuffer(filePath);

                const formData = new FormData();
                const contentBlob = new Blob([bufferData], { type: 'application/octet-stream' })
                formData.append('file', new File([contentBlob], filename))

                await this.uploadFile(formData,filePath)
              },2000)
            }
          }
        });
      });
    },

    async uploadFile (formData,filePath) {
      try {

        console.log('2222', 222)

        this.loading = true
        const token = await this.getToken()
        const url = this.config.BASE_URL + '/product/print/importData'; // 替换为实际的上传 URL
        const headers = {
          ...formData.headers,
          // 'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`
        };

        const res = await axios.post(url, formData, { headers });
        console.log('res', 'res')
        if (res.data.code !== 200) {
          this.background = '#df0024'
        } else {
          this.background = '#0cf';

          fs.unlinkSync(filePath,(err)=>{
            console.log("err",err)
          })
        }


      } catch (error) {
        this.background = '#df0024'
        console.log(error)
        console.error('文件上传失败');
      } finally {
        this.loading = false
      }
    },

  },
  async mounted () {
    ipcRenderer.invoke("get-app-path").then(res => {
      console.log('res', res)
      this.initConfig(res)
    });

  }
}
</script>

<style lang="scss" scoped>
.box {
  width: 80px;
  height: 80px;
  background: #0cf;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  position: fixed;
  overflow: hidden;
  top: 0;
  z-index: 99999;
  cursor: pointer;
  // box-shadow: 0px 3px 49px -20px #00ccff;

  img {
    width: 40px;
    -webkit-app-region: drag;
    height: 40px;
    cursor: move;
  }
}
</style>

