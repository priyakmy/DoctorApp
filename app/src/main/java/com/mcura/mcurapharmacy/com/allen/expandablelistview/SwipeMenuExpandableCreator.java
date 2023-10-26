package com.mcura.mcurapharmacy.com.allen.expandablelistview;


import com.mcura.mcurapharmacy.com.baoyz.swipemenulistview.SwipeMenu;

/**
 * 
 * @author yuchentang seperate the group and the child's menu creator
 * 
 */
public interface SwipeMenuExpandableCreator {

    void createGroup(SwipeMenu menu);

    void createChild(SwipeMenu menu);
}
