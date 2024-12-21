package com.cershy.linyuminiserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuminiserver.entity.Group;

public interface GroupService extends IService<Group> {
    void updateDefaultGroup();
}
