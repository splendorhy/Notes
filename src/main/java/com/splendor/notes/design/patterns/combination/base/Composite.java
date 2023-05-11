package com.splendor.notes.design.patterns.combination.base;

import java.util.ArrayList;

/**
 * @author splendor.s
 * @create 2023/5/5 下午5:56
 * @description 树枝构件定义
 */
public class Composite extends Component {

    private ArrayList<Component> children = new ArrayList<Component>();

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return children.get(index);
    }

    @Override
    public void operation() {
        for (Component child : children) {
            child.operation();
        }
    }
}
