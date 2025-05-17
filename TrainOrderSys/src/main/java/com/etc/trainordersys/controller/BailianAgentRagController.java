package com.etc.trainordersys.controller;


import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import com.etc.trainordersys.entity.ResponseResult;
import com.etc.trainordersys.entity.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai")
public class BailianAgentRagController {
    private static final Logger logger = LoggerFactory.getLogger(BailianAgentRagController.class);
    private DashScopeAgent agent;

    @Value("${spring.ai.dashscope.agent.app-id}")
    private String appId;

    public BailianAgentRagController(DashScopeAgentApi dashscopeAgentApi) {
        this.agent = new DashScopeAgent(dashscopeAgentApi);
    }
    //访问大模型智能体
    @PostMapping("/bailian/agent/call")
    public @ResponseBody ResponseResult call(@RequestBody String message) {
        ChatResponse response = agent.call(new Prompt(message, DashScopeAgentOptions.builder().withAppId(appId).build()));
        if (response == null || response.getResult() == null) {
            logger.error("chat response is null");
            return ResponseResult.fail(ResultEnum.FAIL);
        }

        AssistantMessage app_output = response.getResult().getOutput();
        return ResponseResult.success(app_output.getContent());
    }

    //跳转到客服页面
    @GetMapping("/kefu")
    public String  toKefuPage(){
        return "/home/common/kefu";
    }
}
