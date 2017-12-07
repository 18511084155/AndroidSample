package com.woodys.demos.callback;

/**
 * Created by woodys on 2017/10/27.
 * 需要实现的功能：
 * 执行/下一步：
 * 暂停：
 * 停止：
 * 回放：
 */

public abstract class StepCallBack {
    public abstract void start();
    public abstract void pause();
    public abstract void stop();
    public abstract void playack();
}
