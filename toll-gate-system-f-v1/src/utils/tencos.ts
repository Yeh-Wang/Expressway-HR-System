import COS from "cos-js-sdk-v5";
import { ElMessage } from "element-plus";

const cos = new COS({
    SecretId: import.meta.env.VITE_TENCOS_APP_ID,
    SecretKey: import.meta.env.VITE_TENCOS_APP_KEY,
});

export const uploadFile = (file: File, username: string): Promise<string> => {
    return new Promise((resolve) => {
        cos.putObject(
            {
                Bucket: "toll-station-syatem-1312699896",
                Region: "ap-chongqing",
                Key: '/user-avatars/' + username + '.' + file.type.split('/')[1],
                StorageClass: "STANDARD",
                Body: file,
            }, (err, data) => {
                if (err) {
                    ElMessage.error("上传失败");
                }
                if (data.statusCode === 200) {
                    console.log(file.type)
                    resolve(data.Location);
                    return data.Location;
                } else
                    ElMessage.error("上传失败");
            }
        );
    })
}
