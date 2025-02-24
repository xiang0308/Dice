package com.bihell.dice.system.service.impl;

import com.bihell.dice.system.entity.AuthClasses;
import com.bihell.dice.system.entity.AuthGroup;
import com.bihell.dice.system.mapper.AuthClassesMapper;
import com.bihell.dice.system.mapper.AuthGroupMapper;
import com.bihell.dice.system.param.QueryParam;
import com.bihell.dice.system.service.AuthGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author haseochen
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Deprecated
public class AuthGroupServiceImpl implements AuthGroupService {

    private final AuthGroupMapper authGroupMapper;
    private final AuthClassesMapper authClassesMapper;

    /**
     * @param param 查询参数
     * @return List<AuthGroup>
     */
    @Override
    public List<AuthGroup> getGroupList(QueryParam param) {
        List<AuthGroup> groupList = authGroupMapper.queryByParam(param);

        if (CollectionUtils.isEmpty(groupList)) {
            return groupList;
        }


        for (AuthGroup group : groupList) {
            if (group == null || group.getId() == null) {
                continue;
            }

            List<AuthClasses> classesList = authClassesMapper.queryByGroupId(group.getId());
            group.setChildren(classesList);
        }

        return groupList;

    }
}
