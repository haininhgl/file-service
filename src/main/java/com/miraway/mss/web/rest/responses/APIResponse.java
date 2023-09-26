package com.miraway.mss.web.rest.responses;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.miraway.mss.constants.APIConstants.ResponseStructure.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class APIResponse<T> {

    @JsonProperty(KEY_CODE)
    private int code;

    @JsonProperty(KEY_MESSAGE)
    private String message;

    @JsonProperty(KEY_DATA)
    private T data;

    @JsonProperty(KEY_METADATA)
    private Metadata metadata;

    public APIResponse() {}

    public APIResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> APIResponse<T> newFailureResponse(String message) {
        return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> APIResponse<T> newFailureResponse() {
        return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Thao tác thất bại.", null);
    }

    public static <T> APIResponse<T> newFailureResponse(int code, String message) {
        return new APIResponse<>(code, message, null);
    }

    public static <T> APIResponse<T> newFailureResponse(int code) {
        return new APIResponse<>(code, "Thao tác thất bại.", null);
    }

    public static <T> APIResponse<T> newFailureResponse(int code, String message, T data) {
        return new APIResponse<>(code, message, data);
    }

    public static <T> APIResponse<T> newSuccessResponse(int code, String message, T data) {
        return new APIResponse<>(code, message, data);
    }

    public static <T> APIResponse<T> newSuccessResponse(String message, T data) {
        return new APIResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> APIResponse<T> newSuccessResponse(T data) {
        return new APIResponse<>(HttpStatus.OK.value(), "Thao tác thành công.", data);
    }

    public static <T> APIResponse<T> newSuccessResponse(String message) {
        return new APIResponse<>(HttpStatus.OK.value(), message, null);
    }

    public static <T> APIResponse<T> newSuccessResponse() {
        return new APIResponse<>(HttpStatus.OK.value(), "Thao tác thành công.", null);
    }

    public static <T> APIResponse<List<T>> newSuccessPageResponse(String message, Page<T> data) {
        APIResponse<List<T>> response = new APIResponse<>(HttpStatus.OK.value(), message, data.getContent());
        Metadata metadata = new Metadata(data.getTotalElements(), data.getNumber() + 1, data.getSize());
        response.setMetadata(metadata);

        return response;
    }

    public static <T> APIResponse<List<T>> newSuccessPageResponse(Page<T> data) {
        APIResponse<List<T>> response = new APIResponse<>(HttpStatus.OK.value(), "Thao tác thành công.", data.getContent());
        Metadata metadata = new Metadata(data.getTotalElements(), data.getNumber() + 1, data.getSize());
        response.setMetadata(metadata);

        return response;
    }

    public static <T> APIResponse<List<?>> newSuccessDynamicPageResponse(Page<?> data) {
        APIResponse<List<?>> response = new APIResponse<>(HttpStatus.OK.value(), "Thao tác thành công.", data.getContent());
        Metadata metadata = new Metadata(data.getTotalElements(), data.getNumber() + 1, data.getSize());
        response.setMetadata(metadata);

        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonIgnore
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return code + " - " + message + "  - " + data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof APIResponse)) {
            return false;
        }

        APIResponse<?> that = (APIResponse<?>) o;

        return code == that.code && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }

    public static class Metadata {

        @JsonProperty("total")
        private long total;

        @JsonProperty("current")
        private int page = 0;

        @JsonProperty("pageSize")
        private int size = 100;

        public Metadata() {}

        public Metadata(long total, int page, int size) {
            this.total = total;
            this.page = page;
            this.size = size;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
