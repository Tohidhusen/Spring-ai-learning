package com.Spring_ai.learn.tool;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class ShareMarket {

    @Tool(description = "Get the current stock price for a given ticker symbol")
    public String getStockPrice(@ToolParam(description ="The stock ticker symbol") String ticker){
        return switch (ticker.toUpperCase()) {
            case "AAPL" -> "Apple Inc. (AAPL) is currently trading at $150.00 per share.";
            case "GOOGL" -> "Alphabet Inc. (GOOGL) is currently trading at $2800.00 per share.";
            case "AMZN" -> "Amazon.com, Inc. (AMZN) is currently trading at $3400.00 per share.";
            default -> "Ticker symbol not recognized. Please provide a valid stock ticker symbol.";
        };

    }

    @Tool(description = "Buy a specified quantity of shares for a given ticker symbol")
    public String buyStock(@ToolParam(description = "The stock ticker symbol") String ticker, @ToolParam(description = "The number of shares to buy") int quantity) {
        return switch (ticker.toUpperCase()) {
            case "AAPL" -> "You have bought %d shares of Apple Inc. (AAPL) at $150.00 per share.".formatted(quantity);
            case "GOOGL" -> "You have bought %d shares of Alphabet Inc. (GOOGL) at $2800.00 per share.".formatted(quantity);
            case "AMZN" -> "You have bought %d shares of Amazon.com, Inc. (AMZN) at $3400.00 per share.".formatted(quantity);
            default -> "Ticker symbol not recognized. Please provide a valid stock ticker symbol.";
        };
    }
}
