package com.newmesnet.nowmesnet.base;

import com.newmesnet.nowmesnet.exception.ErrorDefinition;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author zqh
 * @create 2022-06-23 15:07
 */
public abstract class BaseAbstractController {

    protected int parseSize(int size) {
        return size<=0?10:(size>250?250:size);
    }

    protected int parsePage(int page) {
        return page<0?0:page;
    }

    protected ResponseEntity<ApiResponse> createResponseEntity(Page<?> page) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ApiResponse.STATUS_OK);
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setSize(page.getSize());
        response.setNumber(page.getNumber());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setEntities(page.getContent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse> createResponseEntity(Object entity) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ApiResponse.STATUS_OK);
        response.setEntity(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse> createResponseEntity(Object entity, HttpStatus status) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ApiResponse.STATUS_OK);
        response.setEntity(entity);
        return new ResponseEntity<>(response, status);
    }


    protected ResponseEntity<ApiResponse> createResponseEntity(List<?> entities) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ApiResponse.STATUS_OK);
        response.setEntities(entities);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected ResponseEntity<ApiResponse> createResponseEntity() {
        ApiResponse response = new ApiResponse();
        response.setStatus(ApiResponse.STATUS_OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 完全定制一个ResponseEntity<ApiResponse>
     * @param entity    返回的单个内存，可以为null
     * @param entities  返回的数组，如果page也有会被page覆盖，可以为null
     * @param page      返回的分页，会覆盖page，可以为null
     * @param status    使用ApiResponse.STATUS_OK或者ApiResponse.STATUS_FAIL，不能为null
     * @param httpStatus    http状态码，不能为null
     * @return  创建的返回实体
     */
    protected ResponseEntity<ApiResponse> createResponseEntity(Object entity, List<?> entities, Page<?> page, String status, HttpStatus httpStatus) {
        ApiResponse response = new ApiResponse();
        response.setStatus(status);
        if (entity != null) {
            response.setEntity(entity);
        }
        if (entities != null) {
            response.setEntities(entities);
        }
        if (page != null) {
            response.setFirst(page.isFirst());
            response.setLast(page.isLast());
            response.setTotalPages(page.getTotalPages());
            response.setTotalElements(page.getTotalElements());
            response.setSize(page.getSize());
            response.setNumber(page.getNumber());
            response.setNumberOfElements(page.getNumberOfElements());
            response.setEntities(page.getContent());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    protected ResponseEntity<ApiResponse> createResponseEntity(ErrorDefinition errorDefinition, HttpStatus httpStatus) {
        ApiResponse response = new ApiResponse();
        response.setErrorCode(errorDefinition.getErrorCode());
        response.setErrorDescription(errorDefinition.getErrorDescription());
        response.setStatus(ApiResponse.STATUS_FAIL);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 封装前端显示结果
     * @param errorDefinition
     * @param httpStatus
     * @return
     */
    protected ResponseEntity<ApiResponse> createResponseEntity(ErrorDefinition errorDefinition, String errorField, HttpStatus httpStatus) {
        ApiResponse response = new ApiResponse();
        response.setErrorCode(errorDefinition.getErrorCode());
        response.setErrorField(errorField);
        response.setErrorDescription(errorDefinition.getErrorDescription());
        response.setStatus(ApiResponse.STATUS_FAIL);
        return new ResponseEntity<>(response, httpStatus);
    }
}
