package io.jboot.admin.controller.content;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.admin.base.rest.datatable.DataTable;
import io.jboot.admin.base.web.base.BaseController;
import io.jboot.admin.support.aws.AmazonS3Utils;
import io.jboot.web.controller.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("/content/files")
public class FilesController extends BaseController {

    final private static String accessKey = "AKIA5B7XZO73BVTI5JH2";
    final private static String secretKey = "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";
    final private static String region = "ap-southeast-1";
    /**
     * index
     */
    public void index() {
        render("main.html");
    }

    /**
     * 表格数据
     */
    public void tableData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);
        String bucketName = getPara("name")!=null && getPara("name")!=""?getPara("name"):"cloud-ota";
        AmazonS3Utils amazonS3Utils = new AmazonS3Utils(accessKey,secretKey,region);
        Page<S3ObjectSummary> dataPage;
        try{
            ObjectListing bucket = amazonS3Utils.listFiles(bucketName);
            dataPage = new Page<S3ObjectSummary>(bucket.getObjectSummaries(),1,bucket.getObjectSummaries().size(),1,bucket.getObjectSummaries().size()) ;
        }catch (Exception e){
            dataPage = new Page<S3ObjectSummary>(new ArrayList<S3ObjectSummary>(),1,0,1,0) ;
        }

        renderJson(new DataTable<S3ObjectSummary>(dataPage));
    }
}
