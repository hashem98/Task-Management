package com.example.taskmanagement.bo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponseList<R>  {

    private List<R> response;

    private Long totalElements;
}
