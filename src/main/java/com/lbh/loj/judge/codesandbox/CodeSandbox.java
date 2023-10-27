package com.lbh.loj.judge.codesandbox;

import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 * ClassName: CodeSandbox
 * Package: com.lbh.loj.judge.codesandbox
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 18:11
 * @Version 1.0
 */
public interface CodeSandbox {
    /**
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
