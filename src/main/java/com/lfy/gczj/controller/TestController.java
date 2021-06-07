package com.lfy.gczj.controller;


import com.alibaba.fastjson.JSONObject;
import com.lfy.gczj.bean.IndexEntry;
import com.lfy.gczj.bean.SearchBean;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class TestController {

    @Autowired
    private SolrClient client;



    @SneakyThrows
    @RequestMapping(value = "/solr" ,method = RequestMethod.GET)
    public  void test1(){

        File xlsFile = new File("E:\\upload\\01安装材料价格-很全.xlsx");
        // 工作表
        Workbook workbook = WorkbookFactory.create(xlsFile);
        // 表个数。
        int numberOfSheets = workbook.getNumberOfSheets();
        // 遍历表。
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            // 行数。
            int rowNumbers = sheet.getLastRowNum() + 1;
            // Excel第一行。
            Row temp = sheet.getRow(0);
            if (temp == null) {
                continue;
            }
            List<IndexEntry> list = new ArrayList<>();
            // 读数据。
            for (int row = 1; row < rowNumbers; row++) {
                Row r = sheet.getRow(row);
                String s = r.getCell(0).toString() + "";
                if (null==s || "".equals(s.trim()))break;
                IndexEntry indexEntry = new IndexEntry();
                indexEntry.setSid(Double.valueOf(r.getCell(0)+"").intValue());
                indexEntry.setId(Double.valueOf(r.getCell(0)+"").intValue());
                indexEntry.setType(r.getCell(1).toString()+"");
                indexEntry.setName(r.getCell(2).toString()+"");
                indexEntry.setModel(r.getCell(3).toString()+"");
                indexEntry.setUnit(r.getCell(4).toString()+"");
                indexEntry.setPrice(r.getCell(5).toString()+"");
                list.add(indexEntry);
            }
            try {
                client.addBeans(list);
                client.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 删除所有的索引
     * @return
     */
    @RequestMapping(value = "/deleteAll" ,method = RequestMethod.GET)
    public String deleteAll(){
        try {

            client.deleteByQuery("*:*");
            client.commit();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject search(String search,int page,int limit){
         log.info("搜索词为："+search);
        try {
            SolrQuery params = new SolrQuery();

            //查询条件, 这里的 q 对应 下面图片标红的地方
            params.set("q","*"+search+"*");
            //排序
            params.addSort("sid", SolrQuery.ORDER.asc);
            //
            params.set("start", (page-1)*limit);
            //
            params.set("rows", limit);
            //默认域
            params.set("df", "name");
            //只查询指定域
            params.set("fl", "id,name,type,model,price,unit");

            QueryResponse queryResponse = client.query(params);

            SolrDocumentList results = queryResponse.getResults();

            long numFound = results.getNumFound();


            //获取高亮显示的结果, 高亮显示的结果和查询结果是分开放的
            List res = new ArrayList();
            JSONObject j = new JSONObject();
            for (SolrDocument result : results) {
                JSONObject object = new JSONObject(result);
                res.add(object);
            }

            j.put("code",0);
            j.put("count",numFound);
            j.put("data",res);
            return j;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
