package com.careerbuddy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {

    private VersionControl versionControl;
    private HostInfo hostInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HostInfo {
        private String hostname;
        private String component;
        private String datacenter;
        private String environment;
        private String swarm;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class VersionControl {
        private String branch;
        private String buildHost;
        private String buildTime;
        private String buildUserEmail;
        private String buildUserName;
        private String buildVersion;
        private String closestTagCommitCount;
        private String closestTagName;
        private String commitId;
        private String commitIdAbbrev;
        private String commitIdDescribe;
        private String commitIdDescribeShort;
        private String commitMessageFull;
        private String commitMessageShort;
        private String commitTime;
        private String commitUserEmail;
        private String commitUserName;
    }
}
