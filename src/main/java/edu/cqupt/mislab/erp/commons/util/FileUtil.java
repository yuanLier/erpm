package edu.cqupt.mislab.erp.commons.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import edu.cqupt.mislab.erp.commons.aspect.FileOperateException;
import edu.cqupt.mislab.erp.commons.config.QiniuProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;

/**
 * @author yuanyiwen
 * @create 2019-10-20 10:34
 * @description 文件存储工具类
 */
@Slf4j
public abstract class FileUtil {

    /**
     * 单文件上传
     * @param qiniuProperties 可恶，这个属性不好在这里注入，只能通过参数传进来了
     * @param file 文件
     * @param key 存储时的键值（其实就是文件名）
     * @return
     */
    public static boolean fileUpload(QiniuProperties qiniuProperties, FileInputStream file, String key) {

        // 构造一个带指定Region对象的配置类
        Configuration configuration = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(configuration);
        // 生成上传凭证
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucket());

        try {
            // 文件上传
            Response response = uploadManager.put(file, key, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

        } catch (QiniuException e) {

            // 对外抛出文件上传异常
            throw new FileOperateException("文件上传失败！");
        }

        return true;
    }


    /**
     * 单文件删除
     * @param qiniuProperties
     * @param key 键值
     * @return
     */
    public static boolean fileDelete(QiniuProperties qiniuProperties, String key) {

        Configuration configuration = new Configuration(Region.region0());
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, configuration);

        //指定文件所在的存储空间和需要删除的文件
        try {
            bucketManager.delete(qiniuProperties.getBucket(), key);
        } catch (QiniuException e) {
            throw new FileOperateException("文件已删除或文件不存在！");
        }

        return true;
    }


    /**
     * 返回文件存储路径；直接访问这个路径即可得到文件本身
     * @param qiniuProperties
     * @param key
     * @return 文件的存储路径 ：文件所在的存储空间+键值
     */
    public static String getLocationByKey(QiniuProperties qiniuProperties, String key) {
        return qiniuProperties.getDomain() + "/" + key;
    }
}
