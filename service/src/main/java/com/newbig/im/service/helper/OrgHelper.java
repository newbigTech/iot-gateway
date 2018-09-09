package com.newbig.im.service.helper;

import com.google.common.collect.Lists;
import com.newbig.im.dal.model.SysOrg;

import java.util.List;

public class OrgHelper {
    public static List<SysOrg> buildOrgTree(List<SysOrg> orgs) {
        List<SysOrg> rootTrees = Lists.newArrayList();
        for (SysOrg org : orgs) {
            if (org.getAncesstorId()==1 && org.getLevel()==1) {
                rootTrees.add(org);
            }
            for (SysOrg t : orgs) {
                if (t.getParentId().equals(org.getId())) {
                        org.getChildren().add(t);
                }
            }
        }
        return rootTrees;
    }
}
