package com.foodtruth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MythCreateDTO {

    @NotBlank(message = "误区内容不能为空")
    @Size(max = 500)
    private String myth;

    @NotBlank(message = "真相内容不能为空")
    private String truth;

    @NotBlank(message = "一句话总结不能为空")
    @Size(max = 300)
    private String summary;

    @Size(max = 200)
    private String source;

    public String getMyth() { return myth; }
    public void setMyth(String myth) { this.myth = myth; }

    public String getTruth() { return truth; }
    public void setTruth(String truth) { this.truth = truth; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
