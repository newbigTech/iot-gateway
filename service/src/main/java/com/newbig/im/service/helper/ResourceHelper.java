package com.newbig.im.service.helper;

import com.google.common.collect.Lists;
import com.newbig.im.dal.model.SysResource;

import java.util.Collections;
import java.util.List;

public class ResourceHelper {
    public static List<SysResource> buildResourceTreeVo(List<SysResource> resources) {
        List<SysResource> rootTrees = Lists.newArrayList();
        for (SysResource resource : resources) {
            if (resource.getAncesstorId()==0 && resource.getLevel()==0) {
                rootTrees.add(resource);
            }
            for (SysResource t : resources) {
                if (t.getParentId().equals(resource.getId())) {
                    resource.getChildren().add(t);
                }
            }
        }
        Collections.sort(rootTrees, new ResourceComparator());

        return rootTrees;
    }
}
