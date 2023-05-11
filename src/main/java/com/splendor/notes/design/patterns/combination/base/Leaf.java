package com.splendor.notes.design.patterns.combination.base;

/**
 * @author splendor.s
 * @create 2023/5/5 下午5:57
 * @description 树叶构件定义：
 */
public class Leaf extends Component {

    private String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public void operation() {
        System.out.println("树叶" + name + "：被访问！");
    }

}
