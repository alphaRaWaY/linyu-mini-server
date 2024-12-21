package com.cershy.linyuminiserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuminiserver.entity.Group;
import com.cershy.linyuminiserver.mapper.GroupMapper;
import com.cershy.linyuminiserver.service.GroupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    @Value("${linyu.name}")
    private String defaultGroupName;

    @Override
    public void updateDefaultGroup() {
        Group group = getById("1");
        if (group == null) {
            group = new Group();
            group.setId("1");
            group.setName(defaultGroupName);
            save(group);
        } else if (!group.getName().equals(defaultGroupName)) {
            group.setName(defaultGroupName);
            updateById(group);
        }
    }
}
