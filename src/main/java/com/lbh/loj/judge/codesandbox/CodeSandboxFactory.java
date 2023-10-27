package com.lbh.loj.judge.codesandbox;

import com.lbh.loj.judge.codesandbox.impl.ExecuteCodeSandbox;
import com.lbh.loj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.lbh.loj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 工厂模式
 * ClassName: CodeSandboxFactory
 * Package: com.lbh.loj.judge.codesandbox
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 18:58
 * @Version 1.0
 */
public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExecuteCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExecuteCodeSandbox();
        }
    }
}
