package com.careerbuddy.interfaces;

import com.careerbuddy.dto.InfoDTO;
import java.net.UnknownHostException;

public interface AdminServiceExternal {
    InfoDTO getAdminInfo() throws UnknownHostException;
}
