package com.shiji.thread.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class GarbageBag {
    private String desc;
    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
