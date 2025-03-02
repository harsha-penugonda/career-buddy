package com.careerbuddy.service;

import com.careerbuddy.dto.InfoDTO;
import com.careerbuddy.interfaces.AdminServiceExternal;
import com.careerbuddy.utils.constants.ApplicationConstants;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements AdminServiceExternal {

    private final Environment environment;

    @Override
    public InfoDTO getAdminInfo() throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName().toLowerCase();
        if (StringUtils.isNotBlank(hostname) && hostname.endsWith("ec2.internal"))
            hostname = hostname.replace("ip-", "").replace("-", ".").replace(".internal", "");
        InfoDTO.HostInfo hostInfo = InfoDTO.HostInfo.builder()
                .component(ApplicationConstants.APPLICATION_NAME)
                .hostname(hostname)
                .datacenter(System.getenv("DATACENTER"))
                .environment(System.getenv("ENV"))
                .swarm(System.getProperty("swarm.identifier"))
                .build();
        InfoDTO.VersionControl versionControl = InfoDTO.VersionControl.builder()
                .branch(environment.getProperty("git.branch", "NA"))
                .buildHost(environment.getProperty("git.build.host", "NA"))
                .buildTime(environment.getProperty("git.build.time", "NA"))
                .buildUserEmail(environment.getProperty("git.build.user.email", "NA"))
                .buildVersion(environment.getProperty("git.build.version", "NA"))
                .closestTagCommitCount(environment.getProperty("git.closest.tag.commit.count", "NA"))
                .closestTagName(environment.getProperty("git.closest.tag.name", "NA"))
                .commitId(environment.getProperty("git.commit.id", "NA"))
                .commitIdAbbrev(environment.getProperty("git.commit.id.abbrev", "NA"))
                .commitIdDescribe(environment.getProperty("git.commit.id.describe", "NA"))
                .commitIdDescribeShort(environment.getProperty("git.commit.id.describe-short", "NA"))
                .commitMessageFull(environment.getProperty("git.commit.message.full", "NA"))
                .commitMessageShort(environment.getProperty("git.commit.message.short", "NA"))
                .commitTime(environment.getProperty("git.commit.time", "NA"))
                .commitUserEmail(environment.getProperty("git.commit.user.email", "NA"))
                .commitUserName(environment.getProperty("git.commit.user.name", "NA"))
                .build();
        return InfoDTO.builder()
                .hostInfo(hostInfo)
                .versionControl(versionControl)
                .build();
    }
}
