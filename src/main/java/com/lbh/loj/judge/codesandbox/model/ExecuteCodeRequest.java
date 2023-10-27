package com.lbh.loj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: ExecuteCodeRequest
 * Package: com.lbh.loj.judge.codesandbox.model
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 18:21
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入用例
     */
    private List<String> inputList;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 编程语言
     */
    private String language;
}
