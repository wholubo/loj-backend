package com.lbh.loj.judge.codesandbox.impl;


import com.lbh.loj.judge.codesandbox.CodeSandbox;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
