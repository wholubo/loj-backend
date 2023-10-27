package com.lbh.loj.judge.codesandbox;

import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理模式
 * ClassName: codeSandboxProxy
 * Package: com.lbh.loj.judge.codesandbox
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 20:38
 * @Version 1.0
 */
@Slf4j
public class codeSandboxProxy implements CodeSandbox{
    private final CodeSandbox codeSandbox;

    public codeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
