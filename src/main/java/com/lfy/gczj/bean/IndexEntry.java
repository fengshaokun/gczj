package com.lfy.gczj.bean;


import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

@Data
public class IndexEntry implements Serializable {

    //序号
    @Field("id")
    private int id;
    @Field("sid")
    private int sid;
    //名称
    @Field("name")
    private String name;
    //类别
    @Field("type")
    private String type;
    //型号
    @Field("model")
    private String model;
    //单位
    @Field("unit")
    private String unit;
    //价格
    @Field("price")
    private String price;


}
