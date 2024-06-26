package com.shiji.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;

import java.util.List;

public class TreeBuild {

    /**
     * 系统管理
     *     |- 用户管理
     *     |- 添加用户
     * 店铺管理
     *     |- 商品管理
     *     |- 添加商品
     */
    public static void main(String[] args) {
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));
        System.out.println("nodeList.size() = " + nodeList.size());

        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        System.out.println("treeList.size() = " + treeList.size());

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("level");
        treeNodeConfig.setIdKey("categoryCode");
        treeNodeConfig.setParentIdKey("parentCode");
        // 最大递归深度
        //treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("extraField", 666);
                    tree.putExtra("other", new Object());
                });
        System.out.println("treeNodes.size() = " + treeNodes.size());
    }

}
