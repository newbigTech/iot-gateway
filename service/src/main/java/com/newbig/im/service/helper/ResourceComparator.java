package com.newbig.im.service.helper;


import com.newbig.im.dal.model.SysResource;

import java.util.Comparator;

public class ResourceComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        SysResource vo1 = (SysResource) o1;
        SysResource vo2 = (SysResource) o2;
        if (vo1.getSort() > vo2.getSort()) {
            return 1;
        } else if (vo1.getId() > vo2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
