package com.Spring_ai.learn.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class RouterTool {

    @Tool(description = """
        Restart the user's router when they explicitly request
        a restart or reboot. A valid router serial number is required.
        Do not invent a serial number.
        """)
    public String rebootRouter(
            @ToolParam(description = "The router's serial number")
            String serialNo) {

        if (serialNo == null || serialNo.isBlank()) {
            return "A router serial number is required.";
        }

        // Demo implementation only.
        // Replace this with your router manufacturer's API
        // or your actual router management service.
        System.out.println("Reboot requested for router: " + serialNo);

        return "Reboot request accepted for router " + serialNo
                + ". This is a demo; no physical router was restarted.";
    }
}
