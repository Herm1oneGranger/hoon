<template>
  <div class="box">
    <img src="@/assets/qrCode.png" alt="">
  </div>
</template>

<script>
import axios from 'axios'
// 指定目录路径
const DIRECTORY_PATH = 'D:/np-test/'

// 执行保存的文件
const UPLOADED_HISTORY_NAME = 'uploadedHistory.json'

const BASE_URL = 'http://localhost:8080/product/print/importData'


const fs = require('fs');
const path = require('path');
export default {
  data () {
    return {
      timer: null,

    }
  },
  methods: {
    // 读取文件
    readFileToString (filePath) {
      return new Promise((resolve, reject) => {
        if (!fs.existsSync(filePath)) {
          resolve('[]');
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
    // 获取最新的文件
    getLatestFile (directory) {
      // 读取目录下的所有文件
      const files = fs.readdirSync(directory);

      // 过滤出文件
      const fileStats = files
        .map(file => ({
          name: file,
          stat: fs.statSync(path.join(directory, file))
        }))
        .filter(file => file.stat.isFile());

      // 按照修改时间排序
      fileStats.sort((a, b) => b.stat.mtimeMs - a.stat.mtimeMs);

      // 返回最新的文件路径
      return path.join(directory, fileStats[0].name);
    },
    // 定时扫描
    intervalScan () {
      this.timer = setInterval(async () => {


        // const latestDirectoryPath = this.getLatestDirectory(DIRECTORY_PATH)
        // const allFilesName = this.getAllFilesName(latestDirectoryPath)

        // 

        this.onStart()
      }, 1000)
    },

    async onStart () {
      const notUploadDirectories = await this.getNotUploadDirectory()
      await this.batchUploadByDirectory(notUploadDirectories)

    },

    async batchUploadByDirectory (notUploadDirectories) {

      const notUploadDirectoriesLength = notUploadDirectories.length
      console.log("notUploadDirectories", notUploadDirectories)
      for (let index = 0; index < notUploadDirectoriesLength; index++) {
        const notUploadDirectoryName = notUploadDirectories[index];
        console.log("notUploadDirectoryName", notUploadDirectoryName)
        const files = this.getAllFilesName(path.join(DIRECTORY_PATH, notUploadDirectoryName))
        console.log("files", files)
        const filesLength = files.length
        for (let y = 0; y < filesLength; y++) {
          const file = files[y];
          console.log('file', file)

          // const localFile = await fs.createReadStream(file.path);
          // console.log('localFile', localFile)

          const bufferData = await this.readFileToBuffer(file.path);

          const formData = new FormData();
          const contentBlob = new Blob(bufferData, { type: 'application/octet-stream' })
          formData.append('file', new File([contentBlob], file.name))

          await this.uploadFile(formData)
        }

        this.uploadedList.push(notUploadDirectoryName)
      }

      const bufferData = Buffer.from(JSON.stringify(this.uploadedList))
      this.saveFileWithDir(DIRECTORY_PATH, UPLOADED_HISTORY_NAME, bufferData)
    },

    async uploadFile (formData) {
      try {
        const url = BASE_URL; // 替换为实际的上传 URL
        const headers = {
          'Content-Type': 'multipart/form-data', // 设置请求头为二进制流类型
          ...formData.headers,
        };

        const response = await axios.post(url, formData, { headers });
        console.log('文件上传成功');
      } catch (error) {
        console.error('文件上传失败');
      }
    },

    // 获取未上传的目录，多个
    async getNotUploadDirectory () {
      const uploadedList = await this.getUploadedList(DIRECTORY_PATH + UPLOADED_HISTORY_NAME) || []
      this.uploadedList = uploadedList
      console.log('uploadedList', uploadedList)

      const allDirectories = this.getAllDirectories(DIRECTORY_PATH)
      console.log("allDirectories", allDirectories)


      const notUploadDirectories = allDirectories.filter(x => x.includes(allDirectories))

      console.log("notUploadDirectories", notUploadDirectories)
      return notUploadDirectories
    },

    // 获取指定目录下所有目录
    getAllDirectories (directory) {
      // 读取目录下的所有文件和目录
      const entries = fs.readdirSync(directory, { withFileTypes: true });

      // 过滤出目录
      const directories = entries
        .filter(entry => entry.isDirectory())
        .map(entry => entry.name);

      // 返回目录名数组
      return directories;
    },

    // 获取最新的目录
    getLatestDirectory (directory) {
      // 读取目录下的所有文件和目录
      const entries = fs.readdirSync(directory, { withFileTypes: true });

      // 过滤出目录
      const directories = entries.filter(entry => entry.isDirectory());

      // 按照创建时间排序
      directories.sort((a, b) => {
        const aPath = path.join(directory, a.name);
        const bPath = path.join(directory, b.name);
        return fs.statSync(bPath).birthtimeMs - fs.statSync(aPath).birthtimeMs;
      });

      // 返回最新的目录路径
      return path.join(directory, directories[0].name);
    },
    // 获取指定目录下的所有文件
    getAllFilesName (directory) {
      const entries = fs.readdirSync(directory, { withFileTypes: true });

      // 过滤出文件
      const files = entries
        .filter(entry => entry.isFile())
        .map(entry => {
          return {
            name: entry.name,
            path: path.join(directory, entry.name,)
          }
        });

      // 返回文件名数组
      return files;
    },
    // 获取已上传的目录列表
    async getUploadedList (filePath) {
      const data = await this.readFileToString(filePath)
      return JSON.parse(data)
    }
  },
  async mounted () {
    // this.intervalScan()

    // this.onStart()

  }
}
</script>

<style lang="scss" scoped>
.box {
  width: 100px;
  height: 100px;
  background: #0cf;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;

  img {
    width: 40px;
    height: 40px;
  }
}
</style>