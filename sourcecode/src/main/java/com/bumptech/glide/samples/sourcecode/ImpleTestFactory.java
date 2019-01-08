package com.bumptech.glide.samples.sourcecode;

public class ImpleTestFactory implements TestFactory {
    @Override
    public TestBean build(String name) {
        return new TestBean(name);
    }
}
