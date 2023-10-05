package com.miraway.mss.web.rest.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.miraway.mss.constants.Constants.*;
import static com.miraway.mss.constants.Constants.DEFAULT_PAGE_SIZE;

public class PaginationRequest {

    private int current = DEFAULT_PAGINATION_REQUEST_INDEX;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private String sortBy = "";

    private String sortDirection = "";

    public PaginationRequest() {}

    public PaginationRequest(int current, int pageSize, String sortBy, String sortDirection) {
        this.current = current;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return StringUtils.trim(sortBy);
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return StringUtils.trim(sortDirection);
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getCurrent() {
        return current - 1;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pageable toPageable(int maxPageSize) {
        if (this.current < 1) {
            this.current = DEFAULT_PAGINATION_REQUEST_INDEX;
        }

        if (this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        if (this.pageSize > maxPageSize) {
            this.pageSize = maxPageSize;
        }

        if (StringUtils.isNotBlank(sortBy)) {
            Sort sort = Sort.by(sortBy);
            if (sortDirection.equalsIgnoreCase(DESCENDING_SORT_DIRECTION)) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }

            return PageRequest.of(this.current - 1, this.pageSize, sort);
        }

        return PageRequest.of(this.current - 1, this.pageSize);
    }

    public Pageable toPageable(Sort sort, int maxPageSize) {
        if (this.current < 1) {
            this.current = DEFAULT_PAGINATION_REQUEST_INDEX;
        }

        if (this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        if (this.pageSize > maxPageSize) {
            this.pageSize = maxPageSize;
        }

        return PageRequest.of(this.current - 1, this.pageSize, sort);
    }
}
