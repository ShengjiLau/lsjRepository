package com.sso.client.service;

import com.sso.client.model.SsoUserModel;

/**
 * Created by ss on 2017/9/15.
 */
public interface SsoService {

	SsoUserModel loadUserInfo(String ticket);

}
