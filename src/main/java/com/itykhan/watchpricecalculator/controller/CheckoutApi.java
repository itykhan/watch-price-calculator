package com.itykhan.watchpricecalculator.controller;

import com.itykhan.watchpricecalculator.data.ResultPrice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Checkout", description = "the Checkout API")
public interface CheckoutApi {

    @Operation(
            summary = "Calculate the total price of the watches.",
            description = "calculate the total price of the watches specified by the list " +
                    "of unit ids and taking into account a discount for some units"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "list of watch IDs for price calculation, may include duplicate values.",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "IDs sample",
                                    summary = "IDs example",
                                    value = "[\"001\",\"002\",\"001\",\"004\",\"003\"]"
                            )
                    }
            )
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "a watch with some id is not found")
        }
    )
    ResultPrice calculatePrice(@RequestBody List<String> ids);
}
