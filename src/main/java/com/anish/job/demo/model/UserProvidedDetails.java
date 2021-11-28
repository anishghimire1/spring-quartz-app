package com.anish.job.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("User provided Job Details")
@Data
public class UserProvidedDetails {

    @ApiModelProperty(value="Job name")
    private String jobName;

    @ApiModelProperty(value = "Job details that will be printed on file")
    private String details;

    @ApiModelProperty(value = "Interval in seconds to run a job periodically")
    private Integer interval;
}
