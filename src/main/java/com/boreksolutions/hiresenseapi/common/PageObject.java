package com.boreksolutions.hiresenseapi.common;

import com.boreksolutions.hiresenseapi.core.job.dto.response.ViewJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageObject<T> {

    private List<T> data;

    private Integer page;

    private Integer size;

    private Integer totalPages;

    private Long totalSize;

    public PageObject(Page<T> page, int currentPage) {
        this.page = currentPage;
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalSize = page.getTotalElements();
        this.data = page.getContent();
    }

    public PageObject(List<ViewJob> viewJobs, long totalElements, int totalPages) {
    }
}
