package com.newmesnet.nowmesnet.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author zqh
 * @create 2022-06-23 15:08
 */
@Data
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class ApiResponse {
    public static final String STATUS_OK = "OK";
    public static final String STATUS_FAIL = "FAIL";

    /**
     * 标明请求是否成功
     */
    private String status;
    /**
     * @deprecated
     */
    private Integer count;
    /**
     * @deprecated
     */
    private Integer totalCount;
    /**
     * @deprecated
     */
    private List<?> data;
    /**
     * TODO 错误编码,需要讨论
     */
    private String errorCode;
    /**
     * 错误字段,用作前端显示
     */
    private String errorField;
    /**
     * 错误信息,用作前端提示
     */
    private String errorDescription;
    /**
     * 存放多个返回结果,比如查询坐席列表的结果
     */
    private List<?> entities;
    /**
     * 存放单个返回结果,比如查询某个坐席的结果
     */
    private Object entity;

    /**
     * 分页查询时使用,是否第一页
     */
    private Boolean first;
    /**
     * 分页查询时使用,是否最后一页
     */
    private Boolean last;
    /**
     * 一页的记录个数
     */
    private Integer size;
    /**
     * 当前页,从0开始
     */
    private Integer number;
    /**
     * 当前页的记录个数
     */
    private Integer numberOfElements;
    /**
     * 总共多少页
     */
    private Integer totalPages;
    /**
     * 总共多少记录
     */
    private Long totalElements;

    public void setData(List<?> data) {
        if(data!=null){
            this.count = data.size();
            this.data = data;
        }
    }
}
