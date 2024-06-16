package cn.xylose.mitemod.hwite.api;

public interface IBreakingProgress {
    default float getCurrentBreakingProgress() { return 0; }
}
